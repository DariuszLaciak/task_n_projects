package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

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
public class LoginData implements ObjectDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8737302205042269387L;
	private long id;
	private String username;
	private String password;
	private boolean active;
	private Students students;
	private Teachers teachers;
	private Admins admins;
	
	public LoginData() {}
	
	
	
	public LoginData(String username, String password, boolean active) {
		super();
		this.username = username;
		this.password = password;
		this.active = active;
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
	@Column(nullable = false, length = 20)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(nullable = false, length = 20)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(nullable = false, length = 1)
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "login", cascade = CascadeType.ALL, optional=true)
	public Students getStudents() {
		return students;
	}
	public void setStudents(Students students) {
		this.students = students;
	}
	
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "login", cascade = CascadeType.ALL, optional=true)
	public Teachers getTeachers() {
		return teachers;
	}
	public void setTeachers(Teachers teachers) {
		this.teachers = teachers;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "login", cascade = CascadeType.ALL, optional=true)
	public Admins getAdmins() {
		return admins;
	}



	public void setAdmins(Admins admins) {
		this.admins = admins;
	}
	
	
}
