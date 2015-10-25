package com.saick.base.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * get和post方式的编码过滤器
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
public class GetAndPostCharEncodingFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        //包装request类
        MyRequest myRequest = new MyRequest(request);
        response.setContentType("text/html;charset=UTF-8");
        //将包装类进行转发到过滤链中
        chain.doFilter(myRequest, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {
    }
}

/**
 * 包装类
 */
class MyRequest extends HttpServletRequestWrapper {
    private HttpServletRequest request;

    public MyRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    /**
     * 覆写的方法
     */
    @Override
    public String getParameter(String name) {
        String value = null;
        String requestType = request.getMethod();
        if ("GET".equalsIgnoreCase(requestType)) {
            String temp = request.getParameter(name);
            if (temp != null && temp.trim().length() > 0) {
                try {
                    byte[] bytes = temp.getBytes("ISO8859-1");
                    value = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        } else if ("POST".equalsIgnoreCase(requestType)) {
            try {
                request.setCharacterEncoding("UTF-8");
                value = request.getParameter(name);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return value;
    }

}
