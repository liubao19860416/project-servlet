package com.saick.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.saick.base.entity.User;

public class AdminServlet extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * hidden选项提交的表单参数？
         */
        String method = request.getParameter("method");
        if ("login".equals(method)) {
            this.login(request, response);
        } else if ("exit".equals(method)) {
            this.exit(request, response);
        }
    }

    /**
     * 退出方法
     */
    private void exit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("admin");
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    /**
     * 登录方法
     */
    @SuppressWarnings("unused")
    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String checkNumClient = request.getParameter("checkNum");
            String checkNumServer = (String) request.getSession().getAttribute(
                    "CHECKNUM");
            if (checkNumClient != null && checkNumServer != null) {
                if (checkNumClient.equals(checkNumServer)) {
                    User admin = new User(username, password);
                    User tempAdmin = null;/*iAdminService.login(admin)*/
                    if (tempAdmin != null) {
                        request.getSession().removeAttribute("CHECKNUM");
                        request.getSession().setAttribute("admin", tempAdmin);
                        response.sendRedirect(request.getContextPath()
                                + "/menu/main.jsp");
                    } else {
                        request.setAttribute("message", "信息1");
                        request.getRequestDispatcher("/error.jsp").forward(
                                request, response);
                    }
                } else {
                    request.setAttribute("message", "信息2");
                    request.getRequestDispatcher("/error.jsp").forward(request,
                            response);
                }
            } else {
                request.setAttribute("message", "信息3");
                request.getRequestDispatcher("/error.jsp").forward(request,
                        response);
            }
        } catch (Exception e) {
            request.setAttribute("message", "信息4");
            request.getRequestDispatcher("/error.jsp").forward(request,
                    response);
        }
    }
}
