package com.example.app.services;

import java.util.List;
import com.example.app.exception.resourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.app.repository.UserRepository;
import com.example.app.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User newuser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repo.save(user);
	}

	public List<User> getAll() {
		return repo.findAll();
	}
	
	public User findById(Long id) {
		return repo.findById(id).orElseThrow(() -> new resourceNotFoundException("user not found with id" + id));
	}
	
	public User updateUser(Long id, User updateduser) {
		User user = repo.findById(id).orElseThrow(()-> new resourceNotFoundException("usernot found with id " + id));
		if(user != null) {
			user.setName(updateduser.getName());
			user.setEmail(updateduser.getEmail());
			user.setPassword(updateduser.getPassword());
			return user;
		}
		return null;
	}
	
	public void deleteUser(Long id) {
		User user = repo.findById(id).orElseThrow(()-> new resourceNotFoundException("usernot found with id " + id));
		repo.delete(user);
	}
	
	public User login(String email,String password) {
		User user = repo.findByEmail(email);
		
		if(user != null && passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}
		throw new RuntimeException("Invalid email or password");
	}
	
	public User getuserbyemail(String email) {
		return repo.findByEmail(email);
	}

}
