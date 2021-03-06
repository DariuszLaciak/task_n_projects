package pl.edu.pk.laciak.DTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@GenericGenerator(name = "generator", strategy = "foreign", 
parameters = @Parameter(name = "property", value = "login"))
@Entity
@Table
public class Students implements ObjectDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3251650928896878152L;
	private long id;
	private String name;
	private String surname;
	private String address;
	private long PESEL;
	private String album;
	private Date birthday;
	private int period;
	private LoginData login;
	private AcademicGroup academicGroup;
	private Set<Teams> leaderTeams = new HashSet<Teams>();
	private Set<Task> tasks = new HashSet<Task>();
	private Set<Project> project = new HashSet<Project>();
	private Set<Notes> notes = new HashSet<Notes>();
	private Set<Teams> teams = new HashSet<Teams>();
	private Set<Comments> comments = new HashSet<Comments>();
	private Set<Files> files = new HashSet<Files>();
	private Set<Project_task> projectTasks = new HashSet<Project_task>();
	private Set<ProjectVersion> version = new HashSet<ProjectVersion>();
	
	public Students() {}
	
	
	
	public Students(String name, String surname, String address, long pESEL,
			String album, Date birthday, int period) {
		super();
		this.name = name;
		this.surname = surname;
		this.address = address;
		PESEL = pESEL;
		this.album = album;
		this.birthday = birthday;
		this.period = period;
	}

	public void editStudents(String name, String surname, String address, long pESEL,
			Date birthday) {
		this.name = name;
		this.surname = surname;
		this.address = address;
		PESEL = pESEL;
		this.birthday = birthday;
	}


	@Id
	@GeneratedValue(generator = "generator")
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
	
	@Column(nullable = false, length = 30)
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Column(nullable = false, length = 50)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(nullable = false, length = 11)
	public long getPESEL() {
		return PESEL;
	}
	public void setPESEL(long pESEL) {
		PESEL = pESEL;
	}
	
	@Column(nullable = false, length = 15)
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
	@Column(nullable = false)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(nullable = false, length = 1)
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	public LoginData getLogin() {
		return login;
	}
	public void setLogin(LoginData login) {
		this.login = login;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	public Set<Project> getProject() {
		return project;
	}

	public void setProject(Set<Project> project) {
		this.project = project;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	public Set<Notes> getNotes() {
		return notes;
	}

	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "students_teams", joinColumns = { 
			@JoinColumn(name = "idStudent", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "idTeam", 
					nullable = false, updatable = false) })
	public Set<Teams> getTeams() {
		return teams;
	}

	public void setTeams(Set<Teams> teams) {
		this.teams = teams;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	@PrimaryKeyJoinColumn
	public Set<Comments> getComments() {
		return comments;
	}



	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
	@PrimaryKeyJoinColumn
	public Set<Files> getFiles() {
		return files;
	}



	public void setFiles(Set<Files> files) {
		this.files = files;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	@PrimaryKeyJoinColumn
	public Set<Project_task> getProjectTasks() {
		return projectTasks;
	}



	public void setProjectTasks(Set<Project_task> projectTasks) {
		this.projectTasks = projectTasks;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student")
	@PrimaryKeyJoinColumn
	public Set<ProjectVersion> getVersion() {
		return version;
	}



	public void setVersion(Set<ProjectVersion> version) {
		this.version = version;
	}


	@OneToMany(fetch = FetchType.EAGER, mappedBy = "leader")
	@PrimaryKeyJoinColumn
	public Set<Teams> getLeaderTeams() {
		return leaderTeams;
	}



	public void setLeaderTeams(Set<Teams> leaderTeams) {
		this.leaderTeams = leaderTeams;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "academicGroupId", nullable = false)
	public AcademicGroup getAcademicGroup() {
		return academicGroup;
	}



	public void setAcademicGroup(AcademicGroup academicGroup) {
		this.academicGroup = academicGroup;
	}
	
	
	
}
