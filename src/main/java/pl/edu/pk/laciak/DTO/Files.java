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
@Table
public class Files implements ObjectDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1661027355556186372L;

	private long id;
	private String name;
	private Date date;
	private String comment;
	
	private Students owner;
	private Project id_project;
	private Task id_task;
	
	
	public Files(){
		
	}
	
	
	
	public Files(String name, String comment) {
		super();
		this.name = name;
		this.comment = comment;
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
	
	@Column(nullable = false, length = 20)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false, length = 20)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = new Date();
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idOwner", nullable = false)
	public Students getOwner() {
		return owner;
	}
	public void setOwner(Students owner) {
		this.owner = owner;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProject", nullable = true)
	public Project getId_project() {
		return id_project;
	}
	public void setId_project(Project id_project) {
		this.id_project = id_project;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTaskr", nullable = true)
	public Task getId_task() {
		return id_task;
	}
	public void setId_task(Task id_task) {
		this.id_task = id_task;
	}

	@Column(nullable = false, length = 200)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
}
