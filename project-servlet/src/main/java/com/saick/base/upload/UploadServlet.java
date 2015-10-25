package com.saick.base.upload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 带进度条显示的文件上传
 * 
 * @author Liubao
 * @2014年12月29日
 * 
 */
@SuppressWarnings("serial")
public class UploadServlet extends HttpServlet {

    /**
     * GET请求
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String status = (String) session.getAttribute("progress");
        out.println(status);
        session.removeAttribute("progress");
    }

    /**
     * POST请求
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        servletFileUpload.setProgressListener(new MyProgressListener(request));
        try {
            List<FileItem> items = servletFileUpload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String fileName = getFileName(item);
                    String storePath = getServletContext().getRealPath("/files");
                    System.out.println("文件存储路径为："+storePath);
                    item.write(new File(storePath, fileName));
                }
            }
            out.println("<script type='text/javascript'>alert('上传成功！');history.back();</script>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  获取上传文件的文件名
     */
    public String getFileName(FileItem item) {
        String fileName = item.getName();
        System.out.println("上传的文件名是：" + fileName);
        int lastIndex = fileName.lastIndexOf("\\");
        fileName = fileName.substring(lastIndex + 1);
        System.out.println("截取返回的文件名是：" + fileName);
        return fileName;
    }
}
