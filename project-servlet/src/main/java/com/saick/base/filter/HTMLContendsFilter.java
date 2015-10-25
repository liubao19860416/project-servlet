package com.saick.base.filter;

import java.io.IOException;

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
 * Html文件进行转义的过滤器
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class HTMLContendsFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) resp;
        } catch (Exception e) {
            throw new RuntimeException("Is Not A HTTP Request Or Response!");
        }
        ServletRequest myResquest = new MyHttpServletRequest3(request);
        chain.doFilter(myResquest, response);
    }
    public void destroy() {
    }

}

/**
 * 自定义包装类
 */
@SuppressWarnings("unused")
class MyHttpServletRequest3 extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    public MyHttpServletRequest3(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value == null) {
            return null;
        }
        value = replaceHtml(value);
        return value;
    }

    private String replaceHtml(String value) {
        char message[] = new char[value.length()];
        value.getChars(0, value.length(), message, 0);
        StringBuilder sb = new StringBuilder(value.length() + 50);
        for (int i = 0; i < value.length(); i++) {
            switch (message[i]) {
            case '<':
                sb.append("&lt;");
                break;
            case '>':
                sb.append("&gt;");
                break;
            case '"':
                sb.append("&amp;");
                break;
            case '&':
                sb.append("&quot;");
                break;
            default: {
                sb.append(message[i]);
            }
            }
        }
        return sb.toString();
    }
}
