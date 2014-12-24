package pl.edu.pk.laciak.functions;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import pl.edu.pk.laciak.helpers.BorderStyle;

public class Common {

	public static String makeHeader(int val, String header){
		return "<h"+val+">"+header+"</h"+val+">";
	}

	public static String makeInputText(String id, String label, String value){
		return "<div class='inputs'><label class='l_input'>"+label+":</label><input type='text' id='"+id+"' name='"+id+"' value='"+value+"' size='30'></input></div>";
	}
	
	public static String makeInputTextReadOnly(String id, String label, String value){
		return "<div class='inputs'><label class='l_input'>"+label+":</label><input type='text' id='"+id+"' name='"+id+"' value='"+value+"' size='30' readonly='readonly' ></input></div>";
	}
	
	public static String makeInputTextMaxLength(String id, String label, String value, int maxLength){
		return "<div class='inputs'><label class='l_input'>"+label+":</label><input type='text' id='"+id+"' name='"+id+"' value='"+value+"' size='30' maxlength="+maxLength+" ></input></div>";
	}

	public static String makeInputPassword(String id, String label){
		return "<div class='inputs'><label class='l_input'>"+label+":</label><input type='password' id='"+id+"' name='"+id+"' size='30'></input></div>";
	}

	public static String makeButton(String value,String onclick, String clas){
		return "<button class='"+clas+"' type='button' onclick='"+onclick+"'>"+value+"</button>";
	}

	public static String makeLink(String page, String name){
		return "<a href='"+page+"'>"+name+"</a>";
	}
	
	public static String makeUploadFile(String id){
		return "<div class='inputs'><label class='l_input'>Wybierz plik: </label><input type='file' name='"+id+"' id='"+id+"'></input></div>";
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
			break;
		case "teacher":
			submenus = new HashMap<String, String>();
			submenus.put("Lista", "st_list");
			submenus.put("Dodaj","add_group");
			elems.put("Grupy studenckie", submenus);
			
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
			elems.put("Projekty", submenus);
			
			submenus = new HashMap<String, String>();
			elems.put("Zadania", submenus);
			
			submenus = new HashMap<String, String>();
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
	
	

}

