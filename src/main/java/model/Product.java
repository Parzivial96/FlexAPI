//$Id$
package model;

import java.util.List;

public class Product {
	
	private int id;
	
	private String name;
	
	private String category;
	
	private String gender;
	
	private String age;
	
	private Double price;
	
	private Double discount;
	
	private int size;
	
	private int color;
	
	private int sellerId;
	
	private String dp;
	
	

	public Product(int id, String name, String category, String gender, String age, Double price, Double discount, int size, int color, int sellerId, String dp) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.gender = gender;
		this.age = age;
		this.price = price;
		this.discount = discount;
		this.size = size;
		this.color = color;
		this.sellerId = sellerId;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", category=" + category + ", gender=" + gender + ", age=" + age + ", price=" + price + ", discount=" + discount + ", size=" + size + ", color=" + color + "]";
	}
	
	
}
