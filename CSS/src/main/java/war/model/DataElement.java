package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class DataElement {
	
	private static final long serialVersionUID = -1308795024262635690L;
    private int dataelementid;
    private String dataelementname;
    private byte[] dataelement;
    private String format;
    private UserStory userstory ;
    private String extension ;
    
    private StringBuffer dataelementcontent ;
    

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    public int getDataelementid() {
		return dataelementid;
	}
	
	public void setDataelementid(int dataelementid) {
		this.dataelementid = dataelementid;
	}
	
	public String getDataelementname() {
		return dataelementname;
	}
	
	public void setDataelementname(String dataelementname) {
		this.dataelementname = dataelementname;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	@ManyToOne
	@JoinColumn(name="userstory_id")
	public UserStory getUserstory() {
		return userstory;
	}
	
	public void setUserstory(UserStory userstory) {
		this.userstory = userstory;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public byte[] getDataelement() {
		return dataelement;
	}

	public void setDataelement(byte[] dataelement) {
		this.dataelement = dataelement;
	}

	@Transient
	public StringBuffer getDataelementcontent() {
		return dataelementcontent;
	}

	public void setDataelementcontent(StringBuffer dataelementcontent) {
		this.dataelementcontent = dataelementcontent;
	}
  
}
