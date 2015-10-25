package com.saick.base.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 在线人数监听器
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
public class UserOnlineListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession httpSession = sessionEvent.getSession();
        ServletContext servletContext = httpSession.getServletContext();
        Integer online = null;
        //同步操作
        synchronized (servletContext) {
            online = (Integer) servletContext.getAttribute("online");
            if (online == null) {
                online = 1;
            } else {
                online++;
            }
            servletContext.setAttribute("online", online);
        }
    }

    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
