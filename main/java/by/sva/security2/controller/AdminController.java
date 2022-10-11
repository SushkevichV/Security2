package by.sva.security2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.sva.security2.service.UserService;

@Controller
public class AdminController {
	@Autowired
	private UserService userService;
	/*
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	*/
	@GetMapping("/admin")
	public String userList(Model model) {
		model.addAttribute("allUsers", userService.findAllUsers());
		return "admin";
	}
	
	@PostMapping("/admin")
	public String deleteUser(@RequestParam(required = true, defaultValue = "") Long id,
						@RequestParam(required = true, defaultValue = "") String action,
						Model model){
		if(action.equals("delete")) {
			userService.deleteUser(id);
		}
		
		return "redirect:/admin";
	}
	
	@GetMapping("admin/gt/{id}")
	public String gtUser(@PathVariable("id") long id, Model model) {
		model.addAttribute("allUsers", userService.usergtList(id));
		return "admin";
	}
	
	@GetMapping
	public String start() {
		return "index";
	}

}
