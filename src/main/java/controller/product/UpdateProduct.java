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

@WebServlet("/updateProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("applicaion/json");
		response.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String category = request.getParameter("category");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		Double price = request.getParameter("price")!=null ? Double.parseDouble(request.getParameter("price")) : null;
		String size = request.getParameter("size");
		Double discount = request.getParameter("discount")!=null ? Double.parseDouble(request.getParameter("discount")) : null;
		String color = request.getParameter("color");
		String encodedDp = null;
		
		//System.out.println(name+" "+category+" "+gender+" "+age+" "+price+" "+size+" "+discount+" "+color+" "+id);
		
		try {
		Part dpPart = request.getPart("dp");
		if(dpPart != null) {
			InputStream inputStream = dpPart.getInputStream();
			encodedDp = User.encodeToBase64(inputStream);
		}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String query = "UPDATE products SET ";
		
		try {
			Connection connection = DbConnector.getConnection();
			boolean first = true;
			
			if(name!=null && !name.isEmpty()) {
				query += "name = ?";
				first = false;
			}
			
			if(category!=null && !category.isEmpty()) {
				if(!first) {
					query += ", ";
				}
				query += "category = ?";
				first = false;
			}
			
			if(gender!=null && !gender.isEmpty()) {
				if(!first) {
					query += ", ";
				}
				query += "gender = ?";
				first = false;
			}

			if(age!=null && !age.isEmpty()) {
				if(!first) {
					query += ", ";
				}
				query += "age = ?";
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
			
			if(size!=null) {
				if(!first) {
					query += ", ";
				}
				query += "size = ?";
				first = false;
			}
		
			if(color!=null) {
				if(!first) {
					query += ", ";
				}
				query += "colors = ?";
				first = false;
			}
			
			if(encodedDp!=null && !encodedDp.isEmpty()) {
				if(!first) {
					query += ", ";
				}
				query += "dp = ?";
				first = false;
			}
			
			query += " WHERE id = ?";
		
			PreparedStatement statement = connection.prepareStatement(query);
			int index = 1;
			
			if(name!=null && !name.isEmpty()) {
				statement.setString(index++, name);
			}

			if(category!=null && !category.isEmpty()) {
				statement.setString(index++, category);
			}
			
			if(gender!=null && !gender.isEmpty()) {
				statement.setString(index++, gender);
			}
			
			if(age!=null && !age.isEmpty()) {
				statement.setString(index++, age);
			}
			
			if(price!=null) {
				statement.setDouble(index++, price);
			}
			
			if(discount!=null) {
				statement.setDouble(index++, discount);
			}
			
			if(size!=null) {
				statement.setString(index++, size);
			}
			
			if(color!=null) {
				statement.setString(index++, color);
			}

			if(encodedDp!=null && !encodedDp.isEmpty()) {
				statement.setString(index++, encodedDp);
			}
			
			statement.setInt(index, id);
			
			if(statement.executeUpdate()>0) {
				response.setStatus(HttpServletResponse.SC_OK, "Updated Successfully");
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't Update User");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
