package war.model ;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import war.model.Person ;

@Entity
public class WorkBoard {
	
	private static final long serialVersionUID = -1308795024262635690L;
	private int WorkBoardID ;
	private String WorkBoardName ;
	private Person person ;
	
	
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
	@JoinColumn(name="workboard_id")
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
