package controller.favorite;

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

import model.Favorite;
import repository.DbConnector;

@WebServlet("/getFavorite")
public class GetFavorite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String userId = (String) request.getAttribute("idToken");
        

        String query = "SELECT "
                + "f.id AS favoriteId, "
                + "f.productId AS favoriteProductId, "
                + "f.userId AS favoriteUserId, "
                + "p.id AS productId, "
                + "p.name AS productName, "
                + "p.dp AS productDp, "
                + "p.price AS productPrice, "
                + "p.discount AS productDiscount "
                + "FROM favorite f "
                + "JOIN products p ON f.productId = p.id";
        
        if(userId != null && !userId.isEmpty()) {
            query += " WHERE f.userId = ?";
        }

        List<Favorite> favoriteList = new ArrayList<Favorite>();
        
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if(userId != null && !userId.isEmpty()) {
                statement.setInt(1, Integer.parseInt(userId));
            }
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                Favorite favorite = new Favorite();
                favorite.setId(rs.getInt("favoriteId"));
                favorite.setUserId(rs.getInt("favoriteUserId"));
                favorite.setProductId(rs.getInt("favoriteProductId"));
                favorite.setName(rs.getString("productName"));
                favorite.setDp(rs.getString("productDp"));
                favorite.setPrice(rs.getDouble("productPrice"));
                favorite.setDiscount(rs.getDouble("productDiscount"));
                favoriteList.add(favorite);
            }
            
            rs.close();
            
            String favoriteJsonString = new Gson().toJson(favoriteList);
            
            PrintWriter out = response.getWriter();
            out.print(favoriteJsonString);
            out.flush();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
