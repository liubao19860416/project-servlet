package com.saick.base.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * 基本的文件上传servlet
 * @author Liubao
 * @2014年12月18日
 *
 */
@SuppressWarnings("serial")
public class FileUploadServlet01 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            throw new RuntimeException("multipart/form-data");
        }
        String savePath = getServletContext().getRealPath("/WEB-INF/upload");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax((int) (1 * 1024 * 1024));// 100KB
        sfu.setSizeMax(2 * 1024 * 1024);// 2m
        
        try {
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    System.out.println(fieldName + "=" + fieldValue);
                    if (fieldName == null || "".equals(fieldName)) {
                        continue;
                    }
                } else {
                    InputStream in = item.getInputStream();
                    String filename = item.getName();
                    if (filename == null || "".equals(filename)) {
                        continue;
                    }
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    String contentType = request.getContentType();
                    if (!contentType.startsWith("image/")) {
                        response.getWriter().write("<br/>");
                        break;
                    }

                    filename = UUID.randomUUID() + "_" + filename;
                    // String childDir = makeChildDirs2(storeDirecotry);
                    String childDir = makeChildDirs1(savePath, filename);
                    OutputStream out = new FileOutputStream(savePath
                            + "\\" + childDir + "\\" + filename);

                    //删除临时文件
                    item.delete();
                    
                    int len = -1;
                    byte b[] = new byte[1024];
                    while ((len = in.read(b)) != -1) {
                        out.write(b, 0, len);
                    }
                    out.close();
                    in.close();
                }
            }
            response.getWriter().write("!");
            response.setHeader("Refresh", "3;url=" + request.getContextPath() + "/upload.jsp");
            // response.sendRedirect(request.getContextPath()+"/upload.jsp");
        } catch (FileUploadBase.FileSizeLimitExceededException e) {
            throw new RuntimeException(" ");
        } catch (FileUploadBase.SizeLimitExceededException e) {
            throw new RuntimeException(" ");
        } catch (FileUploadException e) {
            throw new RuntimeException(" ");
        }
    }

    private String makeChildDirs1(String storeDirecotry, String filename) {
        int hashCode = filename.hashCode();
        int dir1 = hashCode & 0xf;
        int dir2 = (hashCode >> 4) & 0xf;
        // int dir2=(hashCode&0xf)>>4;
        File file = new File(storeDirecotry + "\\" + dir1 + "/" + dir2);
        if (!file.exists()) {
            file.mkdirs();
        }
        return "" + dir1 + "/" + dir2;
    }

    private String makeChildDirs2(String storeDirecotry) {
        String childDir = null;
        Date now = new Date();
        childDir = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File file = new File(storeDirecotry + "\\" + childDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return childDir;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
