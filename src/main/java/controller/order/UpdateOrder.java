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

@WebServlet("/updateOrder")
public class UpdateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		Integer userId = request.getParameter("userId")!=null ? Integer.parseInt(request.getParameter("userId")) : null;
		Integer productId = request.getParameter("productId")!=null ? Integer.parseInt(request.getParameter("productId")) : null;
		Double price = request.getParameter("price")!=null ? Double.parseDouble(request.getParameter("price")) : null;
		Double discount = request.getParameter("discount")!=null ? Double.parseDouble(request.getParameter("discount")) : null;
		String color = request.getParameter("color");
		String size = request.getParameter("size");
		Integer status = request.getParameter("status")!=null ? Integer.parseInt(request.getParameter("status")) : null;
		
		String query = "UPDATE orders SET ";
		
		try {
			Connection connection = DbConnector.getConnection();
			boolean first = true;
			
			if(userId!=null) {
				query += "userId = ?";
				first = false;
			}
			
			if(productId!=null) {
				if(!first) {
					query += ", ";
				}
				query += "productId = ?";
				first = false;
			}
			
			if(price!=null) {
				if(!first) {
					query += ", ";
				}
				query += "price = ?";
				first = false;
			}

			if(discount!=null) {
				if(!first) {
					query += ", ";
				}
				query += "discount = ?";
				first = false;
			}
			
			if(color!=null) {
				if(!first) {
					query += ", ";
				}
				query += "color = ?";
				first = false;
			}
			
			if(size!=null) {
				if(!first) {
					query += ", ";
				}
				query += "size = ?";
				first = false;
			}
			
			if(status!=null) {
				if(!first) {
					query += ", ";
				}
				query += "status = ?";
				first = false;
			}
			
			query += " WHERE id = ?";
		
			PreparedStatement statement = connection.prepareStatement(query);
			int index = 1;
			
			if(userId!=null) {
				statement.setInt(index++, userId);
			}

			if(productId!=null) {
				statement.setInt(index++, productId);
			}
			
			if(price!=null) {
				statement.setDouble(index++, price);
			}
			
			if(discount!=null) {
				statement.setDouble(index++, discount);
			}
			
			if(color!=null) {
				statement.setString(index++, color);
			}
			
			if(size!=null) {
				statement.setString(index++, size);
			}
			
			if(status!=null) {
				statement.setInt(index++, status);
			}
			
			statement.setInt(index, id);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Updated Order Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Update Order");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
