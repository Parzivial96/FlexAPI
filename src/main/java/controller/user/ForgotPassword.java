package controller.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import repository.DbConnector;

import java.util.Properties;  
import javax.mail.*;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  


@WebServlet("/forgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
    public void init() throws ServletException {
        super.init();
        // Enable TLSv1.2
        System.setProperty("https.protocols", "TLSv1.2");
    }
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String to = request.getParameter("to");
        String subject = "Forgot Password Request";
        String messageContent = "";

        String query = "Select email,password from user where email = ?";
        
        
        final String username = "flex.handler@gmail.com";
        final String password = "hmkhnxhokuotztfv";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

        try {
        	Connection connection = DbConnector.getConnection();
        	PreparedStatement statement = connection.prepareStatement(query);
        	statement.setString(1, to);
        	ResultSet rs = statement.executeQuery();
        	while(rs.next()) {
        		messageContent += "Email : "+rs.getString("email")+"\nPassword : "+rs.getString("password");
        	}
        	
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);

            response.getWriter().println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
			e.printStackTrace();
		}
	    
	}
}