package pl.edu.pk.laciak;



import org.hibernate.Session;


import pl.edu.pk.laciak.hibernate.HibernateUtil;



public class Test {
	
	public static void main(String args[]){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.getTransaction().commit();
		session.close();
		
		System.exit(0);
	}
}
