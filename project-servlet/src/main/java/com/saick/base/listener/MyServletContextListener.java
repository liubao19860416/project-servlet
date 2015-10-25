package com.saick.base.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/**
 * ServletContextListener和ServletContextAttributeListener监听器测试类
 * 
 * @author Liubao
 * @2014年12月17日
 *
 */
public class MyServletContextListener implements ServletContextListener,ServletContextAttributeListener {
    
    public MyServletContextListener() {
        System.out.println("new MyServletContextListener。。。");
    }

    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ServletContext contextInitialized");
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("ServletContext contextDestroyed");
    }

    public void attributeAdded(ServletContextAttributeEvent event) {
        System.out.println("ServletContext attributeAdded ");
        String key = event.getName();
        Object value = event.getValue();
        System.out.println(key + ":" + value);
    }

    public void attributeReplaced(ServletContextAttributeEvent event) {
        System.out.println("ServletContext attributeReplaced ");
        String key = event.getName();
        String value = (String) event.getValue();
        System.out.println(key + ":" + value);
    }

    public void attributeRemoved(ServletContextAttributeEvent event) {
        System.out.println("ServletContext attributeRemoved ");
        String key = event.getName();
        String value = (String) event.getValue();
        System.out.println(key + ":" + value);
    }
}
