package war.model ;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


import war.model.Person ;

@Entity
public class WorkBoard {
	
	private static final long serialVersionUID = -1308795024262635690L;
	private int WorkBoardID ;
	private String WorkBoardName ;
	private Person person ;
	private String mode ;
	
	private List<Files> files ;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getWorkBoardID() {
		return WorkBoardID;
	}
	
	public void setWorkBoardID(int workBoardID) {
		WorkBoardID = workBoardID;
	}
	
	public String getWorkBoardName() {
		return WorkBoardName;
	}
	
	public void setWorkBoardName(String workBoardName) {
		WorkBoardName = workBoardName;
	}

	@ManyToOne
	@JoinColumn(name="person_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@OneToMany(targetEntity=Files.class, mappedBy="workboard",cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}
	
}
