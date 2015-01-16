package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table
public class Deadlines implements ObjectDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7229709012552949219L;
	private long id;
	private Date endDate;
	
	private Task task;
	private Project project;
	
	public Deadlines() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Deadlines(Date endDate) {
		super();
		this.endDate = endDate;
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
	@Column(nullable = false, length = 30)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "deadline", cascade = CascadeType.ALL, optional=true)
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "deadline", cascade = CascadeType.ALL, optional=true)
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
