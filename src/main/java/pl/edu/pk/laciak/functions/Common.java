package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
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

import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.Project_step;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Task;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.DTO.Teams;
import pl.edu.pk.laciak.helpers.BorderStyle;
import pl.edu.pk.laciak.helpers.ProjectComparator;
import pl.edu.pk.laciak.helpers.ProjectStepsComp;
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
				if(isProject)
					submenus.put("Wersja", "student_activ_version");
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
	
	public static List<List<String>> createTableDataProjectSteps(Project proj, String type){
		List<List<String>> list = new ArrayList<List<String>>();
		List<Project_step> elements = new ArrayList<Project_step>(proj.getSteps());
		Collections.sort(elements, new ProjectStepsComp());
		List<String> ps_elems = new ArrayList<String>();
		for(Project_step ps : elements){
			ps_elems = new ArrayList<String>();
			ps_elems.add(""+ps.getNumber());
			ps_elems.add(ps.getText());
			ps_elems.add(""+ps.isFinished());
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

}

