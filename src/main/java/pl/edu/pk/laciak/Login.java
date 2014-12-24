package pl.edu.pk.laciak;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;

import pl.edu.pk.laciak.DTO.LoginData;
import pl.edu.pk.laciak.hibernate.HibernateUtil;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Session session;
	HttpSession s;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String response_msg = "";
		boolean logged = false;
		
		if(request.getParameter("username")!=null){
			String user = request.getParameter("username");
			String pass = request.getParameter("password");
			
			session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query q = session.createQuery("from LoginData where username=:user").setString("user", user);
			List<?> result = q.list();
			
			
			if(result.isEmpty()){
				response_msg = "Zla nazwa uzytkownika lub haslo!";
			}
			else {
				LoginData login = (LoginData)result.get(0);
				if(!login.isActive()){
					response_msg = "Konto nie zostalo aktywowane! Sprawdz e-maila";
					
				}
				else if(!login.getPassword().equals(pass)){
					response_msg = "Zla nazwa uzytkownika lub haslo!";
				}
				else {
					s = request.getSession();
					s.setAttribute("userId", login.getId());
					s.setAttribute("user_name", user);
					if(login.getStudents() != null){
						s.setAttribute("type", "student");
						s.setAttribute("userData", login.getStudents());
					}
					else if(login.getTeachers() != null){
						s.setAttribute("type", "teacher");
						s.setAttribute("userData", login.getTeachers());
					}
					else if(login.getAdmins() != null){
						s.setAttribute("type", "admin");
						s.setAttribute("userData", login.getAdmins());
					}
					logged = true;
					
				}
			}
			session.getTransaction().commit();
			
		}
		response.setContentType("text/plain");  
		response.setCharacterEncoding("UTF-8");
		
		if(logged){
			response_msg = "Zalogowany";
		}
		
		response.getWriter().write(response_msg);
	}

}
