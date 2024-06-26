//$Id$
package model;

public class Order {
	
	private int id;
	
	private int userId;
	
	private int productId;
	
	private String name;
	
	private String dp;
	
	private Double productPrice;
	
	private Double productDiscount;
	
	private Double price;
	
	private Double discount;
	
	private String color;
	
	private String size;
	
	private int status;
	

	public Order(int id, int userId, int productId, Double price, Double discount,String color, String size, int status) {
		super();
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.price = price;
		this.discount = discount;
		this.color = color;
		this.size = size;
		this.status = status;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", productId=" + productId + ", price=" + price + ", discount=" + discount + ", color=" + color + ", size=" + size + ", status=" + status + "]";
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Double getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(Double productDiscount) {
		this.productDiscount = productDiscount;
	}
	
}
