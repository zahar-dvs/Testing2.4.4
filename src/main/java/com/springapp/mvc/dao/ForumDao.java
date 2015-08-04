package com.springapp.mvc.dao;

import com.springapp.mvc.domain.Forum;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ForumDao {

    @Autowired
    SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Forum> showPosts() {

        Criteria criteria = openSession().createCriteria(Forum.class);

        return criteria.list();
    }

    public void saveMessage(String message, String userName) {
        Forum forum = new Forum();
        forum.setPost(message);
        forum.setUserName(userName);
        openSession().save(forum);
    }
}
