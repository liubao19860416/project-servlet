package com.saick.base.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 文件下载的servlet
 * 
 * @author Liubao
 * @2014年12月18日
 *
 */
@SuppressWarnings("serial")
public class FileDownLoadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		fileName=new String(fileName.getBytes("iso-8859-1"),request.getCharacterEncoding());
		String downloadFileName=fileName.substring(fileName.indexOf("_")+1);
		String savePath = getServletContext().getRealPath("/WEB-INF/files");
		//创建子目录
		String childDir = makeNewDir(savePath,fileName);
		File file=new File(savePath+"\\"+childDir+"\\"+fileName);
		if (!file.exists()) {
			response.getWriter().write("文件新创建!");
			return;
		}
		//提示客户端下载文件
		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(downloadFileName,"UTF-8"));
		
		InputStream in=new FileInputStream(file);
		byte[] b=new byte[1024];
		int len=-1;
		ServletOutputStream out = response.getOutputStream();
		while((len=in.read(b))!=-1){
			out.write(b, 0, len);
		}
		out.close();
		in.close();
		response.getOutputStream().write(" ".getBytes());
	}


	private String makeNewDir(String savePath, String fileName) {
		//int hashCode = fileName.substring(0,fileName.indexOf("_")).hashCode();
	    int hashCode = fileName.hashCode();
		int dir1 = hashCode & 0xf;
		int dir2 = (hashCode >> 4) & 0xf;
		File file = new File(savePath + "\\" + dir1 + "/" + dir2);
		if (!file.exists()) {
			throw new RuntimeException(" !");
		}
		return "" + dir1 + "/" + dir2;
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
