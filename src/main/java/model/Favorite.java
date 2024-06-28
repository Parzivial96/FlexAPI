//$Id$
package model;

public class Favorite {
	
	private int id;
	
	private int userId;
	
	private int productId;
	
	private String name;
	
	private String dp;
	
	private Double price;
	
	private Double discount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDp() {
		return dp;
	}

	public void setDp(String dp) {
		this.dp = dp;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "Favorite [id=" + id + ", userId=" + userId + ", productId=" + productId + ", name=" + name + ", dp=" + dp + ", price=" + price + ", discount=" + discount + "]";
	}
	
	
}
