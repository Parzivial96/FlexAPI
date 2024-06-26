package controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import model.User;
import repository.DbConnector;

@WebServlet("/getUser")
public class GetUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		

		List<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM user";
		String userId = (String) request.getParameter("id");
		String idToken = (String) request.getAttribute("idToken");
		
		if(userId==null && idToken!=null && !idToken.isEmpty()) {
			userId = idToken;
		}
		
		try {	
			if(userId!=null && !userId.isEmpty()) {
				query = "SELECT * FROM user WHERE id = "+userId;
			}
			
			Connection connection = DbConnector.getConnection();
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			
			while(rs.next()) {
				userList.add(new User(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("role"),rs.getString("password"),rs.getString("dp")));
			}
			
			rs.close();
			
			String userJsonString = new Gson().toJson(userList);
			
			PrintWriter out = response.getWriter();
			out.print(userJsonString);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
