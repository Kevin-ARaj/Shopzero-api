package com.example.app.controller;

import com.example.app.entity.Cart;
import com.example.app.entity.User;
import com.example.app.services.CartService;
import com.example.app.services.UserService;
import com.example.app.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/{productId}")
    public Cart addToCart(@PathVariable Long productId,
                          @RequestParam int quantity,
                          @RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);

        return cartService.addToCart(user, productId, quantity);
    }


    @GetMapping
    public List<Cart> getCart(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);

        return cartService.getUserCart(user);
    }

    
    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable Long cartId,
                                 @RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);

        cartService.removeFromCart(cartId, user);

        return "Item removed";
    }

 
    @DeleteMapping("/clear")
    public String clearCart(@RequestHeader("Authorization") String header) {

        String token = header.substring(7);
        String email = jwtUtil.extractEmail(token);

        User user = userService.getuserbyemail(email);

        cartService.clearCart(user);

        return "Cart cleared";
    }
}