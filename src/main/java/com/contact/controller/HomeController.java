package com.contact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class HomeController {
//home handler
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title","HOME . Contact manager");
		return "home";
	}
//about handler
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title","ABOUT . Contact manager");
		return "about";
	}
	

}
