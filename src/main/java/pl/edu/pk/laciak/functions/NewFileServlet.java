package pl.edu.pk.laciak.functions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class NewFileServlet
 */
public class NewFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	JSONObject json;
	HttpSession s;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		s = request.getSession();
		File file;
		json = new JSONObject();

		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			Common.makeError(json, out, null, 2);
			return;
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> fields = upload.parseRequest(new ServletRequestContext(request));
			Iterator<FileItem> it = fields.iterator();
			while (it.hasNext()) {
				FileItem fileItem = it.next();
				file = File.createTempFile("uploading", "file");
				fileItem.write(file);
				s.setAttribute("uploadingFile", file);
				s.setAttribute("uploadingFileName", fileItem.getName());
				json.put("success", 1);
				out.println(json);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
