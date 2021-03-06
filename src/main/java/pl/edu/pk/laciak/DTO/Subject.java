package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
public class Subject implements ObjectDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6936805025419373777L;
	private int id;
	private String name;
	
	private Set<Project> projects = new HashSet<Project>();
	private Set<Notes> notes = new HashSet<Notes>();
	private Teachers teacher;
	private Set<Task> tasks = new HashSet<Task>();
	
	public Subject() {
	}
	
	
	public Subject(String name) {
		super();
		this.name = name;
	}


	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	public int getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}
	@Column(nullable = false, length = 30)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "subject")
	@PrimaryKeyJoinColumn
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "subject")
	@PrimaryKeyJoinColumn
	public Set<Notes> getNotes() {
		return notes;
	}
	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTeacher", nullable = false)
	public Teachers getTeacher() {
		return teacher;
	}


	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "subject")
	@PrimaryKeyJoinColumn
	public Set<Task> getTasks() {
		return tasks;
	}


	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
