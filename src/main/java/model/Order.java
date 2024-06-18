//$Id$
package model;

import java.util.List;

class OrderDetails {
	
	int id;
	
	int productId;
	
	String price;
	
	String color;
	
	String size;
}

public class Order {
	
	private String email;
	
	private List<OrderDetails> products;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<OrderDetails> getProducts() {
		return products;
	}

	public void setProducts(List<OrderDetails> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Order [email=" + email + ", products=" + products + "]";
	}
	
	
	
}
