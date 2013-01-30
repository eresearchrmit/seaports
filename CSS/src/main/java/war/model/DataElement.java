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

//import org.apache.commons.codec.binary.Base64;

import war.model.UserStory;

/**
 * Class representing a data element of a user story
 * @author Guillaume Prevost
 * @since 11th Jan. 2013
 */
@Entity
@Table(name = "DataElement")
public class DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
    
	/**
	 * The unique ID of the Data Element
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/**
	 * The name of the data element
	 */
	@Column
    private String name;
	
	/**
	 * The type of the data element. The specific type 'data' is reserved for the data extracted from the database. 
	 */
	@Column
	private String type;
    
	/**
	 * The position of the data element in the user story it belongs to
	 */
    @Column
    private int position;
    
    /**
     * The user story to which this data element belongs
     */
	@ManyToOne
	@JoinColumn(name="user_story_id")
    private UserStory userStory;
	
    /**
     * The binary content of the data element
     */
    @Column
    private byte[] content;
    
    /**
     * The string conversion of the data element content
     */
    @Transient
    private String stringContent; 

	/**
	 * Default constructor of data element
	 */
	public DataElement() {
	}
	
	/**
	 * Constructor of User specifying the name, login and password 
	 * @param name: the name of the data element
	 * @param type: the type of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 * @param content: the binary content of the data element
	 */
	public DataElement(String name, String type, int position, UserStory userStory, byte[] content) {
		super();
		this.name = name;
		this.type = type;
		this.position = position;
		this.userStory = userStory;
		this.content = content;
		
		this.generateStringContent();
	}
    
	/**
	 * Getter for the unique ID of the data element
	 * @return The unique ID of the data element
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter for the unique ID of the data element
	 * @param The unique ID of the data element
	 */
	public void setId(int id) {
		this.id = id ;
	}
	
	/**
	 * Getter for the name of the data element
	 * @return the current name of the data element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for the name of the data element
	 * @param name: the new name of the data element
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the position of the data element in its user story
	 * @return the current position of the data element
	 */
	public int getPosition() {
		return this.position;
	}
	
	/**
	 * Setter for the position of the data element in its user story
	 * @param position: the new position of the data element
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	 * Getter for the type of the data element
	 * @return The current type of the data element
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter for the type of the data element in its user story
	 * @param type: the new type of the data element
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Getter for the user story containing this data element
	 * @return The user story currently containing this data element
	 */
	public UserStory getUserStory() {
		return this.userStory;
	}
	
	/**
	 * Setter for the user story containing this data element
	 * @param userStory: The new user story currently containing this data element
	 */
	public void setUserStory(UserStory userStory) {
		this.userStory = userStory;
	}
	
	/**
	 * Getter for the binary content of the data element
	 * @return The current binary content of the data element
	 */
	public byte[] getContent() {
		if (this.content == null)
			generateBinaryContent();
		return content;
	}

	/**
	 * Setter for the binary content of the data element. 
	 * It also sets the property stringContent by converting the binary content into a String.
	 * @param content
	 */
	public void setContent(byte[] content) {
		this.content = content;
		generateStringContent();
	}
	
	/**
	 * Getter for the string conversion of the data element
	 * @return The current string conversion of the data element
	 */
	public String getStringContent() {
		if (this.stringContent == null)
			generateStringContent();
		return this.stringContent;
	}
	
	/**
	 * Setter for the string content of the data element. 
	 * It also sets the property binaryContent by converting the String content into binary.
	 * @param content
	 */
	public void setStringContent(String stringContent) {
		this.stringContent = stringContent;
		generateBinaryContent();
	}
	
	/**
	 * Converts the binary content into a String and assign it to the stringContent property.
	 */
	public void generateStringContent() {
		if (this.content != null) {
			/*if (this.type != null && (this.type.equals("jpg") || this.type.equals("jpeg")))
				this.stringContent = Base64.encodeBase64String(this.content);
			else*/
				this.stringContent = new String(this.content);
		}
		else {
			this.stringContent = null;
		}
	}
	
	/**
	 * Converts the string content into binary and assign it to the content property.
	 */
	public void generateBinaryContent() {
		if (this.stringContent != null) {
			/*if (this.type != null && (this.type.equals("jpg") || this.type.equals("jpeg")))
				this.content = Base64.decodeBase64(this.stringContent);
			else*/
				this.content = this.stringContent.getBytes();
		}
		else {
			this.content = null;
		}
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
