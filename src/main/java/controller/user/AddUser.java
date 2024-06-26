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

import repository.DbConnector;

import model.User;

@WebServlet("/addUser")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
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
		
		String query = "insert into user (name,email,role,password,dp) values (?,?,?,?,?);";
		
		try {
			
			Connection connection = DbConnector.getConnection();
			PreparedStatement st = connection.prepareStatement(query);
			
			st.setString(1, name);
			st.setString(2, email);
			st.setString(3, role);
			st.setString(4, password);
			st.setString(5, encodedDp);
			if(st.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK,"Added User Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Can't Add User");
			}
					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}	

}
