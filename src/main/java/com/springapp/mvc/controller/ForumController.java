package com.springapp.mvc.controller;

import com.springapp.mvc.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class ForumController {

    @Autowired
    ForumService forumService;

    @RequestMapping(value = "/forum")
    @PreAuthorize("isAuthenticated()")
    public String openForum(ModelMap model){
        model.addAttribute("posts", forumService.showPosts());
        return "forum";
    }

    @RequestMapping("/addMessage")
    public String addMessage(HttpServletRequest request, @RequestParam (required = true) String message) throws  Exception{
        String userName = (String)request.getSession().getAttribute("username");
        forumService.saveMessage(message, userName);
        return "forum";
    }
}
