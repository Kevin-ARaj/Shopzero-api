package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.app.config.JwtUtil;
import com.example.app.entity.Product;
import com.example.app.entity.User;
import com.example.app.services.ProductService;
import com.example.app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
		
	@Autowired
	private ProductService serv;	
	
	@Autowired
	private UserService UserServ;
	
	@Autowired
	private JwtUtil jwtutil;
	
	@PostMapping
	public Product addproduct(@Valid @RequestBody Product product,@RequestHeader("authorization") String header) {
		String token = header.substring(7);
		String email = jwtutil.extractEmail(token);
		
		User user = UserServ.getuserbyemail(email);
		
		product.setUser(user);
		return serv.addproduct(product);
	} 

	@GetMapping
	public java.util.List<Product> getallproducts(){
		return serv.getallprods();
	}
	
	@GetMapping("/{id}")
	public Product getbyid(@PathVariable Long id) {
		return serv.getproductbyid(id);
	}
	
	@PutMapping("/{id}")
	public Product updateproduct(@PathVariable Long id,@Valid @RequestBody Product updatedprod,@RequestHeader("Authorization") String header) {
		String token = header.substring(7);
		String email= jwtutil.extractEmail(token);
		User user = UserServ.getuserbyemail(email);
		return serv.updateproduct(id, updatedprod,user);
	}

	@DeleteMapping("/{id}")
	public String deleteproduct(@PathVariable Long id,@RequestHeader("Authorization") String header) {
		String token = header.substring(7);
		String email = jwtutil.extractEmail(token);
		User user = UserServ.getuserbyemail(email);
		serv.deleteproduct(id,user);
		return "product deleted";
	}
	
	@GetMapping("/my")
	public java.util.List<Product> getmyproducts(@RequestHeader("Authorization") String header){
		String token = header.substring(7);
		String email = jwtutil.extractEmail(token);
		
		User user = UserServ.getuserbyemail(email);
		user.setEmail(email);
		
		return serv.getproductbyuser(user);
	}
}
