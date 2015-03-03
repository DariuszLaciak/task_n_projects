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
public class Project_step implements ObjectDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 265382580628537377L;

	
	private long id;
	private boolean finished;
	private String text;
	private int number;
	
	private Project project;
	private Notes note;

	public Project_step() {
		super();
	}
	
	

	public Project_step(String text, int number) {
		super();
		this.text = text;
		this.number = number;
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

	@Column(nullable = false)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProject", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}


	@OneToOne(fetch = FetchType.EAGER, mappedBy = "ps_note", cascade = CascadeType.ALL, optional=true)
	public Notes getNote() {
		return note;
	}



	public void setNote(Notes note) {
		this.note = note;
	}
	
	
	
}
