package com.example.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.app.exception.resourceNotFoundException;
import com.example.app.entity.Product;
import com.example.app.entity.User;
import com.example.app.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;
	
	public Product addproduct(Product product) {
		return repo.save(product);
	}
	
	public List<Product> getallprods(){
		return repo.findAll();
	}
	
	public Product getproductbyid(Long id) {
		return repo.findById(id).orElseThrow(() -> new resourceNotFoundException("Product ot found with id "+id));
	}
	
	public Product updateproduct(Long id, Product updatedprod,User user) {
		Product product = getproductbyid(id);
		
		if(!product.getUser().getId().equals(user.getId())){
			throw new RuntimeException("you are not allowed to update this product");
		}
		
		product.setName(updatedprod.getName());
		product.setDescription(updatedprod.getDescription());
		product.setPrice(updatedprod.getPrice());
		
		return product;
	}
	
	public void deleteproduct(Long id,User user) {
		Product product = getproductbyid(id);
		if(!product.getUser().getId().equals(user.getId())){
			throw new RuntimeException("you are not allowed to delete this product");
		}
		repo.delete(product);
	}
	
	public List<Product> getproductbyuser(com.example.app.entity.User user) {
		return repo.findByUser(user);
	}
	
}
