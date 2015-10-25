package com.saick.base.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

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
 * 字符编码过滤器2：读取类web.xml中配置的基本配置信息
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class MyCharacterEncodingFilter implements Filter {

    //可以读取web.xml中的相关配置参数信息
    private FilterConfig filterConfig;
    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        String encoding = filterConfig.getInitParameter("encoding");
        if (encoding != null) {
            this.encoding = encoding;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        /**
         * 读取基本的一些配置信息
         */
        Enumeration enumeration = filterConfig.getInitParameterNames();
        System.out.println(filterConfig.getFilterName());
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            System.out
                    .println(name + "=" + filterConfig.getInitParameter(name));
        }

        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) resp;
        } catch (Exception e) {
            throw new RuntimeException("Is Not A HTTP Request Or Response!");
        }
        
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            System.out.println(name);
        }

        // 设置request中的中文编码;
        request.setCharacterEncoding(encoding);
        // 设置response中的中文编码;
        response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset=" + encoding);
        // 设置get方式的中文编码方式;
        ServletRequest myRequest = new MyHttpServletRequest(request);
        
        chain.doFilter(myRequest, response);
    }

    @Override
    public void destroy() {

    }

}
/**
 * 编码过滤的包装类
 */
class MyHttpServletRequest extends HttpServletRequestWrapper {
    HttpServletRequest request;
    public MyHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.request = request;
    }

    //可以理解成需要覆写的方法,都是使用get方式去取数据的方法;
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        String method = request.getMethod();
        if (value != null && "get".equalsIgnoreCase(method)) {
            try {
                return new String(value.getBytes("iso8859-1"),request.getCharacterEncoding());
                // super.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("解码码表指定错误!");
            }
        }
        return null;
    }
}
