package pl.edu.pk.laciak.DTO;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@GenericGenerator(name = "generator", strategy = "foreign", 
parameters = @Parameter(name = "property", value = "login"))
@Entity
@Table
public class Admins implements ObjectDTO {

	private long id;
	private String name;
	private String surname;
	private String address;
	private long PESEL;
	private Date birthday;
	private LoginData login;
	
	public Admins(){};
	
	
	
	public Admins(String name, String surname, String address, long pESEL,
			Date birthday) {
		super();
		this.name = name;
		this.surname = surname;
		this.address = address;
		PESEL = pESEL;
		this.birthday = birthday;
	}

	


	public void editAdmins(String name, String surname, String address, long pESEL,
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
	
	@Column(nullable = false)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public LoginData getLogin() {
		return login;
	}
	public void setLogin(LoginData login) {
		this.login = login;
	}
	
	
}
