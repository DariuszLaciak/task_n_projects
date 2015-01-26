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

import pl.edu.pk.laciak.helpers.Log;
import pl.edu.pk.laciak.helpers.Operation;

@Entity
@Table
public class Logs implements ObjectDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6666238728593315768L;
	
	private long id;
	private Log log_table;
	private long id_table;
	private Operation operation;
	private String old_val;
	private String new_val;
	private Date date;
	
	private LoginData id_user;
	

	public Logs(){
		
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

	
	@Column(nullable = false, length = 30)
	public Log getLog_table() {
		return log_table;
	}


	public void setLog_table(Log log_table) {
		this.log_table = log_table;
	}

	@Column(nullable = false, length = 30)
	public long getId_table() {
		return id_table;
	}


	public void setId_table(long id_table) {
		this.id_table = id_table;
	}

	@Column(nullable = false, length = 30)
	public Operation getOperation() {
		return operation;
	}


	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Column(nullable = true, length = 30)
	public String getOld_val() {
		return old_val;
	}


	public void setOld_val(String old_val) {
		this.old_val = old_val;
	}

	@Column(nullable = true, length = 30)
	public String getNew_val() {
		return new_val;
	}


	public void setNew_val(String new_val) {
		this.new_val = new_val;
	}

	@Column(nullable = false, length = 30)
	public Date getDate() {
		return date;
	}

	
	public void setDate(Date date) {
		this.date = new Date();
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUser", nullable = false)
	public LoginData getId_user() {
		return id_user;
	}

	public void setId_user(LoginData id_user) {
		this.id_user = id_user;
	}
	
	
	
}
