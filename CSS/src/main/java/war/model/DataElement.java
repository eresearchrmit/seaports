package war.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
     * The way the data element should be displayed
     */
	@Enumerated(EnumType.STRING)
	private DisplayType displayType;
    
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
		setCreationDate(new Date());
		setDisplayType(DisplayType.UNDEFINED);
	}
	
	/**
	 * Constructor of User specifying all the fields except the display type
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param included: whether the data element is included or not in the publication of its parent User Story.
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 */
	public DataElement(Date creationDate, String name, boolean included, int position, UserStory userStory) {
		setCreationDate(creationDate);
		setName(name);
		setIncluded(included);
		setPosition(position);
		setUserStory(userStory);
		setDisplayType(DisplayType.UNDEFINED);
	}

	/**
	 * Constructor of User specifying all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param included: whether the data element is included or not in the publication of its parent User Story.
	 * @param position: the position of the data element in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this data element belongs
	 */
	public DataElement(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory) {
		setCreationDate(creationDate);
		setName(name);
		setIncluded(included);
		setPosition(position);
		setUserStory(userStory);
		setDisplayType(displayType);
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
	 * Getter for the display type of the data element in its user story
	 * @return the current display type of the data element
	 */
	public DisplayType getDisplayType() {
		return this.displayType;
	}
	
	/**
	 * Setter for the display type of the data element in its user story
	 * @param position: the new display type of the data element
	 */
	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
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
	
	/**
	 * The possible types of display for a data element. Default is UNDEFINED
	 * @author Guillaume Prevost
	 * @since 12th Apr. 2013
	 */
	public enum DisplayType {
		UNDEFINED("undefined"),
		TABLE("table"),
		PICTURE("picture"),
		GRAPH("graph");
		
		private String text;

		DisplayType(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}

		public static DisplayType fromString(String text) {
			if (text != null) {
				for (DisplayType b : DisplayType.values()) {
					if (text.equalsIgnoreCase(b.text)) {
						return b;
					}
				}
			}
			return null;
		}
		
		public String toString() {
			return text;
		}

		public boolean equals(String otherText) {
			return (otherText == null) ? false : text.equals(otherText);
		}
	}
}