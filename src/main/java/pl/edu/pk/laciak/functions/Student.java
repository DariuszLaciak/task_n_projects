package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.ProjectRepository;
import pl.edu.pk.laciak.DTO.ProjectVersion;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

/**
 * Servlet implementation class Student
 */
public class Student extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Session s;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");  
		response.setCharacterEncoding("UTF-8");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
		HttpSession session = request.getSession();
		Students student = (Students) session.getAttribute("userData");
		String html = "";
		String[] data_form = null;
		try{
			String function = request.getParameter("action");
			switch(function){
			case "addNewRepo":
				data_form = request.getParameterValues("form_values[]");
				String repoType = data_form[0];
				String repoUrl = data_form[1];
				
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Project repoProject = (Project) session.getAttribute("selectedItem");
				ProjectRepository pr = new ProjectRepository(repoType, repoUrl);
				pr.setProject(repoProject);
				s.save(pr);
				repoProject.setRepository(pr);
				s.update(repoProject);
				s.getTransaction().commit();
				session.setAttribute("selectedItem", repoProject);
				json.put("success", 1);
				out.println(json);
				break;
			case "addNewVersion":
				data_form = request.getParameterValues("form_values[]");
				String version = data_form[0];
				String changes = data_form[1];
				
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				
				Project versProject = (Project) session.getAttribute("selectedItem");
				ProjectVersion pv = new ProjectVersion(version, changes);
				pv.setProject(versProject);
				pv.setStudent(student);
				s.save(pv);
				student.getVersion().add(pv);
				versProject.getVersion().add(pv);
				s.update(student);
				s.update(versProject);
				s.getTransaction().commit();
				session.setAttribute("selectedItem", versProject);
				json.put("success", 1);
				out.println(json);
				break;
			}
		}
		catch(NullPointerException e){
			json.put("error", "logged_out");
			out.println(json);
			e.printStackTrace();
			return;
		}
	}

}
