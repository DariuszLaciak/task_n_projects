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

import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Admins;
import pl.edu.pk.laciak.DTO.LoginData;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

/**
 * Servlet implementation class Admin
 */
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			String function = request.getParameter("action");
			switch(function){
			case "add_new_form_type":
				String type = request.getParameter("type");
				
				String form = "<form id='new_user'>";
				
				if(type.equals("group")){
					form += Common.makeHeader(3, "Format");
					form += Common.makeHeader(4, "typ,imie,nazwisko,adres,pesel,data_urodzenia,album(student),semestr(student);");
					form += Common.makeUploadFile("new_group_users");
				}
				else {
					form += Common.makeInputText("new_name", "Imię", "");
					form += Common.makeInputText("new_surname", "Nazwisko", "");
					form += Common.makeInputText("new_address", "Adres", "");
					form += Common.makeInputText("new_pesel", "PESEL", "");
					form += Common.makeInputTextReadOnly("new_birthday", "Data urodzenia", "");
					form += Common.makeHeader(4, "Typ");
					form += Common.makeRadio("new_type", "student", "Student");
					form += Common.makeRadio("new_type", "teacher", "Prowadzący");
					form += Common.makeRadio("new_type", "admin", "Admin");
					form += Common.br(2);
					form += "<script>$(document).ready(function(){ $('input[name=new_type]').change(function(){ "
							+ "student_form();"
							+ " }); });</script>";
							
				}
				form += Common.makeButton("Dodaj", "confirm_add()", "b_grey");
				form += "</form>";
				json.put("form", form);
				json.put("success", 1);
				out.println(json);
			break;
			case "add_group":
				String form_data = request.getParameter("form_data");
				String lista[] = form_data.split(";");
				LoginData ld;
				int number_added = 0;
				Session s = HibernateUtil.getSessionFactory().getCurrentSession();
				
				for(String user: lista){
					number_added++;
					user = user.trim();
					String[] values = user.split(",");
					String username = Common.makeLogin(values[1], values[2], values[4]);
					if(json.containsKey("user_no")){
						json.replace("user_no", number_added);
					}
					else {
						json.put("user_no", number_added);
					}
					if(Common.isLoginUsed(username)){
						Common.makeError(json,out,s,6);
						return;
					}
					ld = new LoginData(username, values[4], true);
					
					if(values[0].equals("student")){
						if(values.length != 8){
							Common.makeError(json,out,s,5);
							return;
						}
						
						Date date = null;
						
						try {
							date = sdf.parse(values[5]);
						} catch (ParseException e) {
							Common.makeError(json,out,s,3);
							return;
						}
						try{
							s = HibernateUtil.getSessionFactory().getCurrentSession();
							s.beginTransaction();
						Students st = new Students(values[1], values[2], values[3], Long.parseLong(values[4]), values[6], date, Integer.parseInt(values[7]));
						
						ld.setStudents(st);
						st.setLogin(ld);
						s.save(ld);
						s.getTransaction().commit();
						}
						catch(NumberFormatException e){
							Common.makeError(json,out,s,4);
							return;
						}
					}
					else if(values[0].equals("teacher")){
						if(values.length != 6){
							Common.makeError(json,out,s,5);
							return;
						}
						
						Date date = null;
						try {
							date = sdf.parse(values[5]);
						} catch (ParseException e) {
							Common.makeError(json,out,s,3);
							return;
						}
						try{
							s = HibernateUtil.getSessionFactory().getCurrentSession();
							s.beginTransaction();
						Teachers t = new Teachers(values[1], values[2], values[3], Long.parseLong(values[4]),date);
						ld.setTeachers(t);
						t.setLogin(ld);
						s.save(ld);
						s.getTransaction().commit();
						}
						catch(NumberFormatException e){
							Common.makeError(json,out,s,4);
							return;
						}
						
					}
					else if(values[0].equals("admin")){
						if(values.length != 6){
							Common.makeError(json,out,s,5);
							return;
						}
						
						Date date = null;
						try {
							date = sdf.parse(values[5]);
						} catch (ParseException e) {
							Common.makeError(json,out,s,3);
							return;
						}
						try{
							s = HibernateUtil.getSessionFactory().getCurrentSession();
							s.beginTransaction();
						Admins a = new Admins(values[1], values[2], values[3], Long.parseLong(values[4]), date);
						ld.setAdmins(a);
						a.setLogin(ld);
						s.save(ld);
						s.getTransaction().commit();
						}
						catch(NumberFormatException e){
							Common.makeError(json,out,s,4);
							return;
						}
						
					}
					else {
						Common.makeError(json,out,s,2);
						return;
					}
				}
				if(s.isOpen())
				s.close();
				json.put("added", number_added);
				json.put("success", 1);
				out.println(json);
				break;
			case "add_indiv":
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				s.beginTransaction();
				
				String[] data_form = request.getParameterValues("form_data[]");
				
				String name = data_form[0];
				String surname = data_form[1];
				String address = data_form[2];
				Long pESEL = new Long(0);
				try {
					pESEL = Long.parseLong(data_form[3]);
				}
				catch(NumberFormatException e){
					Common.makeError(json,out,s,4);
					return;
				}
				
				Date birthday = null;
				try {
					birthday = sdf.parse(data_form[4]);

				} catch (ParseException e) {
					Common.makeError(json,out,s,3);
					return;
				}
				String user_type = data_form[5];
				
				
				
				String username = Common.makeLogin(name, surname, pESEL.toString());
				
				if(Common.isLoginUsed(username)){
					Common.makeError(json,out,s,6);
					return;
				}
				ld = new LoginData(username, pESEL.toString(), true);
				
				if(user_type.equals("student")){
					String index = data_form[6];
					int period = 0;
					try{
					period = Integer.parseInt(data_form[7]);
					}
					catch(NumberFormatException e){
						Common.makeError(json,out,s,4);
						return;
					}
					s = HibernateUtil.getSessionFactory().getCurrentSession();
					if(s.getTransaction().isActive()){
						s.getTransaction().commit();
					}
					
					s.beginTransaction();
					Students s1 = new Students(name, surname, address, pESEL, index, birthday, period);
					s1.setLogin(ld);
					ld.setStudents(s1);
					s.save(ld);
					s.getTransaction().commit();
				}
				else if(user_type.equals("teacher")){
					s = HibernateUtil.getSessionFactory().getCurrentSession();
					if(s.getTransaction().isActive()){
						s.getTransaction().commit();
					}
					
					s.beginTransaction();
					Teachers t1 = new Teachers(name, surname, address, pESEL, birthday);
					t1.setLogin(ld);
					ld.setTeachers(t1);
					s.save(ld);
					s.getTransaction().commit();
				}
				else {
					s = HibernateUtil.getSessionFactory().getCurrentSession();
					if(s.getTransaction().isActive()){
						s.getTransaction().commit();
					}
					
					s.beginTransaction();
					Admins a1 = new Admins(name, surname, address, pESEL, birthday);
					a1.setLogin(ld);
					ld.setAdmins(a1);
					s.save(ld);
					s.getTransaction().commit();
				}
				if(s.isOpen())
					s.close();
				json.put("success", 1);
				out.println(json);
				break;
			case "add_new_subject":
				String sub_name = request.getParameter("name");
				String teacher = request.getParameter("teacher");
				s = HibernateUtil.getSessionFactory().getCurrentSession();
				if(s.getTransaction().isActive()){
					s.getTransaction().commit();
				}
				s.beginTransaction();
				Teachers te = (Teachers) s.load(Teachers.class, Long.parseLong(teacher));
				Subject sub = new Subject(sub_name);
				sub.setTeacher(te);
				te.getSubjects().add(sub);
				int succ = (int) s.save(sub);
				s.getTransaction().commit();
				if(succ > 0){
					json.put("success", 1);
				}
				else {
					json.put("success", 0);
				}
				if(s.isOpen())
					s.close();
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
