package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;

import war.model.UserStory ;


@Entity
@Table(name = "DataElement")
public class DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column
    private String name;
	
	@Column
	private String type;
    
    @Column
    private int position;
    
    @Column
    private byte[] content;
    @Transient
    private String stringContent; 
    
	@ManyToOne
	@JoinColumn(name="userstory_id")
    private UserStory userStory;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return this.position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public void generateStringContent() {
		if (this.type.equals("jpg") || this.type.equals("jpeg"))
			this.stringContent = Base64.encodeBase64String(this.content);
		else
			this.stringContent = new String(this.content);
	}
	
	public byte [] toBytes(String stringContent) {
		this.content = stringContent.getBytes();
		return this.content;
	}
	
	public String getStringContent() {
		return this.stringContent;
	}
	public void setStringContent(String stringContent) {		
		this.stringContent = stringContent;
	}
	
	public UserStory getUserStory() {
		return this.userStory;
	}
	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
