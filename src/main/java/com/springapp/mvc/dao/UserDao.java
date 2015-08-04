package com.springapp.mvc.dao;


import com.springapp.mvc.domain.Authorization;
import com.springapp.mvc.domain.Joke;
import com.springapp.mvc.domain.User;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    public void saveUser(User user, String userRole, Integer enable){
        openSession().save(user);
        user.setEnabled(enable);
        saveAuthorization(userRole, user.getUserid());
    }

    public void saveAuthorization(String userRole, Integer userId){
        Authorization authorization = new Authorization();
        authorization.setUserId(userId);
        authorization.setUserrole(userRole);
        openSession().save(authorization);
    }

    public User checkUser(User user){
        Criteria criteria = openSession().createCriteria(User.class)
                .add(Restrictions.eq("username", user.getUserName()));
        return (User) criteria.uniqueResult();
    }


    public User getUser(String username, String password) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("password", password));
        return (User) criteria.uniqueResult();
    }

    public List<User> getUsers() {
        Criteria criteria = openSession().createCriteria(User.class);
        return criteria.list();
    }

    public User isUserConsist(String username) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

    public int getUserId (String userName){
        Criteria criteria = openSession().createCriteria(User.class)
                .add(Restrictions.eq("username", userName));
        User user = (User) criteria.uniqueResult();
        return user.getUserid();
    }

    public String getNickName (String userName){
        Criteria criteria = openSession().createCriteria(User.class)
                .add(Restrictions.eq("username", userName));
        User user = (User) criteria.uniqueResult();
        return user.getNickName();
    }

    public User getUserById (Integer userId){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class)
                .add(Restrictions.eq("id", userId));
        User user = (User) criteria.uniqueResult();
        return user;
    }

    public String getUserRole(Integer userId){
        Criteria criteria = openSession().createCriteria(Authorization.class)
                .add(Restrictions.eq("userId", userId));
        Authorization authorization = (Authorization) criteria.uniqueResult();
        return authorization.getUserrole();
    }

    public String takeJoke(int randomJoke) {
        Criteria criteria = openSession().createCriteria(Joke.class)
                .add(Restrictions.eq("id", randomJoke));
        Joke joke = (Joke) criteria.uniqueResult();
        return joke.getName();
    }

    public Integer quantityJoke() {
        return sessionFactory.getCurrentSession().createCriteria(Joke.class).list().size();
    }
}
