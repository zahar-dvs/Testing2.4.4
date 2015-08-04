package com.springapp.mvc.controller;

import com.springapp.mvc.domain.User;
import com.springapp.mvc.services.TestService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    TestService testService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String register(ModelMap model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addNewUser(ModelMap model, @Valid User user, final BindingResult result) {
        if (result.hasErrors()){
            return "error";
        }else if (checkUser(user) == null) {
                userService.saveUser(user);
                model.addAttribute("message", "Спасибо за регистрацию! Можете войти под своим логином.");
                return "newLogin";
            }else {
            model.addAttribute("message1", "Пользователь с таким именем уже существует! Пожалуйста, выберите другое имя.");
            return "newRegistration";
            }
        }

    private User checkUser(User user) {
        User checkUser = userService.checkUser(user);
        if (null == checkUser) {
            return checkUser;
        } else {
            return checkUser;
        }
    }

    @RequestMapping("login")
    public ModelAndView getLoginForm() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public String login(HttpServletRequest request, ModelMap model, Principal principal) {
        String userName = principal.getName();
        Integer userId = userService.getUserId(userName);
		String nickName = userService.getNickName(userName);
        model.addAttribute("nickName", nickName);
        model.addAttribute("username", userName);
        request.getSession().setAttribute("userID", userId);
        request.getSession().setAttribute("username", userName);
        return "success";
    }

}

