package com.springapp.mvc.controller;

import com.springapp.mvc.services.AnswersService;
import com.springapp.mvc.services.QuestionsService;
import com.springapp.mvc.services.TestService;
import com.springapp.mvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ResultController {


    @Autowired
    QuestionsService questionsService;

    @Autowired
    AnswersService answersService;

    @Autowired
    TestService testService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ModelAndView Result(HttpServletRequest request,
                               @RequestParam(required=true) Integer res) throws Exception{
        ModelAndView mav = new ModelAndView("currentResult");
        int userID =(Integer) request.getSession().getAttribute("userID");
        int testID = (Integer) request.getSession().getAttribute("testID");
        int questionID = (Integer) request.getSession().getAttribute("questionID");
        String sessionID = request.getSession().getId();
        testService.setCurrTest(userID, testID, questionID, res, sessionID);
        questionsService.clearMap(request.getSession().getId());
        mav.addObject("questions", questionsService.showQuestion(userID, sessionID, testID));
        mav.addObject("currAnswers", answersService.getCurrAnswer(userID, sessionID, testID));
        mav.addObject("correctAnswers", answersService.getCorrectAnswer(userID, sessionID, testID));
        Integer i = answersService.result(userID, sessionID, testID);
        mav.addObject("result",  i);
        testService.setResultTest(userID, testID, i);
        testService.clearCurrTest(userID, sessionID, testID);
        return mav;
    }

    @RequestMapping("/userResults")
    public ModelAndView UserResults(HttpServletRequest request) throws Exception{
        ModelAndView mav = new ModelAndView("allUserResults");
        int userID = (Integer) request.getSession().getAttribute("userID");
        mav.addObject("username", request.getSession().getAttribute("username"));
        mav.addObject("questions", testService.getTestId(userID));
        mav.addObject("currAnswers", testService.getResultTest(userID));
        mav.addObject("time", testService.getTestTime(userID));
        return mav;
    }

}
