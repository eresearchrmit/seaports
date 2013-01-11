package war.model ;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import war.model.User ;

@Entity
public class UserStory {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private String name;
	@Column
	private String mode;
	@Column
	private String access;
	
	@ManyToOne
	@JoinColumn(name="owner_login")
	private User owner;
	
	@OneToMany(targetEntity=DataElement.class, mappedBy="userStory",cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DataElement> dataElements;
	

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return owner;
	}
	public void setUser(User owner) {
		this.owner = owner;
	}
	
	public String getMode() {
		return this.mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getAccess() {
		return this.access;
	}
	public void setAccess(String access) {
		this.access = access;
	}

	public List<DataElement> getDataElements() {
		return this.dataElements;
	}
	public void setDataElements(List<DataElement> dataElements) {
		this.dataElements = dataElements;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
