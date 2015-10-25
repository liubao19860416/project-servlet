package com.saick.base.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saick.base.entity.User;

/**
 * 在ServletContext中添加和Session绑定的用户登录相关的信息
 * 
 * 在线踢人的servlet
 * 
 * @author Liubao
 * @2014年12月16日
 * 
 */
@SuppressWarnings("serial")
public class KickUserServlet extends HttpServlet {

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
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sessionId = request.getParameter("sessionId");
        ServletContext servletContext = this.getServletContext();
        Map<String, User> map = (Map<String, User>) servletContext.getAttribute("servletContextMap");
        if (map != null && map.size() > 0) {
            if (map.containsKey(sessionId)) {
                User user = map.remove(sessionId);
                HttpSession httpSession = user.getHttpSession();
                httpSession.invalidate();
            }
        }
        response.sendRedirect(request.getContextPath() + "/online.jsp");
    }
}
