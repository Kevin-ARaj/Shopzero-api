package com.example.app.dto;

import java.util.Date;

import com.example.app.entity.Product;

public class OrderResponse {
	private Long id;
	private UserResponse user;
	private java.util.List<Product> products;
	private Date orderDate;
    private double totalAmount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	public java.util.List<Product> getProducts() {
		return products;
	}
	public void setProducts(java.util.List<Product> products) {
		this.products = products;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
    
    
}
