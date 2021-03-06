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

import pl.edu.pk.laciak.DTO.Comments;
import pl.edu.pk.laciak.DTO.Deadlines;
import pl.edu.pk.laciak.DTO.Notes;
import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.Project_step;
import pl.edu.pk.laciak.DTO.Project_task;
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
				String[] students1 = task_student.split(",");
				Long[] realStudents = new Long[students1.length];
				for(int i = 0; i < students1.length; i++){
					realStudents[i] = Long.parseLong(students1[i]);
				}
				int idSubject = 0;
				try {
					startDate = sdf.parse(task_start);
				} catch (ParseException e) {
					Common.makeError(json, out, s, 2);
					return;
				}
				try{

					idSubject = Integer.parseInt(task_subject);
				}
				catch(NumberFormatException e){
					Common.makeError(json, out, s, 3);
					return;
				}
				for(Long st : realStudents){
				String task_name = data_form[0];
				
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				
					if(s.getTransaction().isActive())
						s.getTransaction().commit();
					s.beginTransaction();
					
					Students student = null;
					Subject subject = null;
					Deadlines deadline = null;
					
					student = (Students)s.load(Students.class, st);
					task_name += " ["+student.getName() + " " + student.getSurname()+"]";
					Task task = new Task(task_name, startDate);
					if(task_text != null)
						task.setText(task_text);
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
				}
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
				boolean leaderPicked = true;
				for(String data : group_data){
					if(i ==0){
						group_name = data;
					}
					else {
						if(data.equals("yes")){
							i++;
							leaderPicked = false;
							continue;
						}
						else if(data.equals("no")){
							continue;
						}
						else if(!students.contains(data)){
							if(!leaderPicked){
								leaderPicked = true;
								data = data+"leader";
							}
							students.add(data);
						}
					}
					i++;
				}
				Teams team = new Teams(group_name);
				List<Students> studs = new ArrayList<Students>();
				Students stu;
				long idStudents=0;
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Students studentLeader = null;
				for(String st : students){
					boolean isLeader = false;
					if(st.contains("leader")){
						st = st.substring(0,st.indexOf("leader"));
						isLeader = true;
					}
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
						if(isLeader){
							team.setLeader(stu);
							studentLeader = stu;
						}
						stu.getTeams().add(team);

					}
					catch(ObjectNotFoundException e){
						Common.makeError(json, out, s, 2);
						return;
					}

				}
				team.getStudents().addAll(studs);

				s.save(team);
				if(studentLeader != null){
					studentLeader.getLeaderTeams().add(team);
					s.update(studentLeader);
				}
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
					p_name += " ["+p_student.getName() + " " + p_student.getSurname()+"]";
					project.setName(p_name);
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

				s.getTransaction().commit();
				html += Common.makeButton("Edytuj", "manage_project()", "b_grey");
				json.put("html", html);
				out.println(json);
				break;
			case "confirm_manage_project":
				String[] new_project_data = request.getParameterValues("form_values[]");
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
				String noteStep = request.getParameter("note");
				float realNote = Float.parseFloat(noteStep);

				long real_id = Long.parseLong(id_step);
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Project_step step_edit = (Project_step) s.load(Project_step.class, real_id);
				Notes note = new Notes(realNote);
				note.setPs_note(step_edit);
				note.setDate(new Date());
				note.setProject(step_edit.getProject());
				if(step_edit.getProject().getStudent() != null){
					note.setStudent(step_edit.getProject().getStudent());
				}
				else if(step_edit.getProject().getTeam() != null){
					note.setTeam(step_edit.getProject().getTeam());
				}
				s.save(note);
				step_edit.setNote(note);
				if(step_edit.getProject().getTeacher().getId() != (long)sess.getAttribute("userId")){
					Common.makeError(json, out, s, 2);
					return;
				}
				step_edit.setFinished(true);
				s.update(step_edit);
				step_edit.getProject().getNotes().add(note);
				s.getTransaction().commit();
				sess.setAttribute("selectedItem", step_edit.getProject());
				json.put("success", 1);
				out.println(json);
				break;
			case "finishTask":
				String noteTaskS = request.getParameter("note");
				float realNoteTaskS = Float.parseFloat(noteTaskS);
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Task edit_task = (Task) sess.getAttribute("selectedItem");
				Notes noteTas = new Notes(realNoteTaskS);
				noteTas.setDate(new Date());
				noteTas.setTask(edit_task);
				noteTas.setStudent(edit_task.getStudent());
				s.save(noteTas);
				edit_task.setFinished(true);
				s.update(edit_task);
				s.getTransaction().commit();
				edit_task.setNote(noteTas);
				sess.setAttribute("selectedItem", edit_task);
				json.put("success", 1);
				out.println(json);
				break;
			case "add_new_projectTask":
				html = Common.makeInputTextArea("taskText", "Treść zadania", "");
				Project proj = (Project) sess.getAttribute("selectedItem");

				if(proj.getTeam() != null){
					html += Common.makeCheckBoxSendUnchecked("Przypisać do studenta?", "add_student", "yes", "no");
					html += Common.makeSelect("", "studentsTask", Common.makeSelectOptions("studentsOfTeam",""+proj.getId()));
				}
				html += Common.makeButton("Dodaj", "confirm_addProjectTask()", "b_green");
				json.put("html", html);
				out.println(json);
				break;
			case "confirm_addProjectTask":
				String[] new_task_data = request.getParameterValues("form_values[]");
				String taskText = new_task_data[0];
				Project proj_task = (Project) sess.getAttribute("selectedItem");
				String id_student = "";
				if(new_task_data[0].equals("")){
					Common.makeError(json, out, s, 2);
					return;
				}
				if(new_task_data.length > 1){
					if(new_task_data[1].equals("yes")){
						id_student = new_task_data[3];
					}
				}
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				proj_task = (Project) s.load(Project.class, proj_task.getId());
				Project_task pt = new Project_task(taskText);
				pt.setProject(proj_task);
				if(!id_student.equals("")){
					Students taskStudent = (Students) s.load(Students.class, Long.parseLong(id_student));
					pt.setStudent(taskStudent);
					taskStudent.getProjectTasks().add(pt);
				}
				s.save(pt);
				proj_task.getTasks().add(pt);
				s.update(proj_task);
				s.getTransaction().commit();
				sess.setAttribute("selectedItem", proj_task);
				json.put("success", 1);
				out.println(json);
				break;
			case "finishProjectTask":
				String id_task = request.getParameter("id");
				String noteTas1 = request.getParameter("note");
				float realNoteTask = Float.parseFloat(noteTas1);
				long real_id_task = Long.parseLong(id_task);
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Project_task task_edit = (Project_task) s.load(Project_task.class, real_id_task);
				Notes noteTask = new Notes(realNoteTask);
				noteTask.setPt_note(task_edit);
				noteTask.setDate(new Date());
				noteTask.setProject(task_edit.getProject());
				if(task_edit.getProject().getStudent() != null){
					noteTask.setStudent(task_edit.getProject().getStudent());
				}
				else if(task_edit.getProject().getTeam() != null){
					noteTask.setTeam(task_edit.getProject().getTeam());
				}
				s.save(noteTask);
				task_edit.setNote(noteTask);
				if(task_edit.getProject().getTeacher().getId() != (long)sess.getAttribute("userId")){
					Common.makeError(json, out, s, 2);
					return;
				}
				task_edit.setFinished(true);
				s.update(task_edit);
				task_edit.getProject().getNotes().add(noteTask);
				s.getTransaction().commit();
				sess.setAttribute("selectedItem", task_edit.getProject());
				json.put("success", 1);
				out.println(json);
				break;
			case "addNote":
				html = "";
				try{
					Project p_note = (Project)sess.getAttribute("selectedItem");
					html += Common.makeHeader(4, "Typ oceny");
					html += Common.makeRadio("note_type", "step", "Za etap");
					html += Common.makeRadio("note_type", "task", "Za zadanie");
					if(Common.isProjectNoted(p_note)){
						html += Common.makeRadioDisabled("note_type", "project_d", "Za projekt","Projekt jest już oceniony");
					}
					else {
						html += Common.makeRadio("note_type", "project", "Za projekt");
					}
					html += Common.br(1);
					html += "<div id='newNoteFormType'></div>";
					html += Common.br(2);
				}
				catch(ClassCastException e){
					Task t_note = (Task)sess.getAttribute("selectedItem");
					html += Common.makeHeader(3, "Ocena sumaryczna z zadania");
					html += Common.br(1);
				}
				html += Common.makeInputNumber("note_val", "Ocena", Double.parseDouble(Common.getProjetProperty("max_note")),
						Double.parseDouble(Common.getProjetProperty("note_step")), 
						Double.parseDouble(Common.getProjetProperty("max_note")), Double.parseDouble(Common.getProjetProperty("min_note")));
				html += Common.br(2);
				html += Common.makeButton("Wystaw", "confirmNewNote()", "b_green");
				json.put("html", html);
				out.println(json);
				break;
			case "addNoteType":
				String note_type = request.getParameter("type");
				Project pr_note = (Project) sess.getAttribute("selectedItem");
				html = Common.br(1);
				switch(note_type){
				case "task":
					html += Common.makeSelect("Zadanie", "task_note", Common.makeSelectOptions("project_task",String.valueOf(pr_note.getId())));
					break;
				case "step":
					html += Common.makeSelect("Etap numer", "step_note", Common.makeSelectOptions("project_step",String.valueOf(pr_note.getId())));
					break;
				case "project":
					html += Common.makeHeader(3, "Ocena sumaryczna z projektu");
					break;
				}
				json.put("html", html);
				out.println(json);
				break;
			case "confirmNewNote":
				String[] new_note_data = request.getParameterValues("form_values[]");

				if(sess.getAttribute("selectedItemType").equals("t")){
					String valueT = new_note_data[0];
					Task tnote = (Task) sess.getAttribute("selectedItem");
					Float exValue = null;
					try{
						exValue = Float.parseFloat(valueT);
					}
					catch(NumberFormatException e){
						Common.makeError(json, out, s, 2); // parse
						return;
					}
					s = HibernateUtil.getSessionFactory().getCurrentSession();
					s.beginTransaction();
					Notes noteT = new Notes(exValue);
					noteT.setTask(tnote);
					tnote.setNote(noteT);
					if(tnote.getSubject() != null){
						tnote.setSubject(tnote.getSubject());
					}
					noteT.setStudent(tnote.getStudent());
					noteT.setDate(new Date());
					s.save(noteT);
					s.update(tnote);
					sess.setAttribute("selectedItem", tnote);
					s.getTransaction().commit();
					json.put("success", 1);
					out.println(json);
				}
				else {
					s = HibernateUtil.getSessionFactory().getCurrentSession();
					s.beginTransaction();
					Project pnote = (Project) sess.getAttribute("selectedItem");
					String noteType = new_note_data[0];
					if(new_note_data.length < 2){
						Common.makeError(json, out, s, 4); // brak typu
						return;
					}
					String pValue = new_note_data[1];

					Notes prNote = new Notes();
					prNote.setDate(new Date());
					prNote.setProject(pnote);
					if(pnote.getSubject() != null)
						prNote.setSubject(pnote.getSubject());

					if(pnote.getStudent() == null){
						prNote.setTeam(pnote.getTeam());
					}
					else {
						prNote.setStudent(pnote.getStudent());
					}

					if(!noteType.equals("project")){
						if(new_note_data.length < 3){
							Common.makeError(json, out, s, 3); 
							return;
						}
						pValue = new_note_data[2];
						Float prNoteVal = null;
						try{
							prNoteVal = Float.parseFloat(pValue);
						}
						catch(NumberFormatException e){
							Common.makeError(json, out, s, 2);
							return;
						}
						prNote.setValue(prNoteVal);
						if(noteType.equals("step")){
							String idStep = new_note_data[1];
							Project_step psNote = (Project_step) s.load(Project_step.class, Long.parseLong(idStep));
							prNote.setPs_note(psNote);
							s.save(prNote);
							psNote.setNote(prNote);
							s.update(psNote);
						}
						else if(noteType.equals("task")){
							String idTask = new_note_data[1];
							Project_task ptNote = (Project_task) s.load(Project_task.class, Long.parseLong(idTask));
							if(ptNote.getStudent() != null){
								prNote.setTeam(null);
								prNote.setStudent(ptNote.getStudent());
							}
							prNote.setPt_note(ptNote);
							s.save(prNote);
							ptNote.setNote(prNote);
							s.update(ptNote);
						}
						else {
							Common.makeError(json, out, s, 3); // bledny typ oceny
							return;
						}
					}
					else {
						pValue = new_note_data[1];
						Float prNoteVal = null;
						try{
							prNoteVal = Float.parseFloat(pValue);
						}
						catch(NumberFormatException e){
							Common.makeError(json, out, s, 2);
							return;
						}
						prNote.setValue(prNoteVal);
						s.save(prNote);

					}
					pnote.getNotes().add(prNote);
					s.getTransaction().commit();
					sess.setAttribute("selectedItem", pnote);
					json.put("success", 1);
					out.println(json);
				}

				break;

			case "finishProject":
				String note_proj = request.getParameter("note");
				float realProjNote = Float.parseFloat(note_proj);
				Project pFinish = (Project) sess.getAttribute("selectedItem");
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Notes noteProject = new Notes(realProjNote);
				noteProject.setDate(new Date());
				noteProject.setProject(pFinish);
				if(pFinish.getStudent() != null){
					noteProject.setStudent(pFinish.getStudent());
				}
				else if(pFinish.getTeam() != null){
					noteProject.setTeam(pFinish.getTeam());
				}
				s.save(noteProject);
				pFinish.setFinished(true);
				s.update(pFinish);
				s.getTransaction().commit();
				pFinish.getNotes().add(noteProject);
				sess.setAttribute("selectedItem", pFinish);
				json.put("success", 1);
				out.println(json);

				break;

			case "studentsGroup":
				html = "";
				String idGroup = request.getParameter("id");
				long idGroupL = Long.parseLong(idGroup);
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				Teams studentTeam = (Teams) s.load(Teams.class, idGroupL);
				html += Common.makeHeader(3, "Studenci grupy "+studentTeam.getName());
				int j = 1;
				html += "<table>";
				for(Students st : studentTeam.getStudents()){
					html += "<tr><td>"+j+"</td>";
					html += "<td>"+st.getSurname()+ " " + st.getName()+"</td>";
					html += "<td>"+st.getAlbum()+"</td>";
					if(studentTeam.getLeader() != null && studentTeam.getLeader().equals(st))
						html += "<td>Leader</td>";
					html += "</tr>";
					j++;
				}
				html += "</table>";
				s.getTransaction().commit();
				json.put("form", html);
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
