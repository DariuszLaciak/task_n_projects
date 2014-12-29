package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Deadlines;
import pl.edu.pk.laciak.DTO.LoginData;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Task;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

/**
 * Servlet implementation class Teacher
 */
public class Teacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Session s;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Teacher() {
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
		HttpSession sess = request.getSession();
		Teachers teacher = (Teachers) sess.getAttribute("userData");
		try{
			String function = request.getParameter("action");
			switch(function){
			case "add_task":
				String[] data_form = request.getParameterValues("form_values[]");
				String task_name = data_form[0];
				String task_start = data_form[1];
				String task_student = data_form[2];
				String task_subject = data_form[3];
				String is_deadline = data_form[4];
				String task_deadline = data_form[5];
				if(is_deadline.equals("yes")){
					task_deadline = data_form[6];
				}
				
				Date startDate = null;
				Date deadlineTime =null;
				long idStudent = 0;
				int idSubject = 0;
				try {
					startDate = sdf.parse(task_start);
				} catch (ParseException e) {
					json.put("success", "2");
					out.println(json);
				}
				try{
					idStudent = Long.parseLong(task_student);
					idSubject = Integer.parseInt(task_subject);
				}
				catch(NumberFormatException e){
					json.put("success", "3");
					out.println(json);
				}
				Task task = new Task(task_name, startDate);
				Students student = null;
				Subject subject = null;
				Deadlines deadline = null;
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				
				student = (Students)s.load(Students.class, idStudent);
				
				if(idSubject > 0){
					subject = (Subject)s.load(Subject.class, idSubject);
					task.setSubject(subject);
					subject.getTasks().add(task);
				}

				
				
				task.setStudent(student);
				student.getTasks().add(task);
				task.setTeacher(teacher);
				s.refresh(teacher);
				teacher.getTasks().add(task);
				s.persist(task);
				
				if(is_deadline.equals("yes")){
					try {
						System.out.println(task_deadline);
						deadlineTime = sdf.parse(task_deadline);
					} catch (ParseException e) {
						json.put("success", "4");
						out.println(json);
					}
					deadline = new Deadlines(deadlineTime);
					task.setDeadline(deadline);
					deadline.setTask(task);
					s.save(deadline);
					s.update(task);
				}
				
				s.getTransaction().commit();
				
				json.put("success", 1);
				out.println(json);
				break;
			}
		}
			catch(NullPointerException e){
			json.put("error", "logged_out");
			out.println(json);
			e.printStackTrace();
		}
	}

}
