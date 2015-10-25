package com.saick.base.listener;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.saick.base.entity.User;

/**
 * 在ServletContext中添加和Session绑定的用户登录相关的信息
 * 
 * 添加用户登录信息
 * 
 * HttpSessionAttributeListener监听器
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
public class KickUserListener implements HttpSessionAttributeListener {
    
    @SuppressWarnings("unchecked")
    public void attributeAdded(HttpSessionBindingEvent bindingEvent) {
        String attributeName = bindingEvent.getName();
        if ("username".equals(attributeName)) {
            String username = (String) bindingEvent.getValue();
            HttpSession httpSession = bindingEvent.getSession();
            ServletContext servletContext = httpSession.getServletContext();
            //进行同步ServletContext是唯一的
            synchronized (servletContext) {
                Map<String, User> servletContextMap = (Map<String, User>) servletContext.getAttribute("servletContextMap");
                if (servletContextMap == null) {
                    servletContextMap = new LinkedHashMap<String, User>();
                }
                User user = new User(httpSession, username);
                servletContextMap.put(httpSession.getId(), user);
                servletContext.setAttribute("servletContextMap", servletContextMap);
            }
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {

    }

    public void attributeReplaced(HttpSessionBindingEvent se) {

    }
}
