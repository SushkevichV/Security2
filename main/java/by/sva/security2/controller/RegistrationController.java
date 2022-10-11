package by.sva.security2.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import by.sva.security2.entity.User;
import by.sva.security2.service.UserService;

@Controller
public class RegistrationController {
	
	private final UserService userService;
	
	// или использовать @Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userform", new User());
		return "registration";
	}
	
	@PostMapping("/registration")
	public String addUser(@ModelAttribute("userform") @Valid User userForm, BindingResult bindingResult, Model model) { // BindingResult (ошибки валидации) всегда должен идти перед Model
		
		if(bindingResult.hasErrors()) { // если поля заполнены некорректно, возврат на страницу регистрации
			return "registration";
		}
		
		if(!userForm.getPassword().equals(userForm.getPassConfirm())) {
			model.addAttribute("passwordError", "Пароли не совпадают");
			return "registration";
		}
		
		if(!userService.addUser(userForm)) {
			model.addAttribute("usernameError", "Пользователь именем уже существует");
			return "registration";
		}
		
		return "redirect:/";
	}

}
