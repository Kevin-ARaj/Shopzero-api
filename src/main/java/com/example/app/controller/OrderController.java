package com.example.app.controller;

import com.example.app.entity.Orders;
import com.example.app.entity.Product;
import com.example.app.entity.User;
import com.example.app.services.OrderService;
import com.example.app.services.UserService;
import com.example.app.config.JwtUtil;
import com.example.app.dto.OrGetRes;
import com.example.app.dto.OrderResponse;
import com.example.app.dto.ProductResponse;
import com.example.app.dto.UserResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public OrderResponse placeOrder(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);
        Orders order = new Orders();
        order = orderService.placeOrder(user);
        
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setProducts(order.getProduct());
        response.setTotalAmount(order.getTotalAmount());
        User orderuser = order.getUser();
        UserResponse res = new UserResponse();
	        res.setId(orderuser.getId());
	        res.setEmail(orderuser.getEmail());
	        res.setName(orderuser.getName());
        response.setUser(res);

        return response;
    }

    @GetMapping
    public List<OrGetRes> getOrders(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);
        
        List<Orders> list = orderService.getUserOrders(user);
        
        List<OrGetRes> response = new java.util.ArrayList<>();
        
        
        for(Orders order : list) {
        	OrGetRes res = new OrGetRes();
        	res.setId(order.getId());
        	res.setOrderDate(order.getOrderDate());
        	
        	List<Product> li = order.getProduct();
        	List<ProductResponse> prods = new ArrayList<>();
        	for(Product prod : li){
        		ProductResponse pro = new ProductResponse();
        		pro.setAvailability(prod.isAvailablity());
        		pro.setBrand(prod.getBrand());
        		pro.setDescription(prod.getDescription());
        		pro.setDiscount(prod.getDiscount());
        		pro.setId(prod.getId());
        		pro.setImage(prod.getImage());
        		pro.setName(prod.getName());
        		pro.setPrice(prod.getPrice());
        		pro.setRating(prod.getRating());
        	prods.add(pro);
        	};
        	
        	res.setProducts(prods);
        	
        	res.setTotalAmount(order.getTotalAmount());
	        	User orderuser = order.getUser();
	            UserResponse resp = new UserResponse();
	    	        resp.setId(orderuser.getId());
	    	        resp.setEmail(orderuser.getEmail());
	    	        resp.setName(orderuser.getName());
        	res.setUser(resp);
        	response.add(res);
        }	
	
        return response;
    }
}