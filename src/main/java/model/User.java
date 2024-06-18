//$Id$
package model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.util.Base64;

public class User {

	private int id;
	
	private String name;
	
	private String email;

	private String role;
	
	private String password;

	private String dp;
	
	
	
	public User(int id, String name, String email, String role, String password, String dp) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
		this.dp = dp;
	}

	public User(String name, String email, String role, String password, String dp) {
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
		this.dp = dp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDp() {
		return dp;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + ", password=" + password + ", dp=" + dp + "]";
	}


	public static String encodeToBase64(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }	
	
}
