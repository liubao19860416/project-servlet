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
 * 脏话过滤器：替换为指定的字符*
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class DirtyWordsFilter implements Filter {

    private static final String DIRTYWORDSREPLACER = "*";
    private static final String dirtyWords[] = new String[] { "垃圾", "傻逼", "骗子",
            "枪支", "政府", "歧视", "shit", "蠢", "懒" };

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest) req;
            response = (HttpServletResponse) resp;
        } catch (Exception e) {
            throw new RuntimeException("Is Not A HTTP Request Or Response!");
        }
        MyHttpServletRequest2 myResquest = new MyHttpServletRequest2(request);
        chain.doFilter(myResquest, response);
    }

    public void destroy() {
    }

    /**
     * 自定义包装类:这里定义成了内部类，也可以为外部类的
     */
    @SuppressWarnings("unused")
    class MyHttpServletRequest2 extends HttpServletRequestWrapper {
        private HttpServletRequest request;

        public MyHttpServletRequest2(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            // String value = request.getParameter(name);
            if (value == null) {
                return null;
            }
            for (String s : dirtyWords) {
                if (value.contains(s)) {
                    value = value.replace(s, getNewChar(s));
                }
            }
            return value;
        }

        public String getNewChar(String s) {
            // StringBuffer sb=new StringBuffer();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                sb.append(DIRTYWORDSREPLACER);
            }
            return sb.toString();
        }
    }
}
