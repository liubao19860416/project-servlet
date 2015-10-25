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
 * 需要缓存js等静态页面的过滤器
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */

public class NeedCacheFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) resp;
        } catch (Exception e) {
            throw new RuntimeException("IS Not A HTTP Request Or Response");
        }

        String URIName = request.getRequestURI();
        String URIType = URIName.substring(URIName.lastIndexOf('.') + 1);
        String timeValue = null;
        long timeSet = 0;
        timeValue = filterConfig.getInitParameter(URIType);
        if ("html".equalsIgnoreCase(URIType)) {
            timeSet = Long.parseLong(timeValue) * 60 * 60 * 1000;
        } else if ("css".equalsIgnoreCase(URIType)) {
            timeSet = Long.parseLong(timeValue) * 60 * 60 * 1000;
        } else if ("js".equalsIgnoreCase(URIType)) {
            timeSet = Long.parseLong(timeValue) * 60 * 60 * 1000;
        } else {
            timeSet = 0;
        }

        if (timeValue != null) {
            response.setDateHeader("Expires", System.currentTimeMillis()
                    + timeSet);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
