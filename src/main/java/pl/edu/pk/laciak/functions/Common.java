package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.AcademicGroup;
import pl.edu.pk.laciak.DTO.Comments;
import pl.edu.pk.laciak.DTO.Files;
import pl.edu.pk.laciak.DTO.Notes;
import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.ProjectVersion;
import pl.edu.pk.laciak.DTO.Project_step;
import pl.edu.pk.laciak.DTO.Project_task;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Task;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.DTO.Teams;
import pl.edu.pk.laciak.helpers.BorderStyle;
import pl.edu.pk.laciak.helpers.CommentByDateComparator;
import pl.edu.pk.laciak.helpers.FilesByDateComparator;
import pl.edu.pk.laciak.helpers.ProjectByDateComparator;
import pl.edu.pk.laciak.helpers.ProjectComparator;
import pl.edu.pk.laciak.helpers.ProjectNotesComparator;
import pl.edu.pk.laciak.helpers.ProjectStepsComp;
import pl.edu.pk.laciak.helpers.ProjectTaskComparator;
import pl.edu.pk.laciak.helpers.TaskComparator;
import pl.edu.pk.laciak.hibernate.DBCommon;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

public abstract class Common {

	public static String makeHeader(int val, String header){
		return "<h"+val+">"+header+"</h"+val+">";
	}

	public static String makeInputText(String id, String label, String value){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><input type='text' id='"+id+"' name='"+id+"' value='"+value+"' size='30'  required='required'></input></div>";
	}

	public static String makeInputTextReadOnly(String id, String label, String value){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><input type='text' id='"+id+"' name='"+id+"' value='"+value+"' size='30' readonly='readonly' ></input></div>";
	}


	public static String makeInputTextMaxLength(String id, String label, String value, int maxLength){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><input type='text' id='"+id+"' name='"+id+"' value='"+value+"' size='30' maxlength="+maxLength+" required='required' ></input></div>";
	}

	public static String makeInputPassword(String id, String label){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><input type='password' id='"+id+"' name='"+id+"' size='30' required='required'></input></div>";
	}

	public static String makeInputNumber(String id, String label, double value, double step, double max, double min){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><input type='number' id='"+id+"' name='"+id+"' value='"+value+"' step='"+step+"' min='"+min+"' max='"+max+"' size='4' required='required'></input></div>";
	}

	public static String makeButton(String value,String onclick, String clas){
		return "<button class='button "+clas+"' type='button' onclick='"+onclick+"'>"+value+"</button>";
	}

	public static String makeLink(String page, String name){
		return "<a href='"+page+"'>"+name+"</a>";
	}

	public static String makeUploadFile(String id){
		return "<div class='inputs'><label class='l_input'>Wybierz plik </label><input type='file' name='"+id+"' id='"+id+"'></input></div>";
	}

	public static String makeRadio(String name, String value, String label){
		return label+":<input type='radio' name='"+name+"' value='"+value+"'></input>";
	}

	public static String makeRadioDisabled(String name, String value, String label, String title){
		return label+":<input type='radio' name='"+name+"' value='"+value+"' disabled='true' title='"+title+"'></input>";
	}

	public static String makeSelect(String label, String id, List<String[]> options){
		String select = "<div class='inputs'><label class='l_input'>"+label+"</label><select id='"+id+"' name='"+id+"' required='required'>";
		for(String[] elems : options){
			select += "<option value='"+elems[0]+"'>"+elems[1]+"</option>";
		}
		select += "</select></div>";
		return select;
	}

	public static String makeCheckBox(String label, String id, String value){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><input type='checkbox' id='"+id+"' name='"+id+"' value='"+value+"' ></input></div>";
	}

	public static String makeCheckBoxSendUnchecked(String label, String id, String value, String uncheckedvalue){
		String html = "<div class='inputs'><label class='l_input'>"+label+"</label><input type='checkbox' id='"+id+"' name='"+id+"' value='"+value+"' ></input></div>";
		html += "<input type='hidden' id='"+id+"Hidden' name='"+id+"' value='"+uncheckedvalue+"' ></input>";
		return html;
	}

	public static String makeInputPatternSelect(String pattern, int count, List<String[]> options){
		String select = "<div class='inputs'><label class='l_input'>"+count+"</label><select id='"+pattern+"_"+count+"' name='"+pattern+"_"+count+"' required='required'>";
		for(String[] elems : options){
			select += "<option value='"+elems[0]+"'>"+elems[1]+"</option>";
		}
		select += "</select></div>";
		return select;
	}

	public static String makeInputTextArea(String id, String label, String value){
		return "<div class='inputs'><label class='l_input'>"+label+"</label><textarea id='"+id+"' name='"+id+"' rows='4' cols='47'>"+value+"</textarea></div>";
	}
	@SuppressWarnings("unchecked")
	public static JSONObject JsonEncode(String html, String label){
		JSONObject obj = new JSONObject();
		obj.put(label, html);
		return obj;
	}

