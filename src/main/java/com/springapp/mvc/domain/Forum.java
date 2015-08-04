package com.springapp.mvc.domain;

import javax.persistence.*;

@Entity
@Table(name = "FORUM")
public class Forum {

    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "POST")
    private String post;

    @Column(name = "USERNAME")
    private String userName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
