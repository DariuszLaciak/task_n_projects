package pl.edu.pk.laciak.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
































import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Admins;
import pl.edu.pk.laciak.DTO.Comments;
import pl.edu.pk.laciak.DTO.Files;
import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Task;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.DTO.Teams;
import pl.edu.pk.laciak.helpers.BorderStyle;
import pl.edu.pk.laciak.hibernate.DBCommon;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession s;
	Session session;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public User() {
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
		String html = "";
		try{
			String function = request.getParameter("action");
			s = request.getSession();


			String user_type = (String) s.getAttribute("type");
			Object user = s.getAttribute("userData");
			long user_id = (long) s.getAttribute("userId");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			switch(function){
			case "logout":
				s.invalidate();
				json.put("logout_reply", 1);
				out.println(json);
				break;
			case "edit_profile":


				String form = "<form name='edit_profile_form'><br />";
				if(user instanceof Admins){
					Admins adm = (Admins)user;
					form += Common.makeInputText("user_formname", "Imie", adm.getName());
					form += Common.makeInputText("user_surname", "Nazwisko", adm.getSurname());
					form += Common.makeInputText("user_address", "E-mail", adm.getAddress());
					form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+adm.getPESEL(),11);
					form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", format.format(adm.getBirthday()));
				}
				else if(user instanceof Teachers){
					Teachers t = (Teachers)user;
					form += Common.makeInputText("user_formname", "Imie", t.getName());
					form += Common.makeInputText("user_surname", "Nazwisko", t.getSurname());
					form += Common.makeInputText("user_address", "E-mail", t.getAddress());
					form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+t.getPESEL(),11);
					form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", format.format(t.getBirthday()));
				}
				else {
					Students st = (Students)user;
					form += Common.makeInputText("user_formname", "Imie", st.getName());
					form += Common.makeInputText("user_surname", "Nazwisko", st.getSurname());
					form += Common.makeInputText("user_address", "E-mail", st.getAddress());
					form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+st.getPESEL(),11);
					form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", format.format(st.getBirthday()));

				}
				form += Common.insertSeparator("black", BorderStyle.SOLID,"right");
				form += "<br /><span style='float: left; margin-bottom: 10px; border-top: 15px;'>Wgraj zdjęcie profilowe</span><br /><br />";
				form += Common.makeUploadFile("photo_upload");

				form += "<div id='progress_bar'><div class='percent'>0%</div></div></form>";




				json.put("form", form);

				out.println(json);
				break;
			case "saveProfile":
				String[] form_data = request.getParameterValues("data_form[]");
				String name = form_data[0];
				String surname = form_data[1];
				String address = form_data[2];
				long pESEL = Long.parseLong(form_data[3]);

				Date birthday = null;
				try {
					birthday = format.parse(form_data[4]);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				try{

					session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					if(user_type.equals("student")){
						Students profile = (Students) session.get(Students.class, user_id);
						profile.editStudents(name, surname, address, pESEL, birthday);
						session.saveOrUpdate(profile);
						s.setAttribute("userData", profile);

					}
					else if(user_type.equals("teacher")){
						Teachers profile = (Teachers) session.get(Teachers.class, user_id);
						profile.editTeachers(name, surname, address, pESEL, birthday);
						session.saveOrUpdate(profile);
						s.setAttribute("userData", profile);
					}
					else {
						Admins profile = (Admins) session.get(Admins.class, user_id);
						profile.editAdmins(name, surname, address, pESEL, birthday);
						session.saveOrUpdate(profile);
						s.setAttribute("userData", profile);
					}
					session.getTransaction().commit();
					json.put("edited", 1);
				}
				catch (HibernateException e) {
					json.put("edited", 0);
				}

				out.println(json);
				break;
			case "selectItem":

				List<Project> projects = new ArrayList<>();
				List<Task> tasks = new ArrayList<>();
				switch(user_type){
				case "teacher":
					Teachers tea = (Teachers) user;
					projects.addAll(tea.getProjects());
					tasks.addAll(tea.getTasks());
					break;
				case "student":
					Students st = (Students) user;
					projects.addAll(st.getProject());
					for(Teams t : st.getTeams()){
						projects.addAll(t.getProjects());
					}
					tasks.addAll(st.getTasks());
					break;
				}

				html += Common.createSelectItem(projects,tasks);
				json.put("html", html);
				out.println(json);
				break;
			case "confirmSelectItem":
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				if(session.getTransaction().isActive())
					session.getTransaction().commit();
				session.beginTransaction();
				if(user instanceof Teachers){
					user = session.get(Teachers.class, ((Teachers) user).getId());
				}
				else{
					user = session.get(Students.class, ((Students) user).getId());
				}
				session.getTransaction().commit();
				String type = request.getParameter("type");
				String id = request.getParameter("id");
				long id_item = Long.parseLong(id);
				Project selected = null;
				Task selectedT = null;
				if(type.equalsIgnoreCase("p")){
					selected = Common.selectProject(user_type, user, id_item);
					if(selected == null){
						Common.makeError(json, out, session, 2);
						return;
					}

				}
				else if(type.equals("t")){
					selectedT = Common.selectTask(user_type, user, id_item);
					if(selectedT == null){
						Common.makeError(json, out, session, 2);
						return;
					}
				}

				s.setAttribute("selectedItemType", type);
				if(type.equals("p")){
					s.setAttribute("selectedItem", selected);
					s.setAttribute("selectedItemName", selected.getName());
				}
				else {
					s.setAttribute("selectedItem", selectedT);
					s.setAttribute("selectedItemName", selectedT.getName());
				}	
				json.put("name",s.getAttribute("selectedItemName"));
				json.put("menuToCreate",Common.makeMenu(user_type, s.getAttribute("selectedItemType").toString()));
				json.put("success", 1);
				out.println(json);
				break;
			case "checkSession":
				if(s.getAttribute("userId") == null) {
					json.put("success", 1);
					System.err.println("sesja wygasła");
				}
				out.println(json);
				break;
			case "newComment":
				String[] new_comment_data = request.getParameterValues("form_values[]");
				String com_text = new_comment_data[0];

				session = HibernateUtil.getSessionFactory().getCurrentSession();
				if(session.getTransaction().isActive())
					session.getTransaction().commit();
				session.beginTransaction();
				Comments comment = new Comments(com_text);
				comment.setDate(new Date());
				Teachers teacher_com = null;
				Students student_com = null;
				Project proj_com = null;
				Task task_com = null;
				if(s.getAttribute("type").equals("teacher")){
					teacher_com = (Teachers) s.getAttribute("userData");
					comment.setTeacher(teacher_com);
				}
				else {
					student_com = (Students) s.getAttribute("userData");
					comment.setStudent(student_com);
				}
				if(s.getAttribute("selectedItemType").equals("p")){
					proj_com = (Project) s.getAttribute("selectedItem");
					comment.setProject(proj_com);
				}
				else {
					task_com = (Task) s.getAttribute("selectedItem");
					comment.setTask(task_com);
				}
				session.save(comment);

				if(teacher_com != null){
					teacher_com.getComment().add(comment);
					session.update(teacher_com);
				}
				else {
					student_com.getComments().add(comment);
					session.update(student_com);
				}
				if(proj_com != null){
					proj_com.getComment().add(comment);
					session.update(proj_com);
					s.setAttribute("selectedItem", proj_com);
				}
				else {
					task_com.getComment().add(comment);
					session.update(task_com);
					s.setAttribute("selectedItem", task_com);
				}
				session.getTransaction().commit();

				json.put("success", 1);
				out.println(json);
				break;
			case "downloadFile":
				String idFile = request.getParameter("id");
				long realId = 0;
				try {
					realId = Long.parseLong(idFile);
				}
				catch(NumberFormatException e){
					Common.makeError(json, out, null, 2);
					return;
				}
				Files dlFile = null;
				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();

				dlFile = (Files) session.get(Files.class, realId);

				session.getTransaction().commit();

				long activId = 0;
				boolean isProject = true;
				if(dlFile != null){
					if(s.getAttribute("selectedItemType").equals("p")){
						Project p = (Project) s.getAttribute("selectedItem");
						activId = p.getId();
						if(dlFile.getId_project() == null || (dlFile.getId_project() != null && p.getId() != dlFile.getId_project().getId())){
							Common.makeError(json, out, null, 3);
							return;
						}
					}
					else {
						Task t = (Task) s.getAttribute("selectedItem");
						activId = t.getId();
						isProject = false;
						if(dlFile.getId_project() == null || (dlFile.getId_project() != null && t.getId() != dlFile.getId_project().getId())){
							Common.makeError(json, out, null, 3);
							return;
						}
					}
					File dl = FTPCommon.downloadFile(dlFile.getName(), activId, isProject);
					s.setAttribute("downloadingFile", dl);
					json.put("fileName",dlFile.getName());
					json.put("success", 1);
					out.println(json);
				}
				else {
					Common.makeError(json, out, null, 4);
					return;
				}
				break;
			case "finishSomething":
				
				html = "";
				html += "Zamierzasz zakończyć aktywność. Oceń ją: ";
				html += Common.br(2);
				html += Common.makeInputNumber("finishNote", "Ocena",  Double.parseDouble(Common.getProjetProperty("max_note")),
						Double.parseDouble(Common.getProjetProperty("note_step")), 
						Double.parseDouble(Common.getProjetProperty("max_note")), Double.parseDouble(Common.getProjetProperty("min_note")));
				json.put("form", html);

				out.println(json);
				break;
			case "assignStudents":
				
				html = "";
				html += "Przypisz studentów: ";
				html += Common.br(2);
				html += "<div id='assigningWindow'>";
				html += "<div id='assignedStudents'></div><div id='studentList'>";
				html += Common.makeSelect("Grupa akademicka", "academicGroupSelect", Common.makeSelectOptions("academicGroups"));
				html += "<div id='studentListUl'></div></div></div>";
				json.put("form", html);

				out.println(json);
				break;
			case "fillStudentsAcademic":
				String idAcademic = request.getParameter("id");
				long realIdAcademic = Long.parseLong(idAcademic);
				List<Students> studentsAcadmic = DBCommon.getStudentsAcademic(realIdAcademic);
				html = "<ul>";
				if(studentsAcadmic.isEmpty()){
					html += "<li class='empty'>Brak studentów</li>";
				}
				else {
					for(Students s : studentsAcadmic){
						html += "<li class='studentAcademicLi' id='student_"+s.getId()+"'>"+s.getName()+" "+s.getSurname()+"</li>";
					}
				}
				html += "</ul>";
				json.put("form", html);

				out.println(json);
				break;
			}
		}

		catch(NullPointerException e){
			json.put("error", "logged_out");
			out.println(json);
		}
	}
}
