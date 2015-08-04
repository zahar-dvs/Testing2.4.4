package com.springapp.mvc.dao;

import com.springapp.mvc.domain.Question;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class QuestionsDao{

    @Autowired
    SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    public ArrayList<Object> genQuesId(int testID){
        String sql = "Select QUESTIONS.ID from QUESTIONS where QUESTIONS.TEST_ID = :testid";
        SQLQuery query = openSession().createSQLQuery(sql);
        query.setParameter("testid", testID);
        return (ArrayList<Object>) query.list();
    }

    public Question getQuestion(Integer id) {
        return (Question) openSession().get(Question.class, id);
    }

    public void saveQuestion(Question question){
        openSession().save(question);
    }

    public Integer getQuestionId(String question){

        String sql = "Select QUESTIONS.ID from QUESTIONS where QUESTIONS.QUESTION = :questionid";
        SQLQuery query = openSession().createSQLQuery(sql);
        query.setParameter("questionid", question);
        return (Integer) query.list().get(0);
    }

}