package com.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contact.model.User;
import com.contact.repository.UserRepo;
@Service
public class UserService {
	@Autowired
private UserRepo userRepo;
	//process register form service handler
	public User register(User user) {
		return userRepo.save(user);
	}
}
