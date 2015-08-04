package com.springapp.mvc.services;

import com.springapp.mvc.dao.UserDao;
import com.springapp.mvc.domain.Authorization;
import com.springapp.mvc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    private final String ROLE_DEFAULT = "ROLE_USER";
    private final Integer ENABLE = 1;

    @Transactional
    public void saveUser(User user) {
        userDao.saveUser(user, ROLE_DEFAULT, ENABLE);
    }

    @Transactional
    public void updateUser(User user, Integer userId) {
        User oldUser = getUserById(userId);
//        if (oldUser.getUserName() != user.getUserName()){
//            oldUser.setUserName(user.getUserName());
//        }
        if (oldUser.getPassword() != user.getPassword()){
            oldUser.setPassword(user.getPassword());
        }
        if (oldUser.getNickName() != user.getNickName()){
            oldUser.setNickName(user.getNickName());
        }
        if (oldUser.geteMail() != user.geteMail()){
            oldUser.seteMail(user.geteMail());
        }
    }

    @Transactional
    public User checkUser(User user){
        User checkUser = userDao.checkUser(user);
        if (null != checkUser){
            return checkUser;
        }else return checkUser;
    }

//    @Transactional
//    public Integer getUserId(String username, String password){
//        User user = userDao.getUser(username, password);
//        return (Integer) user.getUserid();
//    }

    @Transactional
    public Integer getUserId(String username){
        return userDao.getUserId(username);
    }

    @Transactional
    public String getNickName(String username){
        return userDao.getNickName(username);
    }

    @Transactional
    public String getUserRole(Integer userId){
        if (userDao.getUserRole(userId).equals("ROLE_USER")){
            return "Пользователь";
        }
        else if (userDao.getUserRole(userId).equals("ROLE_ADMIN")){
            return "Администратор";
        }else return "Ошибка";
    }

    @Transactional
    public List<User> getUsers(){
        List<User> list = userDao.getUsers();
        return list;
    }

    @Transactional
    public boolean isUserConsist(String username){
        if (null == userDao.isUserConsist(username)){
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public User getUserById(Integer userId){
        return userDao.getUserById(userId);
    }

    public String takeJoke() {
        Random random = new Random();
        int quantityJoke = userDao.quantityJoke();
        int randomJoke = random.nextInt(quantityJoke)+1;
        return userDao.takeJoke(randomJoke);
    }
}
