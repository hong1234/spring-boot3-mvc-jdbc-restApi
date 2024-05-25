package com.hong.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

@Controller
public class HomeController {
    
    @GetMapping("/")
	public String index(Authentication authentication, Model model) {
		model.addAttribute("userName", authentication.getName());
		return "index";
	}

	@GetMapping("/home")
	public String home(Authentication authentication, Model model) {
		model.addAttribute("userName", authentication.getName());
		return "home";
	}

}
