package com.saick.base.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.saick.base.entity.User;
/**
 * 在ServletContext中添加和Session绑定的用户登录相关的信息2
 * servletContextMap需要进行同步操作！！！
 * HttpSessionAttributeListener监听器
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
public class KickUserListener2 implements HttpSessionAttributeListener {

    @SuppressWarnings("unchecked")
    public void attributeAdded(HttpSessionBindingEvent se) {
        HttpSession session = se.getSession();
        ServletContext context = session.getServletContext();
        Map<String, HttpSession> servletContextMap = (Map<String, HttpSession>) context.getAttribute("servletContextMap");
        if (servletContextMap == null) {
            //servletContextMap需要进行同步操作！！！
            servletContextMap = Collections.synchronizedMap(new HashMap<String, HttpSession>());
            context.setAttribute("servletContextMap", servletContextMap);
        }
        Object obj = session.getAttribute("user");
        if (obj == null || !(obj instanceof User)) {
            return;
        }
        servletContextMap.put(((User) obj).getUsername(), session);
        context.setAttribute("servletContextMap", servletContextMap);
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {

    }

    public void attributeReplaced(HttpSessionBindingEvent se) {

    }

}
