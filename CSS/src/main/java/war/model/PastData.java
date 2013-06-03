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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing the past observed data from Bureau of Meteorology and CSIRO
 * @author Guillaume Prevost
 * @since 4th Apr. 2013
 */
@Entity
@Table(name = "PastData")
public class PastData
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the past data
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
    
	/**
	 * The title of the observed data
	 */
	private String title;
	
	/**
	 * The date when this data has been observed
	 */
	private Date creationDate;
	
	/**
	 * The start date of the period this data relates to
	 */
	private Date periodStart;
	
	/**
	 * The end date of the period this data relates to
	 */
	private Date periodEnd;
		
	/**
	 * The URL of the source of the picture
	 */
	@Column
	private String sourceUrl;
	
	/**
	 * The picture representing the data
	 */
	@Column
	private String picture;
	
	/**
	 * Default constructor of CsiroData
	 */
	public PastData() {
		setCreationDate(new Date());
	}
	
	/**
	 * Constructor of CsiroData
	 * @param title: the title of the observed data
	 * @param creationDate: the date when this data has been created
	 * @param variable: the variable that this data represents
	 * @param sourceURL: the URL of the source of the data
	 * @param picture: the picture representing the value
	 */
	public PastData(String title, Date creationDate, Date periodStart, Date periodEnd, String sourceUrl, String picture) {
		setTitle(title);
		setCreationDate(creationDate);
		setPeriodStart(periodStart);
		setPeriodEnd(periodEnd);
		setSourceUrl(sourceUrl);
		setPicture(picture);
	}

	/**
	 * Getter for the unique ID of the CMAR Data
	 * @return: the unique ID of the CMAR Data
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for the title of the observed data
	 * @return: the current title of the observed data
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Setter for the title of the observed data
	 * @param title: the new title of the observed data
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter for the creation date of the Data
	 * @return: the creation date of the data
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Setter for the creation date of the Data
	 * @param creationDate: the new creation date of the Data
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Getter for the start date of the period this data relates to
	 * @return: The start date of the period this data relates to
	 */
	public Date getPeriodStart() {
		return this.periodStart;
	}
	
	/**
	 * Setter for the start date of the period this data relates to
	 * @param periodStart: the new start date of the period this data relates to
	 */
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	
	/**
	 * Getter for the end date of the period this data relates to
	 * @return: The end date of the period this data relates to
	 */
	public Date getPeriodEnd() {
		return this.periodEnd;
	}
	
	/**
	 * Setter for the end date of the period this data relates to
	 * @param periodEnd: the new end date of the period this data relates to
	 */
	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	/**
	 * Getter for the URL of the source of the picture
	 * @return: the current URL of the source of the picture
	 */
	public String getSourceUrl() {
		return this.sourceUrl;
	}
	
	/**
	 * Setter for the URL of the source of the picture
	 * @param value: the new URL of the source of the picture
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	
	/**
	 * Getter for the picture representing the value
	 * @return: the current picture representing the value
	 */
	public String getPicture() {
		return picture;
	}
	
	/**
	 * Setter for the picture representing the value
	 * @param value: the new picture representing the value
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
}