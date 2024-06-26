package controller.user;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.User;
import repository.DbConnector;

@WebServlet("/updateUser")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String role = request.getParameter("role");
			String password = request.getParameter("password");
			String encodedDp = null;
			
			try {
			Part dpPart = request.getPart("dp");
			if(dpPart != null) {
				InputStream inputStream = dpPart.getInputStream();
				encodedDp = User.encodeToBase64(inputStream);
			}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			String query = "UPDATE user SET ";
			
			try {
				Connection connection = DbConnector.getConnection();
				boolean first = true;
				
				if(name!=null && !name.isEmpty()) {
					query += "name = ?";
					first = false;
				}
				
				if(email!=null && !email.isEmpty()) {
					if(!first) {
						query += ", ";
					}
					query += "email = ?";
					first = false;
				}
				
				if(role!=null && !role.isEmpty()) {
					if(!first) {
						query += ", ";
					}
					query += "role = ?";
					first = false;
				}

				if(password!=null && !password.isEmpty()) {
					if(!first) {
						query += ", ";
					}
					query += "password = ?";
					first = false;
				}
				
				if(encodedDp!=null && !encodedDp.isEmpty()) {
					if(!first) {
						query += ", ";
					}
					query += "dp = ?";
					first = false;
				}
				
				query += " WHERE id = ?";
			
				PreparedStatement statement = connection.prepareStatement(query);
				int index = 1;
				
				if(name!=null && !name.isEmpty()) {
					statement.setString(index++, name);
				}

				if(email!=null && !email.isEmpty()) {
					statement.setString(index++, email);
				}
				
				if(role!=null && !role.isEmpty()) {
					statement.setString(index++, role);
				}
				
				if(password!=null && !password.isEmpty()) {
					statement.setString(index++, password);
				}
				
				if(encodedDp!=null && !encodedDp.isEmpty()) {
					statement.setString(index++, encodedDp);
				}
				
				statement.setInt(index, id);
				
				if(statement.executeUpdate()>0) {
					response.setStatus(HttpServletResponse.SC_OK, "Updated User Successfully");
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Update User");
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

}
