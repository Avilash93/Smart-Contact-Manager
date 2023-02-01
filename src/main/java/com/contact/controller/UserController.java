package com.contact.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contact.model.User;
import com.contact.repository.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepo repo;
	
	@GetMapping("/")
public String profile(Model model,Principal principal) {
		//Principle is used to fetch current logged in user details.
		User user = repo.getUserByEmail(principal.getName());
		model.addAttribute("user",user);
	return "user/userdashboard";
}
}
