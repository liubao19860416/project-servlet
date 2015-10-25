package com.saick.base.uploadanddownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Administrator
 * 文件下载
 */
public class DownloadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		//获取uuidRealFileName
		String uuidRealFileName = request.getParameter("uuidRealFileName");
		//还原
		byte[] buf = uuidRealFileName.getBytes("ISO8859-1");
		//手工解码
		uuidRealFileName = new String(buf,"UTF-8");
		//获取realFileName
		int index = uuidRealFileName.indexOf("_");
		String realFileName = uuidRealFileName.substring(index+1);
		//通知浏览器以下载方式打开文件
		//response.setHeader("content-type","application/x-msdownload");
		response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode(realFileName,"UTF-8"));
		//以IO流方式将服务端的文件复制给客户端
		String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
		String subUploadPath = makeSubUpload(uploadPath,uuidRealFileName);
		InputStream is = new FileInputStream(subUploadPath + "/" + uuidRealFileName);
		OutputStream os = response.getOutputStream();
		buf = new byte[2048];
		int len = 0;
		while((len=is.read(buf))>0){
			os.write(buf,0,len);
		}
		os.close();
		is.close();
	}
	//通过这个算法，得到下载文件的位置
	private String makeSubUpload(String uploadPath,String uuidRealFileName){
		//获取hashCode整型值
		int code = uuidRealFileName.hashCode();
		//第一级子目录
		int dir1 = code & 0xF;//12
		//第二级子目录
		int dir2 = ( code >> 1 ) & 0xF;//6 
		//创建这些子目录
		File file = new File(uploadPath+"/"+dir1+"/"+dir2);
		//如果不存在该子目录
		if(!file.exists()){
			//连续创建2个子目录
			file.mkdirs();
		}
		//将创建后的子目录返回
		return file.getPath();
	} 
}
