package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import repository.DbConnector;

@WebServlet("/validateUser")
public class ValidateUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String query = "select id, email, password from user where email = ?";

        try (Connection connection = DbConnector.getConnection();
             PreparedStatement st = connection.prepareStatement(query)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();
         
            if (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
                if (rs.getString(2).equals(email) && rs.getString(3).equals(password)) {
                    ServletContext context = getServletContext();
                    String secretKey = context.getInitParameter("SECRET_KEY");
                    if (secretKey == null) {
                        throw new ServletException("Secret key not configured in context parameters");
                    }

                    String jwt = Jwts.builder()
                            .setSubject(String.valueOf(rs.getInt(1)))
                            //.claim("email", email)
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                            .signWith(SignatureAlgorithm.HS256, secretKey)
                            .compact();

                
                    response.setStatus(HttpServletResponse.SC_OK);
                    PrintWriter out = response.getWriter();
                    out.print("{\"jwt\": \"" + jwt + "\"}");
                    //out.print("{\"jwt\": \"" + jwt + "\",\"id\":\""+rs.getInt(1)+"\"}");
                    out.flush();
                    rs.close();
                    return;
                }
            }

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
