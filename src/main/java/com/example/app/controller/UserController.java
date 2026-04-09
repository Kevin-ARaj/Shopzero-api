package com.example.app.controller;

import org.springframework.web.bind.annotation.*;
import com.example.app.services.UserService;
import com.example.app.entity.User;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService serv;
	
	static void justprint() {System.out.println("controller hit");} //debug
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		System.out.println("register hit"); //debug
		return serv.newuser(user);
	}
	
	@GetMapping
    public List<User> getAllUsers() {
        return serv.getAll();
    }
	
}
