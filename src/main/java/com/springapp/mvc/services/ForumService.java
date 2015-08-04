package com.springapp.mvc.services;

import com.springapp.mvc.dao.ForumDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    ForumDao forumDao;

    @Transactional
    public List showPosts() {
        List list = forumDao.showPosts();
        return list;
    }

    @Transactional
    public void saveMessage(String message, String userName) {
        forumDao.saveMessage(message, userName);
    }
}
