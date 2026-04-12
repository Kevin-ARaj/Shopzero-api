package com.example.app.controller;

import org.springframework.web.bind.annotation.*;
import com.example.app.services.UserService;
import com.example.app.config.JwtUtil;
import com.example.app.dto.LoginRequest;
import com.example.app.dto.LoginResponse;
import com.example.app.dto.UserRequest;
import com.example.app.dto.UserResponse;
import com.example.app.entity.User;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService serv;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@PostMapping("/register")
	public UserResponse register(@Valid @RequestBody UserRequest request) {
		
		User user =  new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		
		User saveduser = serv.newuser(user);
		
		UserResponse response = new UserResponse();
		
		response.setId(saveduser.getId());
		response.setName(saveduser.getName());
		response.setEmail(saveduser.getEmail());
		
		return response;
	}
	
	@GetMapping
    public List<UserResponse> getAllUsers() {
		List <User> users = serv.getAll();
		List <UserResponse> responseList = new ArrayList<>();
		
		for (User user: users) {
			UserResponse res = new UserResponse();
			res.setId(user.getId());
			res.setName(user.getName());
			res.setEmail(user.getEmail());
			responseList.add(res);
		}
        return responseList;
    }
	
	@GetMapping("/{id}")
	public UserResponse getbyid(@PathVariable Long id) {
		User user = serv.findById(id);
		
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setName(user.getName());
		response.setEmail(user.getEmail());
		
		return response;
	} 
	
	@PutMapping("/{id}")
	public UserResponse update(@PathVariable Long id , @RequestBody UserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		
		User updatedUser  = serv.updateUser(id, user);
		
		UserResponse updated = new UserResponse();
		updated.setId(updatedUser.getId());
		updated.setName(updatedUser.getName());
		updated.setEmail(updatedUser.getEmail());
		
		return updated;	
	}
	
	@DeleteMapping("/{id}")
	public org.springframework.http.ResponseEntity<String> deleteuser(@PathVariable Long id) {
		serv.deleteUser(id);
		return org.springframework.http.ResponseEntity.ok("user deleted");
	} 
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {
		User user = serv.login(request.getEmail(), request.getPassword());
		
		String token = jwtUtil.generateTokens(user.getEmail());
		LoginResponse res = new LoginResponse();
		res.setId(user.getId());
		res.setName(user.getName());
		res.setEmail(user.getEmail());
		res.setToken(token);
		
		return res;
	}
}
