package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Version")
public class ProjectVersion implements ObjectDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8329273867005635363L;

	
	private long id;
	private String version;
	private String changes;
	private Date date;
	
	private Students student;
	private Project project;
	
	public ProjectVersion() {
		super();
	}
	
	
	
	public ProjectVersion(String version, String changes) {
		super();
		this.version = version;
		this.changes = changes;
		this.date = new Date();
	}



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(nullable = false, length = 400)
	public String getVersion() {
		return version;
	}



	public void setVersion(String version) {
		this.version = version;
	}



	@Column(nullable = false, length = 1000)
	public String getChanges() {
		return changes;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}
	
	@Column(nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = new Date();
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idStudent", nullable = false)
	public Students getStudent() {
		return student;
	}
	public void setStudent(Students student) {
		this.student = student;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProject", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
