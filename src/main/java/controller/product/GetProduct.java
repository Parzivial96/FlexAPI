package controller.product;

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

import model.Product;
import model.User;
import repository.DbConnector;

@WebServlet("/getProduct")
public class GetProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String query = "SELECT * FROM products";
		List<Product> productList = new ArrayList<Product>();
		
		if(id!=null && !id.isEmpty()) {
			query += " WHERE id = ?";
		}
		
		try {
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			if(id!=null && !id.isEmpty()) {
				statement.setInt(1, Integer.parseInt(id));
			}
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				productList.add(new Product(rs.getInt("id"),rs.getString("name"),rs.getString("category"),rs.getString("gender"),rs.getString("age"),rs.getDouble("price"),rs.getDouble("discount"),rs.getInt("size"),rs.getInt("colors"),rs.getInt("sellerId"),rs.getString("dp")));
			}
			
			rs.close();
			
			String userJsonString = new Gson().toJson(productList);
			
			PrintWriter out = response.getWriter();
			out.print(userJsonString);
			out.flush();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
