package com.springapp.mvc.controller;

import com.springapp.mvc.domain.User;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class UserController {


	@Autowired
	UserService userService;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userCabinet(HttpServletRequest request, ModelMap model) {
		Integer userId = (Integer) request.getSession().getAttribute("userID");
		User user = userService.getUserById(userId);
		model.addAttribute("username", user.getUserName());
		model.addAttribute("password", user.getPassword());
		model.addAttribute("nickname", user.getNickName());
		model.addAttribute("email", user.geteMail());
		model.addAttribute("userrole", userService.getUserRole(userId));
		return "userProfile";
	}

	@RequestMapping(value = "/userEdit", method = RequestMethod.POST)
	public  String userEdit(HttpServletRequest request, ModelMap model, @Valid User user){
		Integer userId = (Integer) request.getSession().getAttribute("userID");
		userService.updateUser(user, userId);
		User updateUser = userService.getUserById(userId);
//		model.addAttribute("username", updateUser.getUserName());
		model.addAttribute("password", updateUser.getPassword());
		model.addAttribute("nickname", updateUser.getNickName());
		model.addAttribute("email", updateUser.geteMail());
		model.addAttribute("userrole", userService.getUserRole(userId));
		return "userProfile";
	}

	@RequestMapping(value = "/takeJoke", method = RequestMethod.GET)
	@ResponseBody
	public String takeJoke(){
		return userService.takeJoke();
	}

}