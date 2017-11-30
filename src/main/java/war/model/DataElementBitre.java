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
 * Class representing an BITRE Data Element
 * @author Guillaume Prevost
 * @since 10th Apr. 2013
 */
@Entity
@DiscriminatorValue(value = "Bitre")
public class DataElementBitre extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of ABS data contained in this BITRE data element
	 */
    @ManyToMany
    @JoinTable(name="data_element_bitre_data", joinColumns={@JoinColumn(name="data_element_id")}, inverseJoinColumns={@JoinColumn(name="bitre_data_id")})
    @LazyCollection(value=LazyCollectionOption.FALSE)
	private List<BitreData> bitreDataList;
        
	/**
	 * Default constructor of DataElementBitre
	 */
	public DataElementBitre() {
		super();
	}
	
	/**
	 * Constructor of DataElementBitre specifying the all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this data element belongs
	 * @param bitreDataList: the list of BITRE data contained in this past data element
	 */
	public DataElementBitre(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory, List<BitreData> bitreDataList) {
		super(creationDate, name, included, position, displayType, userStory);
		setBitreDataList(bitreDataList);
	}
	
	/**
	 * Getter for the list of BITRE data in the Data Element
	 * @return The current list of BITRE data in the Data Element
	 */
	public List<BitreData> getBitreDataList() {
		return this.bitreDataList;
	}
	
	/**
	 * Setter for the list of BITRE data in the Data Element
	 * @param bitreDataList: the new list of BITRE data in the Data Element
	 */
	public void setBitreDataList(List<BitreData> bitreDataList) {
		this.bitreDataList = bitreDataList;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}