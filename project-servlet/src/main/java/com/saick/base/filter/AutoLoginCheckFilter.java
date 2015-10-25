package com.saick.base.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自动登录过滤器，从本地Cookie中读取用户信息
 * 
 * 注意：
 * 1.必须存在空参数的构造函数，可以省略不写，否则会启动报错：类名不能找到，不能被实例化错误；
 * 2.尽量不要使用重定向，因为使用重定向的话，相当于又一个新的url访问，可能又会被当前的过滤器所映射到而再次执行！！！
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
public class AutoLoginCheckFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            Cookie userCookie = null;
            for (Cookie cookie : cookies) {
                if ("userCookie".equals(cookie.getName())) {
                    userCookie = cookie;
                    break;
                }
            }
            if (userCookie != null) {
                String usernameAndPassword = userCookie.getValue();
                String[] both = usernameAndPassword.split("_");
                String username = both[0];
                //进行解码
                username = URLDecoder.decode(username, "UTF-8");
                request.getSession().setAttribute("username", username);
                /**
                 * 这里对密码的朝珠哦，可以根据用户名称进行查询加密后的md5密码，进行比较
                 */
            }
        } 
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
