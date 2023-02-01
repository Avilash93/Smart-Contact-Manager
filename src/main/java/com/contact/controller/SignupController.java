package com.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.contact.helper.Message;
import com.contact.model.User;
import com.contact.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@Controller

public class SignupController {
	@Autowired
	private UserService userService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	//sign up handler
			@GetMapping("/signup")
			public String signup(Model model) {
				model.addAttribute("title","SIGN UP . Contact manager");
				model.addAttribute("user",new User());
				return "signup";
			}
	
		//process register form handler
		@PostMapping("/do-register")
		public String doRegister(@Valid @ModelAttribute("user")User user,BindingResult result,@RequestParam(value = "agreement",defaultValue = "false" ) boolean agreement,Model model,HttpSession session) {
		//The 'request','session','servletContext' and 'response' expression utility objects are no longer available by default for template expressions and their use is not recommended
			//These objects are not directly available in templates in Thymeleaf 3.1 for security reasons. The recommended way to make this information available to templates is to add the specific pieces of information that are really needed by the template as context variables (model attributes in Spring).
			model.addAttribute("httpSession",session);
			
			try {
				if(!agreement) {
					throw new Exception("You didn't agree terms and condition.");
				}
				if(result.hasErrors()) {
					System.out.println(result.getAllErrors());
					model.addAttribute("user",user);
					return "signup";
				}
				user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
				user.setEnable(true);
				user.setRole("ROLE_USER");
				userService.register(user);
				model.addAttribute("user",new User());
				session.setAttribute("msg", new Message("Successfully registerd", "alert-success"));
				
				
				model.addAttribute("user",user);
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("user",user);
				session.setAttribute("msg", new Message(e.getMessage(), "alert-danger"));
				
				return "signup";
			}
			return "signup";
		}
}
