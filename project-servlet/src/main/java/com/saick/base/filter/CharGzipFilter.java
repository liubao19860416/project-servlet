package com.saick.base.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Gzip格式压缩过滤器
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
public class CharGzipFilter implements Filter {
    
    //private Logger logger =LoggerFactory.getLogger(this.getClass());
    
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        //包装类
        MyResponse myResponse = new MyResponse(response);
        chain.doFilter(request, myResponse);
        byte[] data = myResponse.getData();
        //logger.info("压缩前的字节数长度为："+ data.length);
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        /**
         * 进行压缩数据操作的位置，也是将数据压缩到了内存中
         */
        GZIPOutputStream gzip = new GZIPOutputStream(arrayOutputStream);
        //压缩散发已经被其封装好了，直接调用该方法就可以了；
        gzip.write(data);
        gzip.flush();
        gzip.close();
        byte[] newData = arrayOutputStream.toByteArray();
        //logger.info("压缩后的字节数长度为："+ newData.length);
        /**
         * 通知浏览器，这是经过gzip压缩的格式的数据，浏览器就会自动进行解压缩数据
         */
        response.setHeader("content-encoding", "gzip");
        response.getOutputStream().write(newData);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

}

/**
 * 包装类，将jsp中的数据进行压缩
 */
@SuppressWarnings("unused")
class MyResponse extends HttpServletResponseWrapper {
    private PrintWriter pw;
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private HttpServletResponse response;

    public MyResponse(HttpServletResponse response) {
        super(response);
        this.response = response;
    }

    public PrintWriter getWriter() throws IOException {
        pw = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        return pw;
    }

    public byte[] getData() {
        if (pw != null) {
            pw.flush();
        }
        return outputStream.toByteArray();
    }
}
