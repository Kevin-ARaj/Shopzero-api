package com.example.app.services;

import com.example.app.entity.*;
import com.example.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    public Orders placeOrder(User user) {
    	
        List<Cart> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double total = 0;
        
        List<Product> bought = new ArrayList<>();
        
        for (Cart item : cartItems) {
        	bought.add(item.getProduct());
            total += item.getProduct().getPrice() * item.getQuantity();
        }

        Orders order = new Orders();
        order.setUser(user);
        order.setProducts(bought);
        order.setOrderDate(new Date());
        order.setTotalAmount(total);

        Orders savedOrder = orderRepository.save(order);

        cartRepository.deleteAll(cartItems);

        return savedOrder;
    }

    public List<Orders> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
}