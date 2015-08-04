package com.springapp.mvc.controller;

import com.springapp.mvc.domain.Question;
import com.springapp.mvc.services.AnswersService;
import com.springapp.mvc.services.QuestionsService;
import com.springapp.mvc.services.TestService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {

    @Autowired
    QuestionsService questionsService;

    @Autowired
    AnswersService answersService;

    @Autowired
    TestService testService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/test/{testid}", method = RequestMethod.GET)
    public ModelAndView testController(HttpServletRequest request, @PathVariable("testid") Integer testid) throws Exception {
        ModelAndView mav = new ModelAndView("test");
        mav.addObject("testid", testid);
        request.getSession().setAttribute("testID", testid);
        Integer questionID = questionsService.getQuestionId(testid, request.getSession().getId());
        request.getSession().setAttribute("questionID", questionID);
        showQuestion(mav, questionID);
        return mav;
    }

    private void showQuestion(ModelAndView mav, Integer questionID) {
        Question question = questionsService.getQuestion(questionID);
        mav.addObject("question", question);
        mav.addObject("answ", answersService.getAnswers(questionID));
    }

    @RequestMapping(value = "/question", method = RequestMethod.POST)
    public ModelAndView question(HttpServletRequest request,
                                 @RequestParam(required = false) Integer res) throws Exception {
        ModelAndView mav = new ModelAndView("question");
        int userID = (Integer) request.getSession().getAttribute("userID");
        int testID = (Integer) request.getSession().getAttribute("testID");
        int questionID = (Integer) request.getSession().getAttribute("questionID");
        String sessionID = request.getSession().getId();
        testService.setCurrTest(userID, testID, questionID, res, sessionID);
        questionID = questionsService.getQuestionId(testID, sessionID);
        showQuestion(mav, questionID);
        request.getSession().setAttribute("questionID", questionID);
        return mav;
    }

    @RequestMapping(value = "/showtest", method = RequestMethod.GET)
    public String showTest(HttpServletRequest request, ModelMap model) {
//        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("tests", testService.getTests());
//        request.getSession().setAttribute("userID", user.getUserid());
        return "showTest";

    }
}

