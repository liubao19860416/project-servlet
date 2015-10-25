package com.saick.base.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动登录的servlet
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
@SuppressWarnings("serial")
public class AutoLoginCheckServlet extends HttpServlet {

    /**
     * get和post方式，都会自动调用POST方式
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    /**
     * POST方式
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username != null && password != null) {
            request.getSession().setAttribute("username", username);
            // 进行了URL编码
            username = URLEncoder.encode(username, "UTF-8");
            Cookie cookie = new Cookie("userCookie", username + "_" + password);
            cookie.setMaxAge(1 * 24 * 60 * 60);
            response.addCookie(cookie);
            // 欢迎页面
            response.sendRedirect(request.getContextPath() + "/autoWelcome.jsp");
        } else {
            // 登录页面
            response.sendRedirect(request.getContextPath() + "/autoLogin.jsp");
        }
    }

}
