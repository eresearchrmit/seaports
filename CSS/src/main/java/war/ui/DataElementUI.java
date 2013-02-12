package war.ui;

import java.util.Date;
import java.util.List;

import war.model.CsiroData;
import war.model.DataElement;
import war.model.DataElementCsiro;
import war.model.DataElementEngineeringModel;
import war.model.DataElementFile;
import war.model.DataElementText;
import war.model.EngineeringModelData;
import war.model.UserStory;

/**
 * Class representing a data element of a user story
 * @author Guillaume Prevost
 * @since 07th Feb. 2013
 */
public class DataElementUI {

	private static final long serialVersionUID = -1308795024262635690L;

	private String type;
	
	public String getType() {
		return this.type;
	}
	
	/* ************* CONSTRUCTORS OF DataElementUI **************** */
	
	/**
	 * Default constructor of data element
	 */
	public DataElementUI() {
		this.creationDate = new Date();
	}
	
	/**
	 * Constructor of User specifying the standard Data Element Fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param included: whether the data element is included or not in the publication of its parent User Story.
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 */
	public DataElementUI(Date creationDate, String name, boolean included, int position, UserStory userStory) {
		this.creationDate = creationDate;
		this.name = name;
		this.included = included;
		this.position = position;
		this.userStory = userStory;
		
		this.type = "none";
	}
	

	/**
	 * Constructor of data element from a DataElement
	 */
	public DataElementUI(DataElement dataElement) {
		this.id = dataElement.getId();
		this.creationDate = dataElement.getCreationDate();
		this.name = dataElement.getName();
		this.included = dataElement.getIncluded();
		this.position = dataElement.getPosition();
		this.userStory = dataElement.getUserStory();
		
		this.type = "none";
	}
	
	/**
	 * Constructor of data element from a DataElementFile
	 */
	public DataElementUI(DataElementFile dataElementFile) {
		this.id = dataElementFile.getId();
		this.creationDate = dataElementFile.getCreationDate();
		this.name = dataElementFile.getName();
		this.included = dataElementFile.getIncluded();
		this.position = dataElementFile.getPosition();
		this.userStory = dataElementFile.getUserStory();
		
		this.filetype = dataElementFile.getFiletype();
		this.content = dataElementFile.getContent();
		this.stringContent = dataElementFile.getStringContent();
		this.type = "file";
	}
    
	/**
	 * Constructor of data element from a DataElementText
	 */
	public DataElementUI(DataElementText dataElementText) {
		this.id = dataElementText.getId();
		this.creationDate = dataElementText.getCreationDate();
		this.name = dataElementText.getName();
		this.included = dataElementText.getIncluded();
		this.position = dataElementText.getPosition();
		this.userStory = dataElementText.getUserStory();
		
		this.text = dataElementText.getText();
		this.type = "text";
	}
	
	/**
	 * Constructor of data element from a DataElementCsiro
	 */
	public DataElementUI(DataElementCsiro dataElementCsiro) {
		this.id = dataElementCsiro.getId();
		this.creationDate = dataElementCsiro.getCreationDate();
		this.name = dataElementCsiro.getName();
		this.included = dataElementCsiro.getIncluded();
		this.position = dataElementCsiro.getPosition();
		this.userStory = dataElementCsiro.getUserStory();
		
		this.csiroDataList = dataElementCsiro.getCsiroDataList();
		this.type = "csiro";
	}
	
	/**
	 * Constructor of data element from a DataElementEngineeringModel
	 */
	public DataElementUI(DataElementEngineeringModel dataElementEngineeringModel) {
		this.id = dataElementEngineeringModel.getId();
		this.creationDate = dataElementEngineeringModel.getCreationDate();
		this.name = dataElementEngineeringModel.getName();
		this.included = dataElementEngineeringModel.getIncluded();
		this.position = dataElementEngineeringModel.getPosition();
		this.userStory = dataElementEngineeringModel.getUserStory();
		
		this.engineeringModelDataList = dataElementEngineeringModel.getEngineeringModelDataList();
		this.type = "engineeringmodel";
	}
	
