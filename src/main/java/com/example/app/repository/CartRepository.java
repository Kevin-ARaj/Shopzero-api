package com.example.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app.entity.Cart;
import com.example.app.entity.Product;
import com.example.app.entity.User;

public interface CartRepository extends JpaRepository<Cart ,Long>{
	
	 List<Cart> findByUser(User user);
	 Optional<Cart> findByUserAndProduct(User user, Product product);
}
