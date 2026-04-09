package com.example.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.repository.UserRepository;
import com.example.app.entity.User;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	public User newuser(User user) {
		System.out.print("service hit"); //debug
		return repo.save(user);
	}

	public List<User> getAll() {
		return repo.findAll();
	}

}
