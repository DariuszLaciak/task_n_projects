package pl.edu.pk.laciak.hibernate;

import java.util.List;

import org.hibernate.Session;

import pl.edu.pk.laciak.DTO.Teachers;

public abstract class DBCommon {
	static Session s;
	
	@SuppressWarnings("unchecked")
	public static List<Teachers> getTeachers(){
		List<Teachers> lista= null;
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Teachers").list();
		s.getTransaction().commit();
		
		return lista;
	}
}
