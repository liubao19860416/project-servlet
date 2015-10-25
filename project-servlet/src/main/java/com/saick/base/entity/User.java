package com.saick.base.entity;

import java.io.Serializable;

import javax.servlet.http.HttpSession;

/**
 * 测试实体bean
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
public class User implements Serializable {

    private static final long serialVersionUID = 8367007554547014101L;

    private HttpSession httpSession;
    private String username;
    private String password;

    public User() {
    }
    
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }



    public User(HttpSession httpSession, String username) {
        this.httpSession = httpSession;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
