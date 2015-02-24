package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Project_task implements ObjectDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3664862759062563335L;

	
	private long id;
	private boolean finished;
	private String text;
	
	private Project project;
	private Notes note;
	
	private Students student;
	
	public Project_task(){};
	
	public Project_task(String text) {
		super();
		this.text = text;
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
	
	@Column(nullable = false)
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	@Column(nullable = false, length = 300)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProject", nullable = false)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "pt_note", cascade = CascadeType.ALL, optional=true)
	public Notes getNote() {
		return note;
	}
	public void setNote(Notes note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idStudent", nullable = true)
	public Students getStudent() {
		return student;
	}

	public void setStudent(Students student) {
		this.student = student;
	}
	
	
}
