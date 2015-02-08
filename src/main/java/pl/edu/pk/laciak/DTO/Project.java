package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
public class Project implements ObjectDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7412537744897850077L;
	private long id;
	private String name;
	private Date startDate;
	private Students student;
	private String text;
	
	private Deadlines deadline;
	private Set<Comments> comment = new HashSet<Comments>();
	private Subject subject;
	private Notes note;
	private Teachers teacher;
	private Teams team;
	private Set<Files> files = new HashSet<Files>();
	private Set<Project_task> tasks = new HashSet<Project_task>();
	private Set<Project_step> steps = new HashSet<Project_step>();
	
	public Project(){}
	
	
	
	public Project(String name, Date startDate) {
		super();
		this.name = name;
		this.startDate = startDate;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idStudent", nullable = true)
	public Students getStudent() {
		return student;
	}
	public void setStudent(Students student) {
		this.student = student;
	}

	@OneToOne(fetch = FetchType.EAGER, optional=true)
	@JoinColumn(name = "idDeadline", nullable = true)
	public Deadlines getDeadline() {
		return deadline;
	}

	public void setDeadline(Deadlines deadline) {
		this.deadline = deadline;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	@PrimaryKeyJoinColumn
	public Set<Comments> getComment() {
		return comment;
	}

	public void setComment(Set<Comments> comment) {
		this.comment = comment;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idSubject", nullable = true)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, optional=true)
	public Notes getNote() {
		return note;
	}

	public void setNote(Notes note) {
		this.note = note;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTeacher", nullable = false)
	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTeam", nullable = true)
	public Teams getTeam() {
		return team;
	}

	public void setTeam(Teams team) {
		this.team = team;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id_project")
	@PrimaryKeyJoinColumn
	public Set<Files> getFiles() {
		return files;
	}



	public void setFiles(Set<Files> files) {
		this.files = files;
	}
	
	@Column(nullable = true, length = 1000)
	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	@PrimaryKeyJoinColumn
	public Set<Project_task> getTasks() {
		return tasks;
	}



	public void setTasks(Set<Project_task> tasks) {
		this.tasks = tasks;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "project")
	@PrimaryKeyJoinColumn
	public Set<Project_step> getSteps() {
		return steps;
	}



	public void setSteps(Set<Project_step> steps) {
		this.steps = steps;
	}
	
	
	
}
