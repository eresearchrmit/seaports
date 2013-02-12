package war.ui;

import java.util.ArrayList;
import java.util.List;


import war.model.DataElement;
import war.model.DataElementCsiro;
import war.model.DataElementEngineeringModel;
import war.model.DataElementFile;
import war.model.DataElementText;
import war.model.User;
import war.model.Region;
import war.model.UserStory;

/**
 * Class representing
 * @author Guillaume Prevost
 * @since 7th Jan. 2013
 */
public class UserStoryUI {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the user story
	 */
	private int id;
	
	/**
	 * The name of the user story
	 */
	private String name;
	
	/**
	 * The mode of the user story. It can be 'active', 'passive' or 'published'.
	 * Active: there is only one active user story per user, it is considered as its 'Workboard'. The user can add and remove data elements form the user story.
	 * Passive: the user story is being edited by the user
	 * Published: nothing can be modified by the user as the story is published. 
	 */
	private String mode;
	
	/**
	 * The level of privacy for this suer story. It can be private or public.
	 * Private: only the owner can view the user story
	 * Public: everyone can view the user story
	 */
	private String access;
	
	/**
	 * The user who created the user story
	 */
	private User owner;
	
	/**
	 * The region to which the user story is related
	 */
	private Region region;
	
	/**
	 * The list of data elements contained in the user story
	 */
	private List<DataElementUI> dataElements = new ArrayList<DataElementUI>();
	
	/**
	 * Default constructor of User Story
	 */
	public UserStoryUI() {
	}
	
	/**
	 * Constructor of User Story specifying all its fields
	 * @param name: the name of the user story
	 * @param mode: the mode of the user story
	 * @param access: the level of privacy for this suer story
	 * @param owner: the user who created the user story
	 * @param region: the region to which the user stories is related
	 * @param dataElements: the list of data elements contained in the user story
	 */
	public UserStoryUI(String name, String mode, String access, User owner, Region region, List<DataElement> dataElements) {
		this.name = name;
		this.mode = mode;
		this.access = access;
		this.owner = owner;
		this.region = region;
		setDataElements(dataElements);
	}
	
	public UserStoryUI(UserStory userStory) {
		this.name = userStory.getName();
		this.mode = userStory.getMode();
		this.access = userStory.getAccess();
		this.owner = userStory.getOwner();
		this.region = userStory.getRegion();

		setDataElements(userStory.getDataElements());
	}

	/**
	 * Getter for the ID of the user story
	 * @return The unique ID of the user story
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter for the name of the user story
	 * @return The current name of the user story
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name of the user story
	 * @return The new name of the user story
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the owner of the user story
	 * @return The current owner of the user story
	 */
	public User getOwner() {
		return owner;
	}
	
	/**
	 * Setter for the owner of the user story
	 * @return The new owner of the user story
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	/**
	 * Getter for the mode of the user story
	 * @return The current mode of the user story
	 */
	public String getMode() {
		return this.mode;
	}
	
	/**
	 * Setter for the mode of the user story
	 * @return The new mode of the user story
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	/**
	 * Getter for the privacy level of the user story
	 * @return The current privacy level of the user story
	 */
	public String getAccess() {
		return this.access;
	}
	
	/**
	 * Setter for the privacy level of the user story
	 * @return The new privacy level of the user story
	 */
	public void setAccess(String access) {
		this.access = access;
	}

	/**
	 * Getter for the data elements list of the user story
	 * @return The current data elements list of the user story
	 */
	public List<DataElementUI> getDataElementsUI() {
		return this.dataElements;
	}
	
	/**
	 * Setter for the data elements list of the user story
	 * @return The new data elements list of the user story
	 */
	public void setDataElementsUI(List<DataElementUI> dataElements) {
		this.dataElements = dataElements;
	}
	
	/**
	 * Getter for the data elements list of the user story
	 * @return The current data elements list of the user story
	 */
	public List<DataElement> getDataElements() {
		List<DataElement> dataElementsList = new ArrayList<DataElement>();
		
		for (DataElementUI deUI : this.dataElements) {
			if (deUI.getType().equals("file")) {
				DataElementFile de = new DataElementFile();
				dataElementsList.add(de);
			}
			else if (deUI.getType().equals("text")) {
				DataElementText de = new DataElementText(deUI.getCreationDate(), deUI.getName(),  deUI.getIncluded(), deUI.getPosition(), deUI.getUserStory(), deUI.getText());
				dataElementsList.add(de);
			}
			else if (deUI.getType().equals("csiro")) {
				DataElementCsiro de = new DataElementCsiro(deUI.getCreationDate(), deUI.getName(),  deUI.getIncluded(), deUI.getPosition(), deUI.getUserStory(), deUI.getCsiroDataList());
				dataElementsList.add(de);
			}
			else if (deUI.getType().equals("engineeringmodel")) {
				DataElementEngineeringModel de = new DataElementEngineeringModel(deUI.getCreationDate(), deUI.getName(),  deUI.getIncluded(), deUI.getPosition(), deUI.getUserStory(), deUI.getEngineeringModelDataList());
				dataElementsList.add(de);
			}
			else {
				DataElement de = new DataElement(deUI.getCreationDate(), deUI.getName(), deUI.getIncluded(), deUI.getPosition(), deUI.getUserStory());
				dataElementsList.add(de);
			}
		}
		return dataElementsList;
	}
	
	/**
	 * Setter for the data elements list of the user story
	 * @return The new data elements list of the user story
	 */
	public void setDataElements(List<DataElement> dataElements) {
		for (DataElement de : dataElements) {
			if (de instanceof DataElementFile) {
				DataElementUI deUI = new DataElementUI((DataElementFile)de);
				this.dataElements.add(deUI);
			}
			else if (de instanceof DataElementText) {
				DataElementUI deUI = new DataElementUI((DataElementText)de);
				this.dataElements.add(deUI);
			}
			else if (de instanceof DataElementCsiro) {
				DataElementUI deUI = new DataElementUI((DataElementCsiro)de);
				this.dataElements.add(deUI);
			}
			else if (de instanceof DataElementEngineeringModel) {
				DataElementUI deUI = new DataElementUI((DataElementEngineeringModel)de);
				this.dataElements.add(deUI);
			}
		}
	}
	
	/**
	 * Getter for the region to which the user story is related
	 * @return The region to which the user story is related
	 */
	public Region getRegion() {
		return region;
	}
	
	/**
	 * Setter for the region to which the user story is related
	 * @param region: the new region to which the user story is related
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
