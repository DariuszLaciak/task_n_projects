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
public class Task implements ObjectDTO {
	private long id;
	private String name;
	private Date startDate;
	private Students student;
	
	private Deadlines deadline;
	private Set<Comments> comment = new HashSet<Comments>();
	private Notes note;
	private Teachers teacher;
	
	public Task(){}
	
	
	
	public Task(String name, Date startDate) {
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idStudent", nullable = false)
	public Students getStudent() {
		return student;
	}
	public void setStudent(Students student) {
		this.student = student;
	}
	
	@OneToOne(fetch = FetchType.LAZY, optional=true)
	@PrimaryKeyJoinColumn
	public Deadlines getDeadline() {
		return deadline;
	}

	public void setDeadline(Deadlines deadline) {
		this.deadline = deadline;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
	@PrimaryKeyJoinColumn
	public Set<Comments> getComment() {
		return comment;
	}

	public void setComment(Set<Comments> comment) {
		this.comment = comment;
	}
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL, optional=true)
	public Notes getNote() {
		return note;
	}

	public void setNote(Notes note) {
		this.note = note;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTeacher", nullable = true)
	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
	}

	
	
	
	
}
