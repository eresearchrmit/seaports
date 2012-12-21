package war.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class UserStory {
	
	private static final long serialVersionUID = -1308795024262635690L;
    private int userstoryid;
    private String userstoryname;
    private String access;
    private WorkBoard workboard ;
    
    private List<DataElement> dataelements ;
    
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    public int getUserstoryid() {
		return userstoryid;
	}
	
	public void setUserstoryid(int userstoryid) {
		this.userstoryid = userstoryid;
	}
	
	public String getUserstoryname() {
		return userstoryname;
	}
	
	public void setUserstoryname(String userstoryname) {
		this.userstoryname = userstoryname;
	}
	
	public String getAccess() {
		return access;
	}
	
	public void setAccess(String access) {
		this.access = access;
	}
		
	@OneToOne
	@JoinColumn(name="workboard_id")
	public WorkBoard getWorkboard() {
		return workboard;
	}
	
	public void setWorkboard(WorkBoard workboard) {
		this.workboard = workboard;
	}
		
	@OneToMany(targetEntity=DataElement.class, mappedBy="userstory", cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	public List<DataElement> getDataelements() {
		return dataelements;
	}

	public void setDataelements(List<DataElement> dataelements) {
		this.dataelements = dataelements;
	}

	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
    
  
}
