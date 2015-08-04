package com.springapp.mvc.services;

import com.springapp.mvc.dao.AnswersDao;
import com.springapp.mvc.domain.Answers;
import com.springapp.mvc.domain.CurrTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class AnswersService {

    @Autowired
    private AnswersDao answersDao;

    @Autowired
    private TestService testService;

    /**
     * @return the answersDao
     */
    public AnswersDao getAnswersDao() {
        return answersDao;
    }

    /**
     * @param answersDao the answersDao to set
     */
    public void setAnswersDao(AnswersDao answersDao) {
        this.answersDao = answersDao;
    }


    @Transactional
    public List<Answers> getAnswers(int question_id){
        List<Answers> list= answersDao.getAnswers(question_id);
        return list;
    }


    @Transactional
    public LinkedList<Object> getCurrAnswer(Integer userID, String sessionID, Integer testID){
        LinkedList<Object> currAnswers = new LinkedList<Object>();
        List<CurrTest> list = testService.getCurrTest(userID, sessionID, testID);
        for (CurrTest currTest : list) {
            if (currTest.getAnswerId() != 0) {
                currAnswers.add(answersDao.getCurrAnswer(currTest.getAnswerId()).getAnswer());
            } else {
                currAnswers.add("The answer is not selected");
            }
        }
        return currAnswers;
    }

    @Transactional
    public LinkedList<Object> getCorrectAnswer(Integer userID, String sessionID, Integer testID){
        LinkedList<Object> correctAnswers = new LinkedList<Object>();
        List<CurrTest> list = testService.getCurrTest(userID, sessionID, testID);
        for (CurrTest currTest : list) {
            correctAnswers.add(answersDao.getCorrectAnswer(currTest.getQuestionId()).getAnswer());
        }
        return correctAnswers;
    }


    @Transactional
    public Integer result(Integer userID, String sessionID, Integer testID){
        int i = 0;

        List<CurrTest> list = testService.getCurrTest(userID, sessionID, testID);
        for (CurrTest currTest : list) {
            if (currTest.getAnswerId() != 0){
                if(answersDao.getCurrAnswer(currTest.getAnswerId()).getCorrect() == 1){
                    i++;
                }
            }
        }

        return i;
    }

    @Transactional
    public void saveAnswers(Integer questionId, Integer testId, String answer1, String answer2, String answer3, String answer4){

        saveOneAnswer(questionId, testId, answer1);

        saveOneAnswer(questionId, testId, answer2);

        if (answer3 != "") {
            saveOneAnswer(questionId, testId, answer3);
        }

        if (answer4 != "") {
            saveOneAnswer(questionId, testId, answer4);
        }
    }

    public void saveOneAnswer(Integer questionId, Integer testId, String answer){
        Answers answers = new Answers();
        answers.setQuestionId(questionId);
        answers.setAnswer(answer);
        answers.setTestId(testId);
        answersDao.saveAnswer(answers);

    }

    @Transactional
    public void setCorrect(Integer correctId){
        answersDao.saveCorrect(correctId);
    }

}