	public static List<String[]> makeSelectOptions(String... data){
		List<String[]> list = new ArrayList<String[]>();
		switch(data[0]){
		case "students":
			List<Students> lista = DBCommon.getStudents();
			for(Students s: lista){
				String[] str = new String[2];
				str[0] = s.getId()+"";
				str[1] = s.getSurname() + " " + s.getName();
				list.add(str);
			}
			break;
		case "subjects":
			List<Subject> lista1 = DBCommon.getSubjectList(Long.parseLong(data[1]));
			String[] st = {"0","Bez przedmiotu"};
			list.add(st);
			for(Subject s: lista1){
				String[] str = new String[2];
				str[0] = s.getId()+"";
				str[1] = s.getName();
				list.add(str);
			}
			break;
		case "project_groups":
			List<Teams> lista2 = DBCommon.getGroupList();
			for(Teams s: lista2){
				String[] str = new String[2];
				str[0] = s.getId()+"";
				str[1] = s.getName();
				list.add(str);
			}
			break;
		case "projects_teacher":
			List<Project> lista3 = DBCommon.getProjectsForTeacher(Long.parseLong(data[1]));
			String[] pr = {"0","Wybierz projekt"};
			list.add(pr);
			for(Project s: lista3){
				String[] str = new String[2];
				str[0] = s.getId()+"";
				str[1] = s.getName();
				list.add(str);
			}
			break;
		case "studentsOfTeam":
			List<Students> studs = DBCommon.getStudentsOfTeam(Long.parseLong(data[1]));
			for(Students s: studs){
				String[] str = new String[2];
				str[0] = s.getId()+"";
				str[1] = s.getSurname() + " " + s.getName();
				list.add(str);
			}
			break;
		case "project_step":
			List<Project_step> steps = DBCommon.getStepsOfProject(Long.parseLong(data[1]));
			for(Project_step pt : steps){
				if(pt.getNote() == null){
					String[] str = new String[2];
					str[0] = pt.getId()+"";
					str[1] = pt.getNumber()+"";
					list.add(str);
				}
			}
			break;
		case "project_task":
			List<Project_task> tasks = DBCommon.getTasksOfProject(Long.parseLong(data[1]));
			for(Project_task pt : tasks){
				if(pt.getNote() == null){
					String[] str = new String[2];
					str[0] = pt.getId()+"";
					String textTask = "";
					if(pt.getText().length() < 10){
						textTask = pt.getText();
					}
					else {
						textTask = pt.getText().substring(0, 10);
					}
					str[1] = textTask+"..."+"(Przez: "+(pt.getStudent()==null?"Bez przyspisania":pt.getStudent().getAlbum())+")";
					list.add(str);
				}
			}
			break;
		case "academicGroups":
			List<AcademicGroup> academic = DBCommon.getAcademicGroups();
			String[] str = new String[2];
			str[0] = "0";
			str[1] = "Wybierz";
			list.add(str);
			for(AcademicGroup ac : academic){
				str = new String[2];
				str[0] = String.valueOf(ac.getId());
				str[1] = ac.getName();
				list.add(str);
			}
			break;
		}
		return list;
	}


	public static String br(int num){
		String html = "";
		for(int i = 0 ; i < num ; i++){
			html += "<br />";
		}
		return html;
	}

	public static String insertSeparator(String color, BorderStyle style, String floating){
		String floate ="";

		if(floating != ""){
			floate = "float: "+floating+";";
		}
		else {

		}
		String ret = "<div class='separator' style='width: 100%; height: 1px; border-bottom: 1px "+style+" "+color+"; "+floate+"'></div>";
		return ret;
	}

	public static boolean isUserLogged(HttpSession s){
		try {
			s.getAttribute("type").toString();
		}
		catch(NullPointerException e){
			return false;
		}
		return true;
	}

