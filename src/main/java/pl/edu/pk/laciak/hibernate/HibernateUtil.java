package pl.edu.pk.laciak.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import pl.edu.pk.laciak.DTO.Comments;
import pl.edu.pk.laciak.DTO.Deadlines;
import pl.edu.pk.laciak.DTO.LoginData;
import pl.edu.pk.laciak.DTO.Notes;
import pl.edu.pk.laciak.DTO.Project;
import pl.edu.pk.laciak.DTO.Students;
import pl.edu.pk.laciak.DTO.Subject;
import pl.edu.pk.laciak.DTO.Task;
import pl.edu.pk.laciak.DTO.Teachers;
import pl.edu.pk.laciak.DTO.Teams;

public class HibernateUtil {
	private static SessionFactory sessionFactory = buildSessionFactory();
	
	
	private static ServiceRegistry serviceRegistry;
	
    private static SessionFactory buildSessionFactory() {
    	Configuration configuration = new Configuration();
    	configuration.addAnnotatedClass(Students.class);
    	configuration.addAnnotatedClass(LoginData.class);
    	configuration.addAnnotatedClass(Teachers.class);
    	configuration.addAnnotatedClass(Task.class);
    	configuration.addAnnotatedClass(Project.class);
    	configuration.addAnnotatedClass(Notes.class);
    	configuration.addAnnotatedClass(Teams.class);
    	configuration.addAnnotatedClass(Deadlines.class);
    	configuration.addAnnotatedClass(Comments.class);
    	configuration.addAnnotatedClass(Subject.class);
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        return sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {
    	// Close caches and connection pools
    	getSessionFactory().close();
    }
}
