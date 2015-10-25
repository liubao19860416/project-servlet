package com.saick.base.uploadanddownload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author Administrator
 * 读取浏览器上传的内容（普通内容和上传内容）
 * 并写入服务器指定的目录下存储
 */
public class UploadServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
	    //判断是否是属性文件上传的类型请求request
	    if (!ServletFileUpload.isMultipartContent(request)) {
	        return;
	    }
		try {
			//创建DiskFileItemFactory类
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			
			//默认这个FileUpload上传组件，给上传文件开劈10K的缓存空间，设置为100K
			diskFileItemFactory.setSizeThreshold(100*1024);
			
			//设置这个FileUpload上传组件，上传文件时使用的临时目录，默认是java.io.tmpdir目录
			String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
			diskFileItemFactory.setRepository(new File(tempPath));
			
			//创建ServletFileUpload类
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			
			//设置上传文件的编码方式
			servletFileUpload.setHeaderEncoding("UTF-8");
			
			/*
			 * 解析request中的上传内容，包含普通字段(上传人)和上传文件字段(选择文件)
			 * 返回的结果是浏览器上传的内容，每项内容以FileItem对象来表示，
			 * 其中上传人位于List[0]
			 * 其中上第一个上传文件位于List[1]
			 * 依次类推
			 */
			List<FileItem> fileItemList = servletFileUpload.parseRequest(request);
			
			//迭代
			for(FileItem fileItem : fileItemList){
				
				//如果是普通字段的话，即上传人：哈哈
				if(fileItem.isFormField()){
				    
					//获取普通字段的名字，例如：username
					String fieldName = fileItem.getFieldName();
					
					//获取普通字段的值，例如：哈哈
					String fieldValue = fileItem.getString("UTF-8");
					
					//显示
					//System.out.println(fieldName+":"+fieldValue);
					
				//如果是上传字段的话，即选择上传文件：e:/sh.jpg
				}else{
					
					//获取上传文件的大小，单位字节
					long size = fileItem.getSize();
					
					//如果超过了300K
					if(size > 300 * 1024){
						
						//抛出自定义异常
						throw new FileSizeException();
						
					}
					
					//获取上传文件的类型
					String contentType = fileItem.getContentType();
					
					//如果类型是image/gif
					if(!"image/jpeg".equals(contentType)){
						
						//抛出自定义异常
						throw new FileTypeException();
					}
					
					//获取上传文件的名字
					String name = fileItem.getName();
					//System.out.println(name);
					
					//封装成File对象
					File tempFile = new File(name);
					
					//获取上传文件的真实文件名
					String realFileName = tempFile.getName();
					//System.out.println(realFileName);
				
					//封装服务器指定的上传目录
					String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
					
					//产生UUID真实文件名
					String uuidRealFileName = this.makeUuidRealFileName(realFileName);
					
					//产生子目录，例如：/WEB-INF/upload/12/6
					String subUploadPath = this.makeSubUpload(uploadPath,uuidRealFileName);
					
					File file = new File(subUploadPath + "/" + uuidRealFileName);
					
					//将上传文件以IO流的方式写入指定的目录下
					fileItem.write(file);
					
					//删除临时文件，在正常情况下，自动删除临时文件
					fileItem.delete();
					
				}
			}
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write("<script>" +
					    "window.alert('上传文件成功');" +
					    "window.location.href='"+request.getContextPath()+"/upload.jsp';" +
					"</script>");
			
		}catch(FileTypeException e){
			e.printStackTrace();
			request.setAttribute("message","<font size='44'>上传文件类型必须是image/gif</font>");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request,response);
		}catch(FileSizeException e){
			e.printStackTrace();
			request.setAttribute("message","<font size='44'>上传文件不得超过300K</font>");
			request.getRequestDispatcher("/WEB-INF/message.jsp").forward(request,response);
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			//思考
		}
	}
	//将UUID拼上真实文件名后返回
	private String makeUuidRealFileName(String realFileName){
		return UUID.randomUUID().toString()+"_"+realFileName;
	} 
	//在upload总目录下，创建2层子目录，用于存上传的文件,分散总目录压力,子目录级数不限
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






