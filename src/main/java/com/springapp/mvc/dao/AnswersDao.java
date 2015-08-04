package com.springapp.mvc.dao;

import com.springapp.mvc.domain.Answers;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnswersDao {

    @Autowired
    SessionFactory sessionFactory;


    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Answers> getAnswers(int question_id){
        Criteria criteria = openSession().createCriteria(Answers.class)
                .add(Restrictions.eq("questionId", question_id));
        return criteria.list();
    }

    public Answers getCurrAnswer(Integer answerID){
        Criteria criteria = openSession().createCriteria(Answers.class)
                .add(Restrictions.eq("id", answerID));
        return (Answers) criteria.uniqueResult();
    }

    public Answers getCorrectAnswer(Integer questionID){
        Criteria criteria = openSession().createCriteria(Answers.class)
                .add(Restrictions.eq("questionId", questionID))
                .add(Restrictions.eq("correct", 1));
        return (Answers) criteria.uniqueResult();
    }

    public void saveAnswer(Answers answers){
        openSession().save(answers);
    }

    public void saveCorrect(Integer correctId){
        String sss = "Update ANSWERS set ANSWERS.CORRECT = 1 where ANSWERS.ID = "+correctId.toString();
        String sql = sss;
        SQLQuery query = openSession().createSQLQuery(sql);
        query.executeUpdate();
    }

}
