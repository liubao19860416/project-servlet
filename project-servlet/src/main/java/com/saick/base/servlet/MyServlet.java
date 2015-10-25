package com.saick.base.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.saick.base.entity.User;

/**
 * 基础的servlet测试类
 * 
 * @author Liubao
 * @2014年12月17日
 * 
 */
@SuppressWarnings("serial")
public class MyServlet extends HttpServlet {

    @SuppressWarnings({ "unused", "unchecked" })
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 属性拷贝方法2种方式
         */
        User user=new User();
        User dest=new User();
        User orig=new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 读取web.xml中配置的属性信息
         */
        ServletConfig servletConfig = getServletConfig();
        //读取该servlet的name名称
        String servletName = servletConfig.getServletName();
        String encoding=servletConfig.getInitParameter("encoding");
        String username=servletConfig.getInitParameter("username");
        
        /**
         * 第一部分：读取文件信息，并响应给客户下载
         */
        // 读取文件路径信息
        ServletContext servletContext = getServletContext();
        // D:\apache-tomcat-7.0.53\webapps\MyEclipse_work_day08_Servlet\WEB-INF\1.jpg
        String realPath = servletContext.getRealPath("/WEB-INF/1.jpg");
        // D:\apache-tomcat-7.0.53\webapps\MyEclipse_work_day08_Servlet\2.jpg
        String realPath1 = servletContext.getRealPath("/2.jpg");
        InputStream in = new FileInputStream(realPath);
        
        //对文件名进行URLEncoder编码
        String fileName="1.jpg";
        response.setHeader("Content-Disposition", "attachment;filename="+
                URLEncoder.encode(fileName,"UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        
        int len = -1;
        byte[] buf = new byte[1024];
        try {
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

        /**
         * 第二部分：设置response相关参数
         */
        String str = "响应结果";
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragam", "no-cache");

        // 方式1
        // response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");

        // 方式2
        // response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'>");
        writer.write(str);
        
        /**
         * 第三部分：读取request相关参数
         */
        Enumeration<?> hearderNames = request.getHeaderNames();
        while(hearderNames.hasMoreElements()){
            String hearderName =(String)hearderNames.nextElement();
            String heardervalue=request.getHeader(hearderName);
            System.out.println(hearderName+":"+heardervalue);
        }
        String header = request.getHeader("Accept-Encoding");
        System.out.println(header);
        
        String[] parameterValues = request.getParameterValues("password");
        for (int i = 0; i < parameterValues.length; i++) {
            System.out.println("parameterValue="+parameterValues[i]+"\n");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
