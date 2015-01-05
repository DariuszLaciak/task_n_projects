package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.helpers.BorderStyle;
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
			s.getAttribute("type");
		}
		catch(NullPointerException e){
			return false;
		}
		return true;
	}

	private static Map<String,Map<String, String>> createMenuElements(String user){

		Map<String,Map<String, String>> elems = new HashMap<String,Map<String, String>>();
		Map<String,String> submenus;

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
			submenus.put("Dodaj", "add_project");
			elems.put("Projekty", submenus);

			submenus = new HashMap<String, String>();
			submenus.put("Przegladaj", "view_tasks");
			submenus.put("Dodaj", "add_task");
			elems.put("Zadania",submenus);

			submenus = new HashMap<String, String>();
			submenus.put("Przegladaj", "view_subjects");
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

		return elems;
	}

	public static String makeMenu(String user){
		Map<String,Map<String, String>> menu_elements = createMenuElements(user);
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
		if(s.isOpen())
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

}

