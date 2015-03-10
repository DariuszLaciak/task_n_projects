package pl.edu.pk.laciak.DTO;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="repository")
public class ProjectRepository implements ObjectDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5664334579393698580L;

	private long id;
	private String type;
	private String link;
	
	private Project project;
	
	public ProjectRepository(){}
	
	
	public ProjectRepository(String type, String link) {
		super();
		this.type = type;
		this.link = link;
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(nullable = false, length = 60)
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	@OneToOne(fetch = FetchType.EAGER, optional=false)
	@JoinColumn(name = "idProject", nullable = false)
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
