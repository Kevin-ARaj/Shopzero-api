package com.example.app.services;

import com.example.app.entity.Cart;
import com.example.app.entity.Product;
import com.example.app.entity.User;
import com.example.app.repository.CartRepository;
import com.example.app.repository.ProductRepository;
import com.example.app.exception.resourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart addToCart(User user, Long productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new resourceNotFoundException("Product not found"));

        Cart cart = cartRepository.findByUserAndProduct(user, product)
                .orElse(null);

        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantity);
        } else {
            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    
    public List<Cart> getUserCart(User user) {
        return cartRepository.findByUser(user);
    }

    
    public void removeFromCart(Long cartId, User user) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new resourceNotFoundException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Not allowed");
        }
        cartRepository.delete(cart);
    }

    
    public void clearCart(User user) {
        List<Cart> items = cartRepository.findByUser(user);
        cartRepository.deleteAll(items);
    }
}