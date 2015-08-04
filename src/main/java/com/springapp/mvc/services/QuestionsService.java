package com.springapp.mvc.services;

import com.springapp.mvc.dao.QuestionsDao;
import com.springapp.mvc.domain.CurrTest;
import com.springapp.mvc.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionsService {

    @Autowired
    private QuestionsDao questionsDao;

    @Autowired
    private TestService testService;

    /**
     * @return the questionsDao
     */
    public QuestionsDao getQuestionsDao() {
        return questionsDao;
    }

    /**
     * @param questionsDao the questionsDao to set
     */
    public void setQuestionsDao(QuestionsDao questionsDao) {
        this.questionsDao = questionsDao;
    }

    @Transactional
    public Question getQuestion(Integer id) {
        return questionsDao.getQuestion(id);
    }

    private HashMap<String, ArrayList<Integer>> hashMap = new HashMap<String, ArrayList<Integer>>();

    @Transactional
    public Integer getQuestionId(Integer testID, String sessionID) throws Exception{
        ArrayList<Object> list = questionsDao.genQuesId(testID);
        int i = list.size();
        Random generator = new Random();
        int id;
        id = generator.nextInt(i-1);
        ArrayList<Integer> arrlist = new ArrayList<Integer>();
        if (hashMap.containsKey(sessionID)){
            arrlist.addAll(hashMap.get(sessionID));
        }
        boolean b;
        if (arrlist.size() == 0) {
            b = true;
            arrlist.add(id);
            hashMap.put(sessionID, arrlist);
        } else {
            b = false;
        }

        while (!(b)) {
            for (int j1 = 0; j1 < arrlist.size(); j1++) {
                for (int j2 = 0; j2 < arrlist.size(); j2++) {
                    if (id != arrlist.get(j2)) {
                        b = true;
                    } else {
                        b = false;
                        break;
                    }
                }
                if (!(b)) {
                    break;
                }
            }
            if (!(b)) {
                id = generator.nextInt(10);
            } else {
                arrlist.add(id);
                hashMap.put(sessionID, arrlist);
            }
        }
        return (Integer) list.get(id);
    }

    public void clearMap(String sessionID){
        hashMap.remove(sessionID);
    }

    @Transactional
    public LinkedList<Object> showQuestion(Integer userID, String sessionID, Integer testID){
        LinkedList<Object> questions = new LinkedList<Object>();
        List<CurrTest> list =  testService.getCurrTest(userID, sessionID, testID);
        for (CurrTest currTest : list) {
            questions.add(getQuestion(currTest.getQuestionId()).getQuestion());
        }
        return questions;
    }

    @Transactional
    public void saveQuestion(String que, Integer testId){
        Question question = new Question();
        question.setTestId(testId);
        question.setQuestion(que);
        questionsDao.saveQuestion(question);
    }

    @Transactional
    public Integer getQuestionId(String question){
        return questionsDao.getQuestionId(question);
    }

}
