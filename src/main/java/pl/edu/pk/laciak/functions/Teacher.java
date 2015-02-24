package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.ObjectNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Deadlines;
import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.Project_step;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Task;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.DTO.Teams;
import pl.edu.pk.laciak.hibernate.DBCommon;
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		HttpSession sess = request.getSession();
		Teachers teacher = (Teachers) sess.getAttribute("userData");
		String html = "";
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
				String task_text = data_form[6];
				if(is_deadline.equals("yes")){
					task_deadline = data_form[6];
					task_text = data_form[7];
				}
				
				Date startDate = null;
				Date deadlineTime =null;
				long idStudent = 0;
				int idSubject = 0;
				try {
					startDate = sdf.parse(task_start);
				} catch (ParseException e) {
					Common.makeError(json, out, s, 2);
					return;
				}
				try{
					idStudent = Long.parseLong(task_student);
					idSubject = Integer.parseInt(task_subject);
				}
				catch(NumberFormatException e){
					Common.makeError(json, out, s, 3);
					return;
				}
				Task task = new Task(task_name, startDate);
				Students student = null;
				Subject subject = null;
				Deadlines deadline = null;
				if(task_text != null)
					task.setText(task_text);
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
						deadlineTime = sdf.parse(task_deadline);
					} catch (ParseException e) {
						Common.makeError(json, out, s,4);
						return;
					}
					deadline = new Deadlines(deadlineTime);
					task.setDeadline(deadline);
					deadline.setTask(task);
					s.save(deadline);
					s.update(task);
				}
				s.getTransaction().commit();
				if(s.isOpen())
					s.close();
				json.put("success", 1);
				out.println(json);
				break;
			case "add_group":
				String[] group_data = request.getParameterValues("form_values[]");
				int i = 0;
				String group_name = "";
				List<String> students = new ArrayList<String>();
				for(String data : group_data){
					if(i ==0){
						group_name = data;
					}
					else {
						if(!students.contains(data))
							students.add(data);
					}
					i++;
				}
				Teams team = new Teams(group_name);
				List<Students> studs = new ArrayList<Students>();
				Students stu;
				long idStudents=0;
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				for(String st : students){
					try{
						idStudents = Long.parseLong(st);
					}
					catch(NumberFormatException e){
						Common.makeError(json, out, s, 3);
						return;
					}
					try{
					stu = (Students) s.load(Students.class, idStudents);
					studs.add(stu);
					stu.getTeams().add(team);
				}
					catch(ObjectNotFoundException e){
						Common.makeError(json, out, s, 2);
						return;
					}
					
				}
				team.getStudents().addAll(studs);
				s.save(team);
				s.getTransaction().commit();
				
				json.put("success", 1);
				out.println(json);
				break;
			case "project_type":
				String type = request.getParameter("type");
				html = "<br />";
				switch(type){
				case "indiv":
					html += Common.makeSelect("Student", "project_student", Common.makeSelectOptions("students"));
					html += Common.makeButton("Dodaj projekt", "add_project()", "b_grey");
					break;
				case "group":
					html += Common.makeSelect("Grupa", "project_student", Common.makeSelectOptions("project_groups"));
					html += Common.makeButton("Dodaj projekt", "add_project()", "b_grey");
					break;
					default:
						html += "<h3>Zły typ</h3>";
						break;
				}
				json.put("html", html);
				out.println(json);
				break;
			case "add_project":
				String[] project_data = request.getParameterValues("form_values[]");
				if(project_data.length != 8 && project_data.length != 9 || project_data[0].equals("")){
					Common.makeError(json, out, s, 4);
					return;
				}
				
				String p_name = project_data[0];
				String p_start = project_data[1];
				String p_subject = project_data[2];
				String p_is_deadline = project_data[3];
				String p_deadline = "";
				String project_text = project_data[5];
				String p_type = project_data[6];
				String student_or_group = project_data[7];
				
				if(p_is_deadline.equals("yes")){
					p_deadline = project_data[5];
					project_text = project_data[6];
					p_type = project_data[7];
					student_or_group = project_data[8];
				}
				
				Date start_date_p = null;
				Date deadline_p = null;
				try{
					start_date_p = sdf_date.parse(p_start);
					if(p_is_deadline.equals("yes")){
						deadline_p = sdf_date.parse(p_deadline);
					}
				}
				catch(ParseException e){
					Common.makeError(json, out, s, 2);
					return;
				}
				long id_sg = 0;
				int id_subject = 0;
				try{
					id_sg = Long.parseLong(student_or_group);
					id_subject = Integer.parseInt(p_subject);
				}
				catch(NumberFormatException e){
					Common.makeError(json, out, s, 3);
					return;
				}
				
				Students p_student = null;
				Teams p_team = null;
				Subject p_subject_db = null;
				Project project = new Project(p_name, start_date_p);
				Teachers p_teacher = (Teachers)sess.getAttribute("userData");
				
				if(project_text != null)
					project.setText(project_text);
				
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				
				if(id_subject != 0){
					p_subject_db = (Subject) s.load(Subject.class,id_subject);
					p_subject_db.getProjects().add(project);
					project.setSubject(p_subject_db);
				}
				if(p_type.equals("indiv")){
					p_student = (Students) s.load(Students.class, id_sg);
					p_student.getProject().add(project);
					project.setStudent(p_student);
				}
				else{
					p_team = (Teams) s.load(Teams.class, id_sg);
					p_team.getProjects().add(project);
					project.setTeam(p_team);
				}
				project.setTeacher(p_teacher);
				p_teacher.getProjects().add(project);
				
				s.persist(project);
				if(p_is_deadline.equals("yes")){
					Deadlines dead_p = new Deadlines(deadline_p);
					project.setDeadline(dead_p);
					dead_p.setProject(project);
					s.save(dead_p);
				}
				
				s.getTransaction().commit();
				
				
				json.put("success", 1);
				out.println(json);
				break;
			case "manage_project":
				Long man_id = Long.parseLong(request.getParameter("id"));
				
				List<Students> team_students = DBCommon.getStudentsOfTeam(man_id);
				
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				
				Project man_p = (Project) s.load(Project.class, man_id);
				html = Common.makeInputText("name", "Nowa nazwa", man_p.getName());
				if(man_p.getDeadline() != null)
					html += Common.makeInputTextReadOnly("deadline", "Nowy deadline", sdf_date.format(man_p.getDeadline().getEndDate()));
				else {
					html += Common.makeCheckBoxSendUnchecked("Dodać deadline?", "isDeadline", "yes", "no");
					html += Common.makeInputTextReadOnly("new_deadline", "", sdf_date.format(new Date()));
				}
				
				html += Common.makeHeader(3, "Przypisz zadania");
				for(Students s : team_students){
					this.s.refresh(s);
					html += Common.makeInputText("student"+s.getId(), s.getName() + " " + s.getSurname(), "");
				}
				s.getTransaction().commit();
				html += Common.makeButton("Edytuj", "manage_project()", "b_grey");
				json.put("html", html);
				out.println(json);
				break;
			case "confirm_manage_project":
				// TODO Students zadania
				String[] new_project_data = request.getParameterValues("form_values[]");
				String[] new_project_students = request.getParameterValues("form_students[]");
				String new_project_id = new_project_data[0];
				String new_project_name = new_project_data[1];
				String new_project_deadline = new_project_data[2];
				Date new_project_dead = null;
				try{
				if(new_project_data.length == 5){
					new_project_deadline = new_project_data[4];
					new_project_dead = sdf_date.parse(new_project_deadline);
				}
				else if(new_project_data.length == 3){
					new_project_dead = sdf_date.parse(new_project_deadline);
				}
				}
				catch(ParseException e){
					Common.makeError(json, out, s, 2);
					return;
				}
				long edit_pro_id = Long.parseLong(new_project_id);
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Project man_p1 = (Project) s.load(Project.class, edit_pro_id);
				man_p1.setName(new_project_name);
				if(new_project_data.length == 5){
					
					Deadlines d = new Deadlines(new_project_dead);
					man_p1.setDeadline(d);
					s.save(d);
				}
				else if(new_project_data.length == 3){
					man_p1.getDeadline().setEndDate(new_project_dead);
				}
				s.update(man_p1);
				s.getTransaction().commit();
				json.put("success", 1);
				out.println(json);
				break;
			case "add_new_step":
				html = "";
				Project p = (Project) sess.getAttribute("selectedItem");
				int step_number = p.getSteps().size() + 1;
				html += Common.makeHeader(4, "Krok numer "+step_number);
				html += Common.makeInputTextArea("new_step_text", "Komentarz", "");
				html += Common.makeButton("Dodaj", "confirm_add_new_step()", "b_green");
				json.put("html", html);
				out.println(json);
				break;
			case "confirm_add_new_step":
				String[] new_step_data = request.getParameterValues("form_values[]");
				String number = new_step_data[0];
				String desc = new_step_data[1];
				Project pr = (Project) sess.getAttribute("selectedItem");
				if(desc.trim().equals("")){
					Common.makeError(json, out, s, 2);
					return;
				}
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Project_step step = new Project_step(desc, Integer.parseInt(number));
				step.setProject(pr);
				s.save(step);
				pr.getSteps().add(step);
				s.update(pr);
				s.getTransaction().commit();
				json.put("success", 1);
				out.println(json);
				break;
			case "finishStep":
				String id_step = request.getParameter("id");
				long real_id = Long.parseLong(id_step);
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Project_step step_edit = (Project_step) s.get(Project_step.class, real_id);
				if(step_edit.getProject().getTeacher().getId() != (long)sess.getAttribute("userId")){
					Common.makeError(json, out, s, 2);
					return;
				}
				step_edit.setFinished(true);
				s.update(step_edit);
				s.getTransaction().commit();
				sess.setAttribute("selectedItem", step_edit.getProject());
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
