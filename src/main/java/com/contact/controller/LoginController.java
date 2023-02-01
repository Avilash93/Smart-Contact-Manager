package com.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	@GetMapping("/signin")
public String getLoginPage(Model model) {
		model.addAttribute("title","Login . Contact manager");
	return "login";
}
}
