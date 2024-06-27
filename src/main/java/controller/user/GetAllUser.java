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

@WebServlet("/getAllUser")
public class GetAllUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		

		List<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM user";
		
		try {	
			
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

}
