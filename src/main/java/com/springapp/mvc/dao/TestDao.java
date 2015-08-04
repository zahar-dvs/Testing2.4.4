package com.springapp.mvc.dao;

import com.springapp.mvc.domain.*;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TestDao {

    @Autowired
    SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }


    public List<Test> getTests() {
        Criteria criteria = openSession().createCriteria(Test.class);
        return criteria.list();
    }

    public void setCurrentTest(CurrTest currTest){
        sessionFactory.getCurrentSession().save(currTest);
    }

    public void setResultTest(ResultTest resultTest){
        sessionFactory.getCurrentSession().save(resultTest);
    }

    public List<CurrTest> getCurrTest(Integer userID, String sessionID, Integer testID){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(CurrTest.class)
                .add(Restrictions.eq("userId", userID))
                .add(Restrictions.eq("testId", testID))
                .add(Restrictions.eq("sessionID", sessionID));
        return criteria.list();
    }

    public List<ResultTest> getResultTest(Integer userID){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(ResultTest.class)
                .add(Restrictions.eq("userId", userID));

        return criteria.list();
    }

    public void clearCurrTest(Integer userID, String sessionID, Integer testID){
        String sql = "Delete from CURRTEST " +
                "where user_id = :userID " +
                "and test_id = :testID " +
                "and sessionID = :sessionID";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setParameter("testID", testID)
                .setParameter("userID", userID)
                .setParameter("sessionID", sessionID);
        query.executeUpdate();
    }

    public void saveTest(String test){
        Test newTest = new Test();
        newTest.setName(test);
        openSession().save(newTest);
    }

    public void deleteTest(Integer testId){
        Criteria criteria = openSession().createCriteria(Test.class).add(Restrictions.eq("id", testId));
        Test test = (Test) criteria.uniqueResult();
        openSession().delete(test);

        Criteria criteria2 = openSession().createCriteria(Question.class).add(Restrictions.eq("testId", testId));
        List<Question> question = criteria2.list();
        for (Question que : question){
            openSession().delete(que);
        }

        Criteria criteria3 = openSession().createCriteria(Answers.class).add(Restrictions.eq("testId", testId));
        List<Answers>  answers = criteria3.list();
        for (Answers ans : answers){
            openSession().delete(ans);
        }
    }

    public void deleteQuestion(Integer questionId){
        Criteria criteria = openSession().createCriteria(Question.class).add(Restrictions.eq("id", questionId));
        Question question = (Question) criteria.uniqueResult();
        openSession().delete(question);

        Criteria criteria3 = openSession().createCriteria(Answers.class).add(Restrictions.eq("questionId", questionId));
        List<Answers>  answers = criteria3.list();
        for (Answers ans : answers){
            openSession().delete(ans);
        }

    }

}