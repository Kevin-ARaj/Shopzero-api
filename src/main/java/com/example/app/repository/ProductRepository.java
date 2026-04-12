package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.app.entity.Product;

public interface ProductRepository extends JpaRepository<Product ,Long>{
	java.util.List<Product> findByUser(com.example.app.entity.User user); 

}
