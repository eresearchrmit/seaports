/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.model;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Class representing a Past Data Element
 * @author Guillaume Prevost
 * @since 8th Apr. 2013
 */
@Entity
@DiscriminatorValue(value = "Past")
public class DataElementPast extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of past data contained in this past data element
	 */
    @ManyToMany
    @JoinTable(name="data_element_past_data", joinColumns={@JoinColumn(name="data_element_id")}, inverseJoinColumns={@JoinColumn(name="past_data_id")})
    @LazyCollection(value=LazyCollectionOption.FALSE)
	private List<PastData> pastDataList;
        
	/**
	 * Default constructor of DataElementPast
	 */
	public DataElementPast() {
		super();
	}
	
	/**
	 * Constructor of DataElementPast specifying the all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this data element belongs
	 * @param pastDataList: the list of past data contained in this past data element
	 */
	public DataElementPast(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory, List<PastData> pastDataList) {
		super(creationDate, name, included, position, displayType, userStory);
		setPastDataList(pastDataList);
	}
	
	/**
	 * Getter for the list of past data in the Data Element
	 * @return The current list of past data in the Data Element
	 */
	public List<PastData> getPastDataList() {
		return this.pastDataList;
	}
	
	/**
	 * Setter for the list of past data in the Data Element
	 * @param pastDataList: the new list of past data in the Data Element
	 */
	public void setPastDataList(List<PastData> pastDataList) {
		this.pastDataList = pastDataList;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}