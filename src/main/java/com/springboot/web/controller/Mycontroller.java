package com.springboot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springboot.web.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import com.springboot.smart.helper.Message;
import com.springboot.web.dao.UserRepository;
//import com.springboot.web.entities.User;

@Controller
public class Mycontroller {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - contact_Managment");
		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "about - contact_Managment");
		return "about";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - contact_Managment");
		model.addAttribute("user", new User());
		return "signup";
	}

	// handler for register user mapping
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
//	@RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			Model model,HttpSession session)
	// value automatically filled according to match user field.
	{
		try {
//			if (!agreement) {
//				// System.out.println("you have not agreed the terms and conditions");
//				throw new Exception("condition must be stisfied");
//			}

			if (result1.hasErrors()) {
				System.out.println("ERROR" + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");// we need Dao object to save user data(userRepository object)
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Agreement");
			System.out.println("USER" + user);

			User result = this.userRepository.save(user);// save into data base contact.
			model.addAttribute("user", new User());// data return again from user by line 56 in user
			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("something went wrong!!" + e.getMessage(), "alert-denger"));
			return "signup";
		}
	}

	@GetMapping("/signin")
	public String costomlogin(Model model) {
		model.addAttribute("title", "Login - contact_Managment");
		model.addAttribute("user", new User());
		return "login";
	}

//	@GetMapping("/welcome")
//	public String login(Model model, Principal principal) {
//
//		String username = principal.getName();
//		User user = userRepository.getUserByUserName(username);
//		model.addAttribute("user", user);
//		model.addAttribute("title", "login_contect managment ");
//
//		return "normal/welcome";
//	}
}
