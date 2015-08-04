package com.springapp.mvc.controller;

import com.springapp.mvc.services.AnswersService;
import com.springapp.mvc.services.QuestionsService;
import com.springapp.mvc.services.TestService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    TestService testService;

    @Autowired
    QuestionsService questionsService;

    @Autowired
    AnswersService answersService;

    @RequestMapping("/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView edit () throws Exception {
        ModelAndView mav = new ModelAndView("edit");
//        mav.addObject("users", userService.getUsers());
//        mav.addObject("user", new User());
        mav.addObject("tests", testService.getTests());
        return mav;
    }

    @RequestMapping(value = "/addQuestion/{testid}", method = RequestMethod.GET)
    public ModelAndView addQuestion (HttpServletRequest request,
                                     @PathVariable("testid") Integer testid) throws  Exception{
        ModelAndView mav = new ModelAndView("addquestion");
//        mav.addObject("testid", testid);
        request.getSession().setAttribute("quetestid", testid);
        return mav;
    }

    @RequestMapping("/saveQuestion")
    public ModelAndView saveQuestion (HttpServletRequest request,
                                      @RequestParam(required = true) String question,
                                      @RequestParam(required = true) String answer1,
                                      @RequestParam(required = true) String answer2,
                                      @RequestParam(required = true) String answer3,
                                      @RequestParam(required = true) String answer4) throws  Exception{
        ModelAndView mav = new ModelAndView("saveQuestion");
        Integer testId = (Integer) request.getSession().getAttribute("quetestid");
        questionsService.saveQuestion(question, testId);
        Integer questionId = questionsService.getQuestionId(question);
        request.getSession().setAttribute("questionId", questionId);
        answersService.saveAnswers(questionId, testId, answer1, answer2, answer3, answer4);
        mav.addObject("answers", answersService.getAnswers(questionId));
        return mav;
    }

    @RequestMapping(value = "/saveCorrect/{correct}", method = RequestMethod.GET)
    public ModelAndView saveCorrect (@PathVariable("correct") Integer correct) throws  Exception{
        ModelAndView mav = new ModelAndView("addquestion");
        answersService.setCorrect(correct);
        return mav;
    }

    @RequestMapping("/saveTest")
    public ModelAndView saveTest (HttpServletRequest request,
                                      @RequestParam(required = true) String test) throws  Exception{
        ModelAndView mav = new ModelAndView("edit");
        testService.saveTest(test);
        return mav;
    }

    @RequestMapping(value = "/deleteTest/{testid}", method = RequestMethod.GET)
         public ModelAndView deleteTest (HttpServletRequest request,
                                         @PathVariable("testid") Integer testId) throws  Exception{
        ModelAndView mav = new ModelAndView("edit");
        testService.deleteTest(testId);
        mav.addObject("tests", testService.getTests());
        return mav;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancelQuestion (HttpServletRequest request) throws  Exception{
        Integer questionId = (Integer) request.getSession().getAttribute("questionId");
        testService.deleteQuestion(questionId);
        return "/edit";
    }

    @RequestMapping(value = "/show")
    public String show(){
        return "show";
    }

}
