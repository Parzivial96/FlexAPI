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


@WebServlet("/addOrder")
public class AddOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String userId = (String) request.getAttribute("idToken");
		Integer productId = request.getParameter("productId")!=null ? Integer.parseInt(request.getParameter("productId")):null;
		//Double price = request.getParameter("price")!=null ? Double.parseDouble(request.getParameter("price")) : null;
		//Double discount = request.getParameter("discount")!=null ? Double.parseDouble(request.getParameter("discount")) : null;
		String color = request.getParameter("color");
		String size = request.getParameter("size");
		Integer status = 0;
		
		
		String query = "INSERT INTO orders (userId, productId, color, size, status) VALUES (?,?,?,?,?)";
		
		try {
			
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, userId);
			statement.setInt(2, productId);
			statement.setString(3, color);
			statement.setString(4, size);
			statement.setInt(5, status);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Order Added Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Add Order");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
