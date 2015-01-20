package pl.edu.pk.laciak.helpers;  
import java.io.*;  

import javax.servlet.*;  
import javax.servlet.http.*;  

import pl.edu.pk.laciak.functions.FTPCommon;



public class displayPhoto extends HttpServlet {  

	/**
	 * 
	 */
	private static final long serialVersionUID = -4414707447756120921L;
	

	public void doGet(HttpServletRequest request,HttpServletResponse response)  
			throws IOException  
	{  
		response.setContentType("image/jpeg");  
		ServletOutputStream out;  
		out = response.getOutputStream();  
		long id = (long) request.getSession().getAttribute("userId");
		FileInputStream fin = new FileInputStream(FTPCommon.getPhoto(id));  

		BufferedInputStream bin = new BufferedInputStream(fin);  
		BufferedOutputStream bout = new BufferedOutputStream(out);  
		int ch =0; ;  
		while((ch=bin.read())!=-1)  
		{  
			bout.write(ch);  
		}  

		bin.close();  
		fin.close();  
		bout.close();  
		out.close();  
	}  
	
} 