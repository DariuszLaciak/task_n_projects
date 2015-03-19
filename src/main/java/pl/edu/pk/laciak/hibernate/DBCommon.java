package pl.edu.pk.laciak.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import pl.edu.pk.laciak.DTO.Notes;
import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.Project_step;
import pl.edu.pk.laciak.DTO.Project_task;
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
		lista = s.createQuery("from Subject where idTeacher=:id order by name").setParameter("id", teacher_id).list();
		if(s !=null)
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
		lista = s.createQuery("from Teams order by name").list();
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
	
	public static List<Students> getStudentsOfTeam(long id){
		List<Students> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		Project t = (Project) s.createQuery("from Project where id=:id").setParameter("id", id).list().get(0);
		if(t.getTeam() != null)
			lista.addAll(t.getTeam().getStudents());
		else
			lista.add(t.getStudent());
		
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Project> getProjectsForTeacher(long id){
		List<Project> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Project p where p.teacher.id=:id order by name").setParameter("id", id).list();
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Project_task> getTasksOfProject(long id){
		List<Project_task> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Project_task p where p.project.id=:id order by id").setParameter("id", id).list();
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Project_step> getStepsOfProject(long id){
		List<Project_step> lista = new ArrayList<>();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		if(!s.getTransaction().isActive())
			s.beginTransaction();
		lista = s.createQuery("from Project_step p where p.project.id=:id order by id").setParameter("id", id).list();
		s.getTransaction().commit();
		if(s.isOpen())
			s.close();
		return lista;
	}
	
	
	
	
}
