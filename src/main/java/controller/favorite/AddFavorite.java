package controller.favorite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.DbConnector;

@WebServlet("/addFavorite")
public class AddFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String userId = (String) request.getAttribute("idToken");
		Integer productId = request.getParameter("productId")!=null ? Integer.parseInt(request.getParameter("productId")):null;
		
		
		String query = "INSERT INTO favorite (userId, productId) VALUES (?,?)";
		
		try {
			
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, userId);
			statement.setInt(2, productId);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Favorite Added Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Add Favorite");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
