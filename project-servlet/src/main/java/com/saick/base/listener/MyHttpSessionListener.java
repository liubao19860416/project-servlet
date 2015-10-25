package com.saick.base.listener;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * HttpSessionListener和HttpSessionAttributeListener监听器测试类
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
@SuppressWarnings("deprecation")
public class MyHttpSessionListener implements HttpSessionListener,HttpSessionAttributeListener {
   
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        System.out.println("HttpSession " + httpSession.getId() + " : "+ new Date().toLocaleString());
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession httpSession = se.getSession();
        System.out.println("HttpSession " + httpSession.getId() + " : " + new Date().toLocaleString());
    }

    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println(" " + se.getName() + " : " + se.getValue());
    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
        System.out.println(" " + se.getName() + " : " + se.getValue());
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
        System.out.println(" " + se.getName() + " : " + se.getValue());
    }
}
