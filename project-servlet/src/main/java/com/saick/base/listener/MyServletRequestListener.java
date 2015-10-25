package com.saick.base.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ServletRequestListener和ServletRequestAttributeListener监听器测试类
 * 
 * @author Liubao
 * @2014年12月17日
 */
public class MyServletRequestListener implements ServletRequestListener,ServletRequestAttributeListener {
  
    @Override
    public void requestInitialized(ServletRequestEvent event) {
        ServletRequest servletRequest = event.getServletRequest();
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession httpSession = httpServletRequest.getSession();
        String clientIP = (String) httpSession.getAttribute("clientIP");
        if (clientIP == null) {
            clientIP = httpServletRequest.getRemoteAddr();
            httpSession.setAttribute("clientIP", clientIP);
        }
        System.out.println("MyServletRequestHander requestInitialized IP："+clientIP);
    }

    public void requestDestroyed(ServletRequestEvent event) {
        System.out.println("MyServletRequestHander requestDestroyed");
    }

    public void attributeAdded(ServletRequestAttributeEvent event) {
        System.out.println("MyServletRequestHander attributeAdded");
        System.out.println(event.getName() + " : " + event.getValue());
    }

    public void attributeReplaced(ServletRequestAttributeEvent event) {
        System.out.println("MyServletRequestHander attributeReplaced");
        System.out.println(event.getName() + " : " + event.getValue());
    }

    public void attributeRemoved(ServletRequestAttributeEvent event) {
        System.out.println("MyServletRequestHander attributeRemoved");
        System.out.println(event.getName() + " : " + event.getValue());
    }
}
