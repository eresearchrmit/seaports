package war.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import war.model.UserStory;

/**
 * Class representing a data element of a user story
 * @author Guillaume Prevost
 * @since 11th Jan. 2013
 */
@Entity
@Table(name = "DataElement")
@DiscriminatorColumn(name = "type")
public class DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
    
	/**
	 * The unique ID of the Data Element
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/**
	 * The date when this data element has been created
	 */
	private Date creationDate;
	
	/**
	 * The name of the data element
	 */
	@Column
    private String name;

	/**
	 * Whether the data element is included or not in the publication of its parent User Story.
	 */
    @Column
    private boolean included;
	
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
	 * Default constructor of data element
	 */
	public DataElement() {
		this.creationDate = new Date();
	}
	
	/**
	 * Constructor of User specifying the name, login and password 
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param included: whether the data element is included or not in the publication of its parent User Story.
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 */
	public DataElement(Date creationDate, String name, boolean included, int position, UserStory userStory) {
		this.creationDate = creationDate;
		this.name = name;
		this.included = included;
		this.position = position;
		this.userStory = userStory;
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
	 * Getter for the creation date of the data element
	 * @return: the creation date of the data element
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Setter for the creation date of the data element
	 * @param value: the new creation date of the data element
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	 * Getter for the inclusion of the data element in its parent's publication
	 * @return the current inclusion of the data element
	 */
	public boolean getIncluded() {
		return this.included;
	}
	
	/**
	 * Setter for the inclusion of the data element in its parent's publication
	 * @param position: the new inclusion of the data element
	 */
	public void setIncluded(boolean included) {
		this.included = included;
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
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}