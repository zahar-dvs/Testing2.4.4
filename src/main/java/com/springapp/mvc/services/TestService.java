package com.springapp.mvc.services;

import com.springapp.mvc.dao.TestDao;
import com.springapp.mvc.domain.CurrTest;
import com.springapp.mvc.domain.ResultTest;
import com.springapp.mvc.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class TestService  {

    @Autowired
    TestDao testDao;

    @Transactional
    public List<Test> getTests(){
        List<Test> list = testDao.getTests();
        return list;
    }

    @Transactional
    public void setCurrTest(Integer userID, Integer testID, Integer questionID, Integer answerID, String sessionID){
        CurrTest currTest = new CurrTest();
        currTest.setUserId(userID);
        currTest.setTestId(testID);
        currTest.setQuestionId(questionID);
        if (answerID!=0) {
            currTest.setAnswerId(answerID);
        }
        currTest.setSessionID(sessionID);
        testDao.setCurrentTest(currTest);
    }

    @Transactional
    public void setResultTest(Integer userID, Integer testID, Integer result){
        ResultTest resultTest = new ResultTest();
        resultTest.setUserId(userID);
        resultTest.setTestId(testID);
        resultTest.setResult(result);
        testDao.setResultTest(resultTest);
    }

    @Transactional
    public List<CurrTest> getCurrTest(Integer userID, String sessionID, Integer testID){
        List<CurrTest> list = testDao.getCurrTest(userID, sessionID, testID);
        return list;
    }

    @Transactional
    public LinkedList<Object> getTestId(Integer userID){
        LinkedList<Object> result = new LinkedList<Object>();
        List<ResultTest> list = testDao.getResultTest(userID);
        for (ResultTest resultTest : list) {
            result.add(resultTest.getTestId());
        }
        return result;
    }

    @Transactional
    public LinkedList<Object> getResultTest(Integer userID){
        LinkedList<Object> result = new LinkedList<Object>();
        List<ResultTest> list = testDao.getResultTest(userID);
        for (ResultTest resultTest : list) {
            result.add(resultTest.getResult());
        }
        return result;
    }

    @Transactional
    public LinkedList<Object> getTestTime(Integer userID){
        LinkedList<Object> result = new LinkedList<Object>();
        List<ResultTest> list = testDao.getResultTest(userID);
        for (ResultTest resultTest : list) {
            result.add(resultTest.getTime());
        }
        return result;
    }

    @Transactional
    public void clearCurrTest(Integer userID, String sessionID, Integer testID){
        testDao.clearCurrTest(userID, sessionID, testID);
    }

    @Transactional
    public void saveTest(String test){
        testDao.saveTest(test);
    }

    @Transactional
    public void deleteTest(Integer testId){
        testDao.deleteTest(testId);
    }

    @Transactional
    public void deleteQuestion(Integer questionId){
        testDao.deleteQuestion(questionId);
    }

}
