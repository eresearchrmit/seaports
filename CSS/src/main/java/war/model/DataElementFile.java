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
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;

/**
 * Class representing an uploaded file containing some data
 * @author Guillaume Prevost
 * @since 31th Jan. 2013
 */
@Entity
@DiscriminatorValue(value = "File")
public class DataElementFile extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The type of the file. 
	 */
	@Column
	private String filetype;

    /**
     * The binary content of the file
     */
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;
    
    /**
     * The string conversion of the file content
     */
    @Transient
    private String stringContent; 

	/**
	 * Default constructor of file
	 */
	public DataElementFile() {
	}

	/**
	 * Constructor of File specifying the name, login and password
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this data element belongs
	 * @param filetype: the type of the file. 
	 * @param content: the binary content of the file
	 */
	public DataElementFile(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory, String filetype, byte[] content) {
		super(creationDate, name, included, position, displayType, userStory);
		this.filetype = filetype;
		this.content = content;
		
		this.generateStringContent();
	}
    
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
		if (this.content == null)
			generateBinaryContent();
		return content;
	}

	/**
	 * Setter for the binary content of the file. 
	 * It also sets the property stringContent by converting the binary content into a String.
	 * @param content: the new binary content of the file
	 */
	public void setContent(byte[] content) {
		this.content = content;
		generateStringContent();
	}
	
	/**
	 * Getter for the string conversion of the file
	 * @return The current string conversion of the file
	 */
	public String getStringContent() {
		if (this.stringContent == null)
			generateStringContent();
		return this.stringContent;
	}
	
	/**
	 * Setter for the string content of the file. 
	 * It also sets the property binaryContent by converting the String content into binary.
	 * @param content: the new string content of the file
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
			if (this.filetype != null && (this.filetype.equals("jpg") || this.filetype.equals("jpeg"))) {
				this.stringContent = Base64.encodeBase64String(this.content);
			}
			else if (this.filetype != null && this.filetype.equals("csv")) {
				String result = "<table class=\"data display datatable\">";
				
				String fileContent = new String(this.content);
				String[] rows = fileContent.split("\n");
				
				int i = 0;
				for (String row : rows) {
					result += "<tr class=\"" + ((i % 2 == 0) ? "even" : "odd") + "\">";
					String[] cells = row.split(",");
					for (String cell : cells) {
						result += "<td class=\"top\">" + cell + "</td>";
					}
					result += "</tr>";
					i++;
				}
				result += "</table>";
				this.stringContent = result;
			}
			else
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
			if (this.filetype != null && (this.filetype.equals("jpg") || this.filetype.equals("jpeg")))
				this.content = Base64.decodeBase64(this.stringContent);
			else
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