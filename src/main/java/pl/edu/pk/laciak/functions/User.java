package pl.edu.pk.laciak.functions;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import org.json.simple.JSONObject;

import pl.edu.pk.laciak.DTO.Admins;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

/**
 * Servlet implementation class User
 */
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       HttpSession s;
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
		switch(function){
		case "logout":
			s.invalidate();
			out.println("1");
			break;
		case "edit_profile":
			Object user = s.getAttribute("userData");
			JSONObject json = new JSONObject();
			String form = "<form name='edit_profile_form'><br />";
			if(user instanceof Admins){
				Admins adm = (Admins)user;
				form += Common.makeInputText("user_formname", "Imie", adm.getName());
				form += Common.makeInputText("user_surname", "Nazwisko", adm.getSurname());
				form += Common.makeInputText("user_address", "Adres", adm.getAddress());
				form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+adm.getPESEL(),11);
				form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", adm.getBirthday().toString());
			}
			else if(user instanceof Teachers){
				Teachers t = (Teachers)user;
				form += Common.makeInputText("user_formname", "Imie", t.getName());
				form += Common.makeInputText("user_surname", "Nazwisko", t.getSurname());
				form += Common.makeInputText("user_address", "Adres", t.getAddress());
				form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+t.getPESEL(),11);
				form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", t.getBirthday().toString());
			}
			else {
				Students st = (Students)user;
				form += Common.makeInputText("user_formname", "Imie", st.getName());
				form += Common.makeInputText("user_surname", "Nazwisko", st.getSurname());
				form += Common.makeInputText("user_address", "Adres", st.getAddress());
				form += Common.makeInputTextMaxLength("user_pesel", "PESEL", ""+st.getPESEL(),11);
				form += Common.makeInputTextReadOnly("user_birthday", "Data urodzenia", st.getBirthday().toString());
				form += "<br />";
				form += Common.makeInputText("user_album", "Nr albumu",st.getAlbum());
				form += Common.makeInputTextMaxLength("user_period", "Semestr", ""+st.getPeriod(),2);
			}
					
			form += "</form>";
			
			json.put("form", form);

			out.println(json);
			break;
		}
	}

}
