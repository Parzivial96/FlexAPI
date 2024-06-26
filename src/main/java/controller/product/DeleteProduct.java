package controller.product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.DbConnector;

@WebServlet("/deleteProduct")
public class DeleteProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		Integer id = request.getParameter("id")!=null ? Integer.parseInt(request.getParameter("id")):null;
		
		String query = "DELETE FROM products where id = ?";
		
		try {
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Deleted Product Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Delete Product");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
