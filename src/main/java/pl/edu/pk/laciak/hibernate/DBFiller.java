package pl.edu.pk.laciak.hibernate;

import java.util.Date;

import org.hibernate.Session;

import pl.edu.pk.laciak.DTO.Admins;
import pl.edu.pk.laciak.DTO.LoginData;

public class DBFiller {
	
	public static void main(String[] args){
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		LoginData ld = new LoginData("admin", "admin", true);
		Admins admin = new Admins("admin","admin","adminowo",12345678909L,new Date());
		ld.setAdmins(admin);
		admin.setLogin(ld);
		
		s.saveOrUpdate(ld);
		
		s.getTransaction().commit();
	}
}
