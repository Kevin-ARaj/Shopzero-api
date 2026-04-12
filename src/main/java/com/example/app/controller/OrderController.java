package com.example.app.controller;

import com.example.app.entity.Orders;
import com.example.app.entity.User;
import com.example.app.services.OrderService;
import com.example.app.services.UserService;
import com.example.app.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Orders placeOrder(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);

        return orderService.placeOrder(user);
    }

    @GetMapping
    public List<Orders> getOrders(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);

        return orderService.getUserOrders(user);
    }
}