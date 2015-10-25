package com.saick.base.uploadanddownload;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 递归查询上传总目录下的上传文件
 */
public class ListServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		//定位总上传目录
		String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
		//准备Map集合装上传文件的信息，其中key为uuidRealFileName，value为realFileName
		Map<String,String> map = new LinkedHashMap<String,String>();
		//获取上传文件信息
		getFiles(uploadPath,map);
		//将Map集合绑定到request域对象中
		request.setAttribute("map",map);
		//转发到list.jsp页面
		request.getRequestDispatcher("/WEB-INF/list.jsp").forward(request,response);
	}
	//写一个递归算法，获取上传文件信息
	private void getFiles(String uploadPath,Map<String,String> map){
		//封装成File对象
		File file = new File(uploadPath);
		//如果是文件的话，递归算法一定要有出口
		if(file.isFile()){
			//获取uuidRealFileName
			String uuidRealFileName = file.getName();
			//获取realFileName
			int index = uuidRealFileName.indexOf("_");
			String realFileName = uuidRealFileName.substring(index+1);
			//装入Map集合
			map.put(uuidRealFileName,realFileName);
		}else if(file.isDirectory()){
			//获取该目录下的所有文件
			File[] files = file.listFiles();
			//迭代
			for(File f : files){
				//递归调用
				getFiles(f.getPath(),map);
			}
		}
	}
}














