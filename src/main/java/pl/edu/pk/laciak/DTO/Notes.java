package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
public class Notes implements ObjectDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8677945528591417113L;
	private long id;
	private float value;
	private Students student;
	private Subject subject;
	private Project project;
	private Task task;
	private Project_task pt_note;
	private Project_step ps_note;
	
	public Notes() {
	}
	
	
	
	public Notes(float value) {
		super();
		this.value = value;
	}



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public long getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}
	@Column(nullable = false)
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idStudent", nullable = false)
	public Students getStudent() {
		return student;
	}
	public void setStudent(Students student) {
		this.student = student;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSubject", nullable = true)
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@OneToOne(fetch = FetchType.LAZY, optional=true)
	@PrimaryKeyJoinColumn
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@OneToOne(fetch = FetchType.LAZY, optional=true)
	@PrimaryKeyJoinColumn
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}


	@OneToOne(fetch = FetchType.EAGER, optional=true)
	@PrimaryKeyJoinColumn
	public Project_task getPt_note() {
		return pt_note;
	}



	public void setPt_note(Project_task pt_note) {
		this.pt_note = pt_note;
	}


	@OneToOne(fetch = FetchType.EAGER, optional=true)
	@PrimaryKeyJoinColumn
	public Project_step getPs_note() {
		return ps_note;
	}



	public void setPs_note(Project_step ps_note) {
		this.ps_note = ps_note;
	}
	

}
