package controller.order;

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

import model.Order;
import repository.DbConnector;

@WebServlet("/getOrder")
public class GetOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String userId = request.getParameter("userId");
        String idToken = (String) request.getAttribute("idToken");
        
        if(userId == null && idToken != null && !idToken.isEmpty()) {
            userId = idToken;
        }

        String query = "SELECT "
                + "o.id AS orderId, "
                + "o.price AS orderPrice, "
                + "o.size AS orderSize, "
                + "o.color AS orderColor, "
                + "o.discount AS orderDiscount, "
                + "o.status AS orderStatus, "          
                + "p.id AS productId, "
                + "p.name AS productName, "
                + "p.dp AS productDp, "
                + "p.price AS productPrice, "
                + "p.discount AS productDiscount "
                + "FROM orders o "
                + "JOIN products p ON o.productId = p.id";
        
        if(userId != null && !userId.isEmpty()) {
            query += " WHERE o.userId = ?";
        }

        List<Order> orderList = new ArrayList<Order>();
        
        try (Connection connection = DbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            if(userId != null && !userId.isEmpty()) {
                statement.setInt(1, Integer.parseInt(userId));
            }
            
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()) {
                Order order = new Order(
                    rs.getInt("orderId"),
                    Integer.parseInt(userId),
                    rs.getInt("productId"),
                    rs.getDouble("orderPrice"),
                    rs.getDouble("orderDiscount"),
                    rs.getString("orderColor"),
                    rs.getString("orderSize"),
                    rs.getInt("orderStatus")
                );         
                order.setName(rs.getString("productName"));
                order.setDp(rs.getString("productDp"));
                order.setProductPrice(rs.getDouble("productPrice"));
                order.setProductDiscount(rs.getDouble("productDiscount"));
                orderList.add(order);
            }
            
            rs.close();
            
            String orderJsonString = new Gson().toJson(orderList);
            
            PrintWriter out = response.getWriter();
            out.print(orderJsonString);
            out.flush();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
