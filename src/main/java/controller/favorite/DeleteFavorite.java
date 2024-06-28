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

@WebServlet("/deleteFavorite")
public class DeleteFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		int productId = Integer.parseInt(request.getParameter("productId"));
		String userId = (String) request.getAttribute("idToken");
		
		String query = "DELETE FROM favorite where productId = ? AND userId = ?";
		
		try {
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, productId);
			statement.setString(2,  userId);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Deleted Favorite Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Delete Favorite");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
