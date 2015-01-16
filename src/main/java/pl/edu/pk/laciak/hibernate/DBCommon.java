package pl.edu.pk.laciak.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.DTO.Teams;

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
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Subject> getSubjectList(long teacher_id){
		List<Subject> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Subject where idTeacher=:id").setParameter("id", teacher_id).list();
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Teams> getGroupList(){
		List<Teams> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Teams").list();
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Students> getStudents(){
		List<Students> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Students order by surname").list();
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
}