	private static Map<String,Map<String, String>> createMenuElements(String user,String type){

		Map<String,Map<String, String>> elems = new HashMap<String,Map<String, String>>();
		Map<String,String> submenus;

		if(type.equals("normal"))
			switch(user){
			case "admin":
				submenus = new HashMap<String, String>();
				submenus.put("Przeglądaj","db_look");
				elems.put("Zarządznie bazą", submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Aktywuj", "activate");
				submenus.put("Dodaj", "add_new");
				submenus.put("Szukaj", "search");
				submenus.put("Przeglądaj", "look");

				elems.put("Zarządzanie kontami",submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Dodaj", "add_new_subject");
				submenus.put("Zarządzaj", "manage_subject");
				elems.put("Zarządzenie przedmiotami",submenus);
				break;
			case "teacher":
				submenus = new HashMap<String, String>();
				submenus.put("Lista", "st_list");
				submenus.put("Dodaj","add_group_teacher");
				elems.put("Grupy projektowe", submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Przegladaj", "view_projects");
				submenus.put("Zarządzaj","manage_project");
				submenus.put("Dodaj", "add_project");
				elems.put("Projekty", submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Przeglądaj", "view_tasks");
				submenus.put("Dodaj", "add_task");
				elems.put("Zadania",submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Przeglądaj", "view_subjects");
				elems.put("Przedmioty", submenus);
				break;
			case "student":
				submenus = new HashMap<String, String>();
				submenus.put("Lista", "project_list_st");
				elems.put("Projekty", submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Lista", "task_list_st");
				elems.put("Zadania", submenus);

				submenus = new HashMap<String, String>();
				submenus.put("Lista", "subject_list_st");
				elems.put("Przemioty", submenus);
				break;
			default:
				break;
			}
		else {
			String name= "";
			boolean isProject = false;
			if(type.equals("p")){
				name = "Wybrany projekt";
				isProject = true;
			}
			else {
				name = "Wybrane zadanie";
			}

			switch(user){
			case "teacher":
				submenus = new HashMap<String, String>();
				submenus.put("Ewidencja", "teacher_activ_look");
				submenus.put("Komentarz", "teacher_activ_comment");
				submenus.put("Pliki", "teacher_activ_files");
				submenus.put("Oceny", "teacher_activ_notes");
				if(isProject){
					submenus.put("Wersja", "teacher_activ_version");
					submenus.put("Zadania", "teacher_activ_tasks");
				}
				elems.put(name, submenus);

				break;
			case "student":
				submenus = new HashMap<String, String>();
				submenus.put("Ewidencja", "student_activ_look");
				submenus.put("Komentarz", "student_activ_comment");
				submenus.put("Pliki", "student_activ_files");
				submenus.put("Oceny", "student_activ_notes");
				if(isProject){
					submenus.put("Wersja", "student_activ_version");
					submenus.put("Zadania", "student_activ_tasks");
				}
				elems.put(name, submenus);
				break;
			}
		}

		return elems;
	}

	public static String makeMenu(String user, String type){
		Map<String,Map<String, String>> menu_elements = createMenuElements(user,type);;

		String menu = "<ul class='"+user+"'>";
		for(Entry<String, Map<String, String>> entry : menu_elements.entrySet()){
			menu += "<li class='mmenu'><a >"+entry.getKey()+"</a><ul class='submenu'>";
			for(Entry<String,String> submenu : entry.getValue().entrySet()){
				menu += "<li><a id='"+submenu.getValue()+"' href='#'>"+submenu.getKey()+"</a></li>";
			}
			menu += "</ul></li>";
		}
		menu += "</ul>";
		return menu;
	}

	public static String getPhotoUrl(String user_id){
		String url;
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			System.err.println("Error with properties");
		}
		url = properties.getProperty("project_dir")+"/images/users/"+user_id+ "/photo.jpg";
		return url;
	}

	public static String getProjetProperty(String prop){
		String value;
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			System.err.println("Error with properties");
		}
		value = properties.getProperty(prop);
		return value;
	}

	@SuppressWarnings("unchecked")
	public static void makeError(JSONObject json, PrintWriter out, Session s, int error_nr){
		json.put("success", error_nr);
		out.println(json);
		if(s!= null && s.isOpen())
			s.getTransaction().rollback();
	}

	@SuppressWarnings("rawtypes")
	public static boolean isLoginUsed(String login){
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		List l = s.createQuery("from LoginData where username=:login").setParameter("login", login).list();
		s.getTransaction().commit();
		return !l.isEmpty();
	}

	public static String makeLogin(String name, String surname, String pESEL){
		name = replacePolishLetter(name);
		surname = replacePolishLetter(surname);
		return name.substring(0,1).toLowerCase()+surname.toLowerCase()+pESEL.substring(8);
	}

	public static String replacePolishLetter(String word){
		word = word.replace("ł", "l");
		word = word.replace("ą", "a");
		word = word.replace("ę", "e");
		word = word.replace("ó", "o");
		word = word.replace("ć", "c");
		word = word.replace("ś", "s");
		word = word.replace("ż", "z");
		word = word.replace("ź", "z");
		word = word.replace("ń", "n");
		return word;
	}

	public static String createSubjectTable(List<Subject> lista){

		List<String> headers = new ArrayList<String>();
		headers.add("Nazwa");
		headers.add("");

		List<List<String>> subjects = new ArrayList<List<String>>();
		for(Subject s : lista){
			List<String> l = new ArrayList<String>();
			l.add(s.getName());
			l.add(makeButton("Szczegóły", "sub_details("+s.getId()+")", "b_blue"));
			subjects.add(l);
		}

		String html = createTable(subjects,headers);

		return html;
	}

	public static String createTable(List<List<String>> lista, List<String> headers){
		String html = "<div class='table_wrap'><table class='result_table'>";
		html += "<thead><tr>";
		for(String h : headers)
			html += "<th>"+h+"</th>";
		html += "</tr></thead>";

		int line = 1;
		String t_class = "odd";
		for(List<String> s : lista){
			if(line % 2 == 0){
				t_class = "even";
			}
			else {
				t_class = "odd";
			}
			line++;
			html += "<tr class='"+t_class+"'>";
			for(String s1: s){
				html += "<td>"+s1+"</td>";
			}
			html += "</tr>";
		}
		html += "</table></div>";
		return html;
	}

	public static String createSelectItem(List<Project> projects, List<Task> tasks){
		projects.sort(new ProjectComparator());
		tasks.sort(new TaskComparator());
		String html ="<ul>";
		if(!projects.isEmpty()){
			html += "<li class='separator'>Projekty</li>";
			for(Project p : projects){
				html += "<li class='selectElement'><a onclick='selectItem(\"p\","+p.getId()+")'><span>"+p.getName()+"</span></a></li>";
			}
		}
		if(!tasks.isEmpty()){
			html += "<li class='separator'>Zadania</li>";
			for(Task t : tasks){
				html += "<li class='selectElement'><a onclick='selectItem(\"t\","+t.getId()+")'><span>"+t.getName()+"</span></a></li>";
			}
		}
		if(tasks.isEmpty() && projects.isEmpty()){
			html += "<li><span><i>Brak przypisanych zadań lub projektów</i></span></li>";
		}
		html += "</ul>";
		return html;
	}

	public static Project selectProject(String type, Object data, long id){
		Project pr = null;
		Set<Project> projects = new HashSet<Project>();
		switch(type){
		case "teacher":
			Teachers t = (Teachers) data;
			projects.addAll(t.getProjects());
			break;
		case "student":
			Students s = (Students) data;
			projects.addAll(s.getProject());
			List<Teams> te = new ArrayList<Teams>(s.getTeams());
			for(Teams team : te){
				projects.addAll(team.getProjects());
			}
			break;
		}
		for(Project p1 : projects){
			if(p1.getId() == id){
				pr = p1;
				break;
			}
		}

		return pr;
	}

	public static Task selectTask(String type, Object data, long id){
		Task pr = null;
		Set<Task> tasks = new HashSet<Task>();
		switch(type){
		case "teacher":
			Teachers t = (Teachers) data;
			tasks.addAll(t.getTasks());
			break;
		case "student":
			Students s = (Students) data;
			tasks.addAll(s.getTasks());
			break;
		}
		for(Task p1 : tasks){
			if(p1.getId() == id){
				pr = p1;
				break;
			}
		}

		return pr;
	}

	public static String sha256(String base) {
		try{
			String cipher_base = getProjetProperty("cipher_base");
			MessageDigest digest = MessageDigest.getInstance(cipher_base);
			byte[] hash = digest.digest(base.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public static boolean doesStudentHaveSubjects(Students s){
		for(Project p : s.getProject()){
			if(p.getSubject() != null){
				return true;
			}
		}
		for(Teams t : s.getTeams()){
			for(Project p : t.getProjects()){
				if(p.getSubject() != null){
					return true;
				}
			}
		}
		for(Task t : s.getTasks()){
			if(t.getSubject() != null){
				return true;
			}
		}
		return false;
	}

	public static List<List<String>> createTableDataProjectSteps(Project proj, String type){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Project_step> elements = new ArrayList<Project_step>(proj.getSteps());
		Collections.sort(elements, new ProjectStepsComp());
		List<String> ps_elems = new ArrayList<String>();
		for(Project_step ps : elements){
			ps_elems = new ArrayList<String>();
			ps_elems.add(""+ps.getNumber());
			ps_elems.add(ps.getText());
			if(ps.isFinished())
				ps_elems.add("Tak");
			else
				ps_elems.add("Nie");
			if(type.equals("teacher") && !ps.isFinished()){
				ps_elems.add(makeButton("Zakończ", "finishStep("+ps.getId()+")", "smallButton b_green"));
			}
			else {
				ps_elems.add("");
			}
			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableDataProjectStepsHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Numer");
		ps_elems.add("Opis");
		ps_elems.add("Zakończony");
		ps_elems.add("");
		return ps_elems;
	}

	public static List<List<String>> createTableDataProjectTasks(Project proj, String type){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Project_task> elements = new ArrayList<Project_task>(proj.getTasks());
		Collections.sort(elements,new ProjectTaskComparator());
		List<String> ps_elems = new ArrayList<String>();
		for(Project_task ps : elements){
			ps_elems = new ArrayList<String>();
			if(ps.getStudent() != null){
				ps_elems.add(ps.getStudent().getSurname() +" "+ ps.getStudent().getName());
			}
			else {
				ps_elems.add("Bez przypisania");
			}
			ps_elems.add(ps.getText());
			if(ps.isFinished())
				ps_elems.add("Tak");
			else
				ps_elems.add("Nie");

			if(type.equals("teacher") && !ps.isFinished()){
				ps_elems.add(makeButton("Zakończ", "finishProjectTask("+ps.getId()+")", "smallButton b_green"));
			}
			else {
				ps_elems.add("");
			}
			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableDataProjectTaskHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Student");
		ps_elems.add("Treść");
		ps_elems.add("Zakończone");
		ps_elems.add("");
		return ps_elems;
	}

	public static List<List<String>> createTableDataNotes(Project proj){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Notes> notes = new ArrayList<Notes>(proj.getNotes());
		Collections.sort(notes,new ProjectNotesComparator());
		List<String> ps_elems = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(Notes note : notes){
			if(note != null){
				ps_elems = new ArrayList<String>();
				ps_elems.add(String.valueOf(note.getValue()));
				if(note.getPs_note() != null){
					ps_elems.add("Za etap: "+note.getPs_note().getNumber());
				}
				else if(note.getPt_note() != null){
					String task_text;
					if(note.getPt_note().getText().length() < 10){
						task_text = note.getPt_note().getText();
					}
					else {
						task_text = note.getPt_note().getText().substring(0, 10)+ "... ";
					}
					String student;
					if(note.getPt_note().getStudent()!=null){
						student = note.getPt_note().getStudent().getSurname()+ " "+note.getPt_note().getStudent().getName();
					}
					else {
						student = "Ogólne";
					}
					ps_elems.add("Za zadanie: "+task_text +", przez: "
							+ student);
				}
				else {
					ps_elems.add("Za projekt");
				}
				ps_elems.add(sdf.format(note.getDate()));
				list.add(ps_elems);
			}
		}
		return list;
	}
	public static List<String> createTableDataNotesHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Ocena");
		ps_elems.add("Powód");
		ps_elems.add("Data wystawienia");
		return ps_elems;
	}

	public static boolean isProjectNoted(Project project){

		for(Notes n : project.getNotes()){
			if(n.getPs_note() == null && n.getPt_note() == null){
				return true;
			}
		}

		return false;
	}

	public static ProjectVersion getLastVersionOfProject(Project project){
		ProjectVersion version = null;
		long id = 0;
		for(ProjectVersion v: project.getVersion()){
			if(id < v.getId()){
				id = v.getId();
				version = v;
			}
		}

		return version;
	}

	public static boolean doesStudentHaveProjects(Students student){
		if(!student.getProject().isEmpty()){
			return true;
		}
		else {
			for(Teams t : student.getTeams()){
				if(!t.getProjects().isEmpty()){
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isTeamLeader(Project project, Students student){
		if(project.getTeam() == null){
			return true;
		}
		else if(project.getTeam().getLeader() != null){
			if(project.getTeam().getLeader().getId() == student.getId()){
				return true;
			}
		}
		else if(project.getTeam().getLeader() == null){
			return true;
		}
		return false;
	}

	public static List<List<String>> createTableComments(Project proj,Task task){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Comments> comments = new ArrayList<Comments>();
		if(proj != null){
			comments.addAll(proj.getComment() );
		}
		else if(task != null){
			comments.addAll(task.getComment());
		}
		Collections.sort(comments,new CommentByDateComparator());
		List<String> ps_elems = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for(Comments c : comments){
			ps_elems = new ArrayList<String>();
			ps_elems.add(sdf.format(c.getDate()));
			ps_elems.add(c.getText());
			if(c.getStudent() != null){
				ps_elems.add(c.getStudent().getSurname() + " " +c.getStudent().getName() + " (S)");
			}
			else if(c.getTeacher() != null){
				ps_elems.add(c.getTeacher().getSurname() + " " +c.getTeacher().getName() + " (N)");
			}
			list.add(ps_elems);
		}


		return list;
	}

	public static List<String> createTableCommentsHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Data");
		ps_elems.add("Treść");
		ps_elems.add("Autor");
		return ps_elems;
	}

	public static List<List<String>> createTableGroups(Teachers teacher){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Teams> teams = new ArrayList<Teams>();
		Map<String, Integer> number = new HashMap<String, Integer>();

		for(Project p : teacher.getProjects()){
			if(p.getTeam()!=null){
				if(!teams.contains(p.getTeam())){
					teams.add(p.getTeam());
					number.put(p.getTeam().getId()+"", 1);
				}
				else {
					number.put(p.getTeam().getId()+"", number.get(p.getTeam().getId())+1);
				}
			}
		}



		List<String> ps_elems = new ArrayList<String>();

		int i = 1;
		for(Teams t : teams){
			ps_elems = new ArrayList<String>();
			ps_elems.add(String.valueOf(i));
			ps_elems.add(t.getName());
			if(number.get(t.getId()+"") != null)
				ps_elems.add(number.get(t.getId()+"")+"");
			else {
				ps_elems.add("0");
			}
			ps_elems.add(Common.makeButton("Studenci", "teamStudents("+t.getId()+")", "b_blue"));
			i++;
			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableGroupsHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Nr");
		ps_elems.add("Nazwa");
		ps_elems.add("Liczba projektów");
		ps_elems.add("Skład");
		return ps_elems;
	}


	public static List<List<String>> createTableFiles(Project proj,Task task){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Files> files = new ArrayList<Files>();
		if(proj != null)
			files.addAll(proj.getFiles());
		else if(task != null)
			files.addAll(task.getFiles());

		Collections.sort(files,new FilesByDateComparator());
		List<String> ps_elems = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		int i = 1;
		for(Files f : files){
			ps_elems = new ArrayList<String>();
			ps_elems.add(String.valueOf(i));
			ps_elems.add(f.getName());
			ps_elems.add(f.getOwner().getSurname() + " " + f.getOwner().getName());
			ps_elems.add(sdf.format(f.getDate()));
			ps_elems.add(f.getComment());
			ps_elems.add(Common.makeButton("Pobierz", "downloadFile("+f.getId()+")", "b_blue"));
			i++;
			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableFilesHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Nr");
		ps_elems.add("Nazwa");
		ps_elems.add("Autor");
		ps_elems.add("Data");
		ps_elems.add("Opis");
		ps_elems.add("");
		return ps_elems;
	}

	public static List<List<String>> createTableSubjectsStudent(Students student){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Subject> subjects = new ArrayList<Subject>();
		Map<String, Long> number = new HashMap<String, Long>();

		for(Project p : student.getProject()){
			if(p.getSubject() != null){
				if(!number.containsKey(p.getSubject().getId()+"pf"))
					number.put(p.getSubject().getId()+"pf", 0L);
				if(!number.containsKey(p.getSubject().getId()+"p"))
					number.put(p.getSubject().getId()+"p", 0L);


				if(!subjects.contains(p.getSubject())){
					subjects.add(p.getSubject());
				}
				if(number.containsKey(p.getSubject().getId()+"p")){
					number.put(p.getSubject().getId()+"p", number.get(p.getSubject().getId()+"p")+1);
				}

				if(p.isFinished())
					number.put(p.getSubject().getId()+"pf", number.get(p.getSubject().getId()+"pf")+1);
			}
		}
		for(Teams t : student.getTeams()){
			for(Project p : t.getProjects()){
				if(p.getSubject()!= null){
					if(!number.containsKey(p.getSubject().getId()+"pf"))
						number.put(p.getSubject().getId()+"pf", 0L);
					if(!number.containsKey(p.getSubject().getId()+"p"))
						number.put(p.getSubject().getId()+"p", 0L);


					if(!subjects.contains(p.getSubject())){
						subjects.add(p.getSubject());
					}
					if(number.containsKey(p.getSubject().getId()+"p")){
						number.put(p.getSubject().getId()+"p", number.get(p.getSubject().getId()+"p")+1);
					}

					if(p.isFinished())
						number.put(p.getSubject().getId()+"pf", number.get(p.getSubject().getId()+"pf")+1);
				}
			}
		}
		for(Task t : student.getTasks()){
			if(t.getSubject() != null){
				if(!number.containsKey(t.getSubject().getId()+"tf"))
					number.put(t.getSubject().getId()+"tf", 0L);
				if(!number.containsKey(t.getSubject().getId()+"t"))
					number.put(t.getSubject().getId()+"t", 0L);
				if(!subjects.contains(t.getSubject())){
					subjects.add(t.getSubject());
				}
				if(number.containsKey(t.getSubject().getId()+"t")){
					number.put(t.getSubject().getId()+"t", number.get(t.getSubject().getId()+"t")+1);
				}
				if(t.isFinished())
					number.put(t.getSubject().getId()+"tf", number.get(t.getSubject().getId()+"tf")+1);
			}
		}


		List<String> ps_elems = new ArrayList<String>();


		for(Subject s : subjects){
			ps_elems = new ArrayList<String>();

			ps_elems.add(s.getName());
			ps_elems.add(s.getTeacher().getSurname()+ " " + s.getTeacher().getName());
			if(number.get(s.getId()+"pf")==null){
				number.put(s.getId()+"pf", 0L);
			}
			if(number.get(s.getId()+"tf")==null){
				number.put(s.getId()+"tf", 0L);
			}
			if(number.get(s.getId()+"p") == null){
				number.put(s.getId()+"p", 0L);
			}

			if(number.get(s.getId()+"t") == null){
				number.put(s.getId()+"t", 0L);
			}

			ps_elems.add(""+number.get(s.getId()+"pf")+"/"+number.get(s.getId()+"p"));
			ps_elems.add(""+number.get(s.getId()+"tf")+"/"+number.get(s.getId()+"t"));

			list.add(ps_elems);
		}

		return list;
	}

	public static List<List<String>> createTableSubjectsTeacher(Teachers teacher){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Subject> subjects = new ArrayList<Subject>(teacher.getSubjects());
		Map<String, Long> number = new HashMap<String, Long>();


		for(Subject s : subjects){
			for(Project p : s.getProjects()){
				if(!number.containsKey(s.getId()+"pf"))
					number.put(s.getId()+"pf", 0L);
				if(!number.containsKey(s.getId()+"p"))
					number.put(s.getId()+"p", 0L);


				if(number.containsKey(s.getId()+"p")){
					number.put(s.getId()+"p", number.get(s.getId()+"p")+1);
				}

				if(p.isFinished())
					number.put(s.getId()+"pf", number.get(s.getId()+"pf")+1);

			}
			for(Task t : s.getTasks()){
				if(!number.containsKey(s.getId()+"tf"))
					number.put(s.getId()+"tf", 0L);
				if(!number.containsKey(s.getId()+"t"))
					number.put(s.getId()+"t", 0L);

				if(number.containsKey(s.getId()+"t")){
					number.put(s.getId()+"t", number.get(s.getId()+"t")+1);
				}
				if(t.isFinished())
					number.put(s.getId()+"tf", number.get(s.getId()+"tf")+1);

			}
		}

		List<String> ps_elems = new ArrayList<String>();

		for(Subject s : subjects){
			ps_elems = new ArrayList<String>();

			ps_elems.add(s.getName());
			if(number.get(s.getId()+"pf")==null){
				number.put(s.getId()+"pf", 0L);
			}
			if(number.get(s.getId()+"tf")==null){
				number.put(s.getId()+"tf", 0L);
			}
			if(number.get(s.getId()+"p") == null){
				number.put(s.getId()+"p", 0L);
			}

			if(number.get(s.getId()+"t") == null){
				number.put(s.getId()+"t", 0L);
			}

			ps_elems.add(""+number.get(s.getId()+"pf")+"/"+number.get(s.getId()+"p"));
			ps_elems.add(""+number.get(s.getId()+"tf")+"/"+number.get(s.getId()+"t"));

			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableSubjectsTeacherHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Nazwa przedmiotu");
		ps_elems.add("Liczba projektów (zakończonych/wszystkich)");
		ps_elems.add("Liczba zadań (zakończonych/wszystkich)");
		return ps_elems;
	}

	public static List<String> createTableSubjectsStudentHeaders(){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Nazwa przedmiotu");
		ps_elems.add("Prowadzący");
		ps_elems.add("Liczba projektów (zakończonych/wszystkich)");
		ps_elems.add("Liczba zadań (zakończonych/wszystkich)");
		return ps_elems;
	}

	public static boolean isProjectOrTaskFinished(Object activity){
		Project project = null;
		Task task = null;
		if(activity instanceof Project)
			project = (Project) activity;
		else
			task = (Task) activity;

		if(project != null){
			return project.isFinished();
		}
		else if(task != null){
			return task.isFinished();
		}

		return false;
	}

	public static List<List<String>> createTableTasks(Object user){
		Students student = null;
		Teachers teacher = null;
		if(user instanceof Students)
			student = (Students) user;
		else
			teacher = (Teachers) user;

		List<List<String>> list = new ArrayList<List<String>>();
		List<Task> tasks = null;
		if(student != null)
			tasks = new ArrayList<Task>(student.getTasks());
		else
			tasks = new ArrayList<Task>(teacher.getTasks());


		List<String> ps_elems = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		int i = 1;
		for(Task t : tasks){
			ps_elems = new ArrayList<String>();
			ps_elems.add(String.valueOf(i));
			ps_elems.add(t.getName());

			if(teacher != null)
				ps_elems.add(t.getStudent().getSurname() + " " + t.getStudent().getName());

			if(t.getSubject() != null){
				ps_elems.add(t.getSubject().getName());
			}
			else {
				ps_elems.add("n/d");
			}

			ps_elems.add(sdf.format(t.getStartDate()));
			if(t.getDeadline() != null){
				ps_elems.add(sdf.format(t.getDeadline().getEndDate()));
			}
			else {
				ps_elems.add("Brak");
			}
			if(t.getNote() != null){
				ps_elems.add(t.getNote().getValue()+"");
			}
			else {
				ps_elems.add("Brak");
			}
			if(!t.getFiles().isEmpty()){
				ps_elems.add(t.getFiles().size()+"");
			}
			else {
				ps_elems.add("0");
			}
			if(!t.getComment().isEmpty()){
				ps_elems.add(t.getComment().size()+"");
			}
			else {
				ps_elems.add("0");
			}
			if(t.isFinished()){
				ps_elems.add("Tak");
			}
			else{
				ps_elems.add("Nie");
			}
			i++;
			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableTasksHeaders(boolean isStudent){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Nr");
		ps_elems.add("Nazwa");
		if(!isStudent)
			ps_elems.add("Student");
		ps_elems.add("Przedmiot");
		ps_elems.add("Data rozpoczęcia");
		ps_elems.add("Deadline");
		ps_elems.add("Ocena");
		ps_elems.add("Liczba plików");
		ps_elems.add("Liczba komentarzy");
		ps_elems.add("Zakończony");
		return ps_elems;
	}

	public static List<List<String>> createTableProjects(Object user){
		Students student = null;
		Teachers teacher = null;
		if(user instanceof Students)
			student = (Students) user;
		else
			teacher = (Teachers) user;

		List<List<String>> list = new ArrayList<List<String>>();
		List<Project> projects = null;
		if(student != null){
			projects = new ArrayList<Project>(student.getProject());
			for(Teams t : student.getTeams()){
				projects.addAll(t.getProjects());
			}
		}
		else {
			projects = new ArrayList<Project>(teacher.getProjects());
		}

		Collections.sort(projects,new ProjectByDateComparator());

		List<String> ps_elems = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		int i = 1;
		for(Project p : projects){
			String projectNote = "Brak";
			for(Notes n : p.getNotes()){
				if(n.getProject()!= null){
					projectNote = n.getValue()+"";
				}
			}
			ps_elems = new ArrayList<String>();
			ps_elems.add(String.valueOf(i));
			ps_elems.add(p.getName());

			if(teacher != null)
				if(p.getStudent() != null){
					ps_elems.add("Indywidualny");
					ps_elems.add(p.getStudent().getSurname() + " " + p.getStudent().getName());
				}
				else {
					ps_elems.add("Grupowy");
					ps_elems.add(p.getTeam().getName());
				}

			if(p.getSubject() != null){
				ps_elems.add(p.getSubject().getName());
			}
			else {
				ps_elems.add("n/d");
			}

			ps_elems.add(sdf.format(p.getStartDate()));
			if(p.getDeadline() != null){
				ps_elems.add(sdf.format(p.getDeadline().getEndDate()));
			}
			else {
				ps_elems.add("Brak");
			}
			if(!p.getNotes().isEmpty()){
				ps_elems.add(p.getNotes().size()+"");
			}
			else {
				ps_elems.add("Brak");
			}

			ps_elems.add(projectNote);

			if(!p.getTasks().isEmpty()){
				ps_elems.add(p.getTasks().size()+"");
			}
			else {
				ps_elems.add("0");
			}
			if(!p.getFiles().isEmpty()){
				ps_elems.add(p.getFiles().size()+"");
			}
			else {
				ps_elems.add("0");
			}
			if(!p.getComment().isEmpty()){
				ps_elems.add(p.getComment().size()+"");
			}
			else {
				ps_elems.add("0");
			}
			if(!p.getSteps().isEmpty()){
				ps_elems.add(p.getSteps().size()+"");
			}
			else {
				ps_elems.add("0");
			}

			if(!p.getVersion().isEmpty()){
				ps_elems.add(getLastVersionOfProject(p).getVersion());
			}
			else {
				ps_elems.add("Brak");
			}
			if(p.isFinished()){
				ps_elems.add("Tak");
			}
			else{
				ps_elems.add("Nie");
			}
			i++;
			list.add(ps_elems);
		}

		return list;
	}

	public static List<String> createTableProjectsHeaders(boolean isStudent){
		List<String> ps_elems = new ArrayList<String>();
		ps_elems.add("Nr");
		ps_elems.add("Nazwa");
		if(!isStudent){
			ps_elems.add("Typ");
			ps_elems.add("Wykonujący");
		}
		ps_elems.add("Przedmiot");
		ps_elems.add("Data rozpoczęcia");
		ps_elems.add("Deadline");
		ps_elems.add("Liczba ocen");
		ps_elems.add("Ocena końcowa");
		ps_elems.add("Liczba zadań");
		ps_elems.add("Liczba plików");
		ps_elems.add("Liczba komentarzy");
		ps_elems.add("Liczba etapów");
		ps_elems.add("Wersja");
		ps_elems.add("Zakończony");
		return ps_elems;
	}
}

