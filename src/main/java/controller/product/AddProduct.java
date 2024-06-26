package controller.product;

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

import model.User;
import repository.DbConnector;

@WebServlet("/addProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("applicaion/json");
		response.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		Double price = request.getParameter("price")!=null ? Double.parseDouble(request.getParameter("price")) : 0;
		String size = request.getParameter("size");
		Double discount = request.getParameter("discount")!=null ? Double.parseDouble(request.getParameter("discount")) : 0;
		String color = request.getParameter("color");
		String sellerId = (String) request.getAttribute("idToken");
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
		
		
		String query = "insert into products (name,category,gender,age,price,size,discount,colors,sellerId,dp) values(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			Connection connection = DbConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, category);
			statement.setString(3, gender);
			statement.setString(4, age);
			statement.setDouble(5, price);
			statement.setString(6, size);
			statement.setDouble(7, discount);
			statement.setString(8, color);
			statement.setString(9, sellerId);
			statement.setString(10, encodedDp);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Successfully Added Product");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Add Product");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
