package com.example.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.example.app.config.JwtUtil;
import com.example.app.dto.ProductResponse;
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
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/add")
	public ProductResponse addproduct(@Valid @RequestBody Product product,@RequestHeader("Authorization") String header) {
		String token = header.substring(7);
		String email = jwtutil.extractEmail(token);
		User user = UserServ.getuserbyemail(email);
		Product prod = new Product();
		prod = serv.addproduct(product);
		
		ProductResponse response = new ProductResponse();
		response.setAvailability(prod.isAvailablity());
		response.setBrand(prod.getBrand());
		response.setDescription(prod.getDescription());
		response.setDiscount(prod.getDiscount());
		response.setId(prod.getId());
		response.setImage(prod.getImage());
		response.setName(prod.getName());
		response.setPrice(prod.getPrice());
		response.setRating(prod.getRating());
//		response.setUser_id(user.getId());
		
		return response;
	} 

	@GetMapping  
	public List<ProductResponse> getallproducts(){
		
		List <Product> products = new ArrayList<> ();
		products = serv.getallprods();
		List <ProductResponse> response = new ArrayList<> ();
		User user = new User();
		for(Product prods : products) {
			ProductResponse prod = new ProductResponse();
			user = prods.getUser();
			prod.setAvailability(prods.isAvailablity());
			prod.setBrand(prods.getBrand());
			prod.setDescription(prods.getDescription());
			prod.setDiscount(prods.getDiscount());
			prod.setId(prods.getId());
			prod.setImage(prods.getImage());
			prod.setName(prods.getName());
			prod.setPrice(prods.getPrice());
			prod.setRating(prods.getRating());
//			prod.setUser_id(user.getId());
			
			response.add(prod);			
		}
		return response;
	}
	
	@GetMapping("/{id}")
	public ProductResponse getbyid(@PathVariable Long id) {
		
		Product prod  = new Product();
		prod = serv.getproductbyid(id);
		ProductResponse response = new ProductResponse();
		
		response.setAvailability(prod.isAvailablity());
		response.setBrand(prod.getBrand());
		response.setDescription(prod.getDescription());
		response.setDiscount(prod.getDiscount());
		response.setId(prod.getId());
		response.setImage(prod.getImage());
		response.setName(prod.getName());
		response.setPrice(prod.getPrice());
		response.setRating(prod.getRating());
//		response.setUser_id(prod.getUser().getId());
		
		return response;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ProductResponse updateproduct(@PathVariable Long id,@Valid @RequestBody Product updatedprod,@RequestHeader("Authorization") String header) {
		String token = header.substring(7);
		String email= jwtutil.extractEmail(token);
		User user = UserServ.getuserbyemail(email);
		Product prod  = new Product();
		prod = serv.updateproduct(id, updatedprod,user);
		ProductResponse response = new ProductResponse();
		response.setAvailability(prod.isAvailablity());
		response.setBrand(prod.getBrand());
		response.setDescription(prod.getDescription());
		response.setDiscount(prod.getDiscount());
		response.setId(prod.getId());
		response.setImage(prod.getImage());
		response.setName(prod.getName());
		response.setPrice(prod.getPrice());
		response.setRating(prod.getRating());
//		response.setUser_id(prod.getUser().getId());
		
		return response;
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,String>> deleteproduct(@PathVariable Long id,@RequestHeader("Authorization") String header) {
		String token = header.substring(7);
		String email = jwtutil.extractEmail(token);
		User user = UserServ.getuserbyemail(email);
		serv.deleteproduct(id,user);
		return ResponseEntity.ok(Map.of("Message","Item deleted"));
	}
	
	@GetMapping("/my") 
	public List<ProductResponse> getmyproducts(@RequestHeader("Authorization") String header){
		String token = header.substring(7);
		String email = jwtutil.extractEmail(token);
		
		User user = UserServ.getuserbyemail(email);
		user.setEmail(email);
		List<Product> request = new ArrayList<>();
		request = serv.getproductbyuser(user);
		List<ProductResponse> response = new ArrayList<>();
		for(Product prod : request ) {
			ProductResponse res = new ProductResponse();
				res.setAvailability(prod.isAvailablity());
				res.setBrand(prod.getBrand());
				res.setDescription(prod.getDescription());
				res.setDiscount(prod.getDiscount());
				res.setId(prod.getId());
				res.setImage(prod.getImage());
				res.setName(prod.getName());
				res.setPrice(prod.getPrice());
				res.setRating(prod.getRating());
			response.add(res);
		}
		
		return response;
	}
}
