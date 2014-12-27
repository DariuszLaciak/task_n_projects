package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

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
public class Subject implements ObjectDTO {
	private int id;
	private String name;
	
	private Set<Project> projects = new HashSet<Project>();
	private Set<Notes> notes = new HashSet<Notes>();
	private Teachers teacher;
	
	public Subject() {
		// TODO Auto-generated constructor stub
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
	@PrimaryKeyJoinColumn
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
	@PrimaryKeyJoinColumn
	public Set<Notes> getNotes() {
		return notes;
	}
	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTeacher", nullable = false)
	public Teachers getTeacher() {
		return teacher;
	}


	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}
	
	
}
