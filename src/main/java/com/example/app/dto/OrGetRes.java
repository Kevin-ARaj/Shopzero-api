package com.example.app.dto;

import java.util.Date;


public class OrGetRes {
	private Long id;
	private UserResponse user;
	private java.util.List<ProductResponse> products;
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
	public java.util.List<ProductResponse> getProducts() {
		return products;
	}
	public void setProducts(java.util.List<ProductResponse> products) {
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
