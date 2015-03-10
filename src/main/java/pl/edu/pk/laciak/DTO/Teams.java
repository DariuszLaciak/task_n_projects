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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table
public class Teams implements ObjectDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5653984065291879549L;
	private long id;
	private String name;
	
	private Students leader;
	private Set<Students> students = new HashSet<Students>();
	private Set<Project> projects = new HashSet<Project>();
	private Set<Notes> notes = new HashSet<Notes>();
	
	public Teams() {
	}
	
	
	public Teams(String name) {
		super();
		this.name = name;
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
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "teams")
	public Set<Students> getStudents() {
		return students;
	}
	public void setStudents(Set<Students> students) {
		this.students = students;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
	@PrimaryKeyJoinColumn
	public Set<Project> getProjects() {
		return projects;
	}
	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "team")
	public Set<Notes> getNotes() {
		return notes;
	}


	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idLeader", nullable = true)
	public Students getLeader() {
		return leader;
	}


	public void setLeader(Students leader) {
		this.leader = leader;
	}
	
	

}
