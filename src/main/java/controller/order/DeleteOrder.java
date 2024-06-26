package controller.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.DbConnector;


@WebServlet("/deleteOrder")
public class DeleteOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		String query = "DELETE FROM orders where id = ?";
		
		try {
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Deleted Order Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Delete Order");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	
	}

}
