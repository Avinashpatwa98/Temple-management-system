package com.springboot.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot.web.dao.UserRepository;
//import com.springboot.smart.helper.Message;

import com.springboot.web.entities.User;

//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserRepository userRepository;

	@GetMapping("/index")
	public String login(Model model, Principal principal) {

		//try {
			String username = principal.getName();
		//	if(userName==null) {
		//		System.out.println("user is null");
		//	}
			System.out.println("USERNAME" + username);

			// get the user using username(Email)
			User user = userRepository.getUserByUserName(username);
			
			System.out.println("USER" + user);

			model.addAttribute("user", user);

		    model.addAttribute("title", "login_contect managment ");
		
	//	} catch (Exception e) {
	//		// TODO: handle exception
	//		e.printStackTrace();
	//	}

			return "normal/welcome";
	

	}

}
