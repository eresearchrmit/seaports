/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Type;

import war.model.UserStory;

/**
 * Class representing a text item of a user story
 * @author Guillaume Prevost
 * @since 6th Feb. 2013
 */
@Entity
@DiscriminatorValue(value = "Text")
public class DataElementText extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;

	/**
	 * The content of the text item.
	 */
	@Column
	@Type(type="text")
	private String text;
	
	/**
	 * Default constructor of text item
	 */
	public DataElementText() {
		super();
	}
	
	/**
	 * Constructor of User specifying the name, login and password 
	 * @param creationDate: the date when the text item was created
	 * @param name: the name of the text item
	 * @param included: whether the text item is included or not in the publication of its parent User Story.
	 * @param position: the position of the text item in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this text item belongs
	 * @param content: the text of the text item
	 */
	public DataElementText(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory, String text) {
		super(creationDate, name, included, position, displayType, userStory);
		this.text = text;
	}
	
	/**
	 * Getter for the text of the text item
	 * @return the current text of the text item
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Getter for the text of the text item
	 * @return the current text of the text item
	 */
	public String getEscapedText() {
		return text.replaceAll("(\r\n|\r|\n|\n\r)", "").replace("'", "\\\'");
	}

	/**
	 * Setter for the text of the text item
	 * @param the new text of the text item
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}