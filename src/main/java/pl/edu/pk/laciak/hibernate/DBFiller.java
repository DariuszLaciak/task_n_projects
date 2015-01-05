package pl.edu.pk.laciak.hibernate;

import java.util.Date;

import org.hibernate.Session;

import pl.edu.pk.laciak.DTO.Admins;
import pl.edu.pk.laciak.DTO.LoginData;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Teachers;

public class DBFiller {
	
	public static void main(String[] args){
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		s.beginTransaction();
		
		LoginData ld = new LoginData("admin", "admin", true);
		Admins admin = new Admins("admin","admin","adminowo",12345678909L,new Date());
		ld.setAdmins(admin);
		admin.setLogin(ld);
		
		LoginData ld1  = new LoginData("student", "student", true);
		Students student = new Students("student", "studentę", "studentowo", 12345678909L,"099887", new Date(), 2);
		student.setLogin(ld1);
		ld1.setStudents(student);
		
		LoginData ld2 = new LoginData("teacher", "teacher", true);
		Teachers teacher = new Teachers("teacher", "teacher", "teacherowo", 12345678909L, new Date());
		teacher.setLogin(ld2);
		ld2.setTeachers(teacher);
		
		s.save(ld);
		s.save(ld1);
		s.save(ld2);
		
		s.getTransaction().commit();
		s.close();
		System.exit(0);
	}
}
