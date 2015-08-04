package com.springapp.mvc.domain;

import javax.persistence.*;

/**
 * Created by SashOk on 27.06.2015.
 */
@Entity
@Table(name="AUTHORIZATION")
public class Authorization {
    @Id
    @Column(name = "USERROLE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "USERID")
    private Integer userId;
    @Column(name = "USERROLE")
    private String userrole;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }
}
