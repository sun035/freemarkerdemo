package com.sgcc.ythjz.util.formwork;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.sgcc.ythjz.basic.ResultWarp;
import com.sgcc.ythjz.util.IdGen;

/**
 * @author 王童
 *
 */
public class FileUpload {
	

	 public  static String upload_file(HttpServletRequest req,String destRealPath) { 
		 ResultWarp rw=null;   
		 CommonsMultipartResolver resolver = new CommonsMultipartResolver(req.getSession().getServletContext());
   	        resolver.setDefaultEncoding("UTF-8"); 	      
   	        resolver.setMaxUploadSize(1024 * 1024 * 10);//最大为10m	    	       
   	        MultipartResolver res=(MultipartResolver)resolver;
   	        MultipartHttpServletRequest multipartRequest=null;
   	        try{
           	 multipartRequest = res.resolveMultipart(req);  
   	        }catch(MaxUploadSizeExceededException e){
   	        	rw = new ResultWarp(ResultWarp.FAILED, "文件不能大于10m");
   	        	return JSON.toJSONString(rw);
   	        }	    	        
   	        if(multipartRequest == null){
   	        	rw = new ResultWarp(ResultWarp.FAILED,"未知的错误");
   	        	return JSON.toJSONString(rw);	    	          
   	        }  	          
               try {                
                   MultipartFile multipartFile = multipartRequest.getFile("fileUpload");                  
                   if(multipartFile == null){
                   	rw = new ResultWarp(ResultWarp.FAILED, "没有找到上传的文件");
	    	        	return JSON.toJSONString(rw);
                   }
                   String suffix = "";
                   String originalFilename = multipartFile.getOriginalFilename();
                   if(originalFilename == null || originalFilename.lastIndexOf(".") <= 0){
                   	rw = new ResultWarp(ResultWarp.FAILED, "文件没有后缀或没有传入文件");
	    	        	return JSON.toJSONString(rw);
                   }	                        	                    
                   suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                   if(!suffix.equals(".pdf")){
                   	rw = new ResultWarp(ResultWarp.FAILED, "该文件不是pdf格式");
	    	        	return JSON.toJSONString(rw);
                   }
                   String fileName=originalFilename.substring(0,originalFilename.indexOf(".pdf"));
                   String destFileName =IdGen.getId()+"_"+fileName+ suffix;
                   System.out.println("destFileName:"+destFileName);
                   if (!"/".equals(destRealPath.substring(destRealPath.length()-1))) {
                   	destFileName = "/" + destFileName;
           		}
                   String destFileWithPath = destRealPath+ destFileName;                                     
                   File destStaticPathDir = new File(destFileWithPath);	                    
                   if(!destStaticPathDir.exists()){
                       destStaticPathDir.mkdirs();	                        
                   }               
                   try {	                        
                       multipartFile.transferTo(destStaticPathDir);
                       System.out.println(destStaticPathDir.length());	                        
                       System.out.println("destStaticPathDir:path:"+destStaticPathDir.getPath());
                   } catch (IllegalStateException e) {
                   	 e.printStackTrace();
                   	rw = new ResultWarp(ResultWarp.FAILED, "IllegalStateException");
                      
                   } catch (IOException e) {
                   	e.printStackTrace();
                   	rw = new ResultWarp(ResultWarp.FAILED, "IOException");
                   }
                   rw = new ResultWarp(ResultWarp.SUCCESS, "文件上传成功");
                   rw.addData("filePath",destFileWithPath);
   	        	return JSON.toJSONString(rw);
               } catch (Exception e1) { 	                
                   e1.printStackTrace();
                   rw = new ResultWarp(ResultWarp.FAILED, "Exception");
               }
   	        return null;
	}

}
