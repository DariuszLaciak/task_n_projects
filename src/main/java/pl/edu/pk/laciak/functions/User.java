package pl.edu.pk.laciak.functions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

















import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Admins;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.helpers.BorderStyle;
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
		String function = request.getParameter("action");
		s = request.getSession();
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		String user_type = (String) s.getAttribute("type");
		Object user = s.getAttribute("userData");
		long user_id = (long) s.getAttribute("userId");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
				form += Common.makeInputText("user_address", "Adres", adm.getAddress());
				form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+adm.getPESEL(),11);
				form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", format.format(adm.getBirthday()));
			}
			else if(user instanceof Teachers){
				Teachers t = (Teachers)user;
				form += Common.makeInputText("user_formname", "Imie", t.getName());
				form += Common.makeInputText("user_surname", "Nazwisko", t.getSurname());
				form += Common.makeInputText("user_address", "Adres", t.getAddress());
				form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+t.getPESEL(),11);
				form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", format.format(t.getBirthday()));
			}
			else {
				Students st = (Students)user;
				form += Common.makeInputText("user_formname", "Imie", st.getName());
				form += Common.makeInputText("user_surname", "Nazwisko", st.getSurname());
				form += Common.makeInputText("user_address", "Adres", st.getAddress());
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
				// TODO Auto-generated catch block
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
		}
	}

}