	/* ************* GENERIC FIELDS FOR ALL DataElement CLASSES **************** */
	
	/**
	 * The unique ID of the Data Element
	 */
	private int id;

	/**
	 * The date when this data element has been created
	 */
	private Date creationDate;
	
	/**
	 * The name of the data element
	 */
    private String name;

	/**
	 * Whether the data element is included or not in the publication of its parent User Story.
	 */
    private boolean included;
	
	/**
	 * The position of the data element in the user story it belongs to
	 */
    private int position;
    
    /**
     * The user story to which this data element belongs
     */
    private UserStory userStory;
	
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
	
	/* *********************************************************************** */
	
	
	
	/* ************* SPECIFIC FIELDS OF DataElementFile CLASS **************** */
	
	/**
	 * The type of the file. 
	 */
	private String filetype;

    /**
     * The binary content of the file
     */
    private byte[] content;
    
    /**
     * The string conversion of the file content
     */
    private String stringContent; 
    
	/**
	 * Getter for the type of the file
	 * @return The current type of the file
	 */
	public String getFiletype() {
		return filetype;
	}
	
	/**
	 * Setter for the type of the file in its user story
	 * @param type: the new type of the file
	 */
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	/**
	 * Getter for the binary content of the file
	 * @return The current binary content of the file
	 */
	public byte[] getContent() {
		return content;
	}

	/**
	 * Setter for the binary content of the file.
	 * @param content: the new binary content of the file
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	/**
	 * Getter for the string conversion of the file
	 * @return The current string conversion of the file
	 */
	public String getStringContent() {
		return this.stringContent;
	}
	
	/**
	 * Setter for the string content of the file.
	 * @param content: the new string content of the file.
	 */
	public void setStringContent(String stringContent) {
		this.stringContent = stringContent;
	}
	
	/* *********************************************************************** */
	
	
	
	
	/* ************* SPECIFIC FIELDS OF DataElementText CLASS **************** */
	
	/**
	 * The content of the text item.
	 */
	private String text;
	
	/**
	 * Getter for the text of the text item
	 * @return the current text of the text item
	 */
	public String getText() {
		return text;
	}

	/**
	 * Setter for the text of the text item
	 * @param the new text of the text item
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/* *********************************************************************** */
	
    

	/* ************* SPECIFIC FIELDS OF DataElementCsiro CLASS *************** */
	
	private List<CsiroData> csiroDataList;

	/**
	 * Getter for the list of CSIRO data in the Data Element
	 * @return The current list of CSIRO data in the Data Element
	 */
	public List<CsiroData> getCsiroDataList() {
		return this.csiroDataList;
	}
	
	/**
	 * Setter for the list of CSIRO data in the Data Element
	 * @return The new list of CSIRO data in the Data Element
	 */
	public void setCsiroDataList(List<CsiroData> csiroDataList) {
		this.csiroDataList = csiroDataList;
	}
	
	/* *********************************************************************** */
	
	
	
	/* ******* SPECIFIC FIELDS OF DataElementEngineeringModel CLASS ********** */

	private List<EngineeringModelData> engineeringModelDataList;
	
	/**
	 * Getter for the list of engineering model data in the Data Element
	 * @return The current list of engineering model data in the Data Element
	 */
	public List<EngineeringModelData> getEngineeringModelDataList() {
		return this.engineeringModelDataList;
	}
	
	/**
	 * Setter for the list of engineering model data in the Data Element
	 * @return The new list of engineering model data in the Data Element
	 */
	public void setEngineeringModelDataList(List<EngineeringModelData> engineeringModelDataList) {
		this.engineeringModelDataList = engineeringModelDataList;
	}
	
	/* *********************************************************************** */
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
