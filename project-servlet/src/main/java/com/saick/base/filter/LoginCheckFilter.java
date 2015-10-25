package com.saick.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动登录校验用户身份的过滤器
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
public class LoginCheckFilter implements Filter {
   
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        /**
         * 这里的密码信息和用户名系你想可以通过访问dao获得
         */
        if (username != null && password != null) {
            if ("liubao".equals(username) && "123456".equals(password)) {
                chain.doFilter(request, response);
                // request.getRequestDispatcher("/admin/admin.jsp").forward(request,response);
                // response.sendRedirect(request.getContextPath()+"/admin/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath()+ "/adminLogin.jsp");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/adminLogin.jsp");
        }
    }

    public void destroy() {
    }

    
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
