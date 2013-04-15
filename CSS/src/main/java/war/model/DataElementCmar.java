package war.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Class representing a CMAR Data Element
 * @author Guillaume Prevost
 * @since 12th Mar. 2013
 */
@Entity
@DiscriminatorValue(value = "Cmar")
public class DataElementCmar extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of CMAR data contained in this CMAR data element
	 */
    @ManyToMany
    @JoinTable(name="data_element_cmar_data", joinColumns={@JoinColumn(name="data_element_id")}, inverseJoinColumns={@JoinColumn(name="cmar_data_id")})
    @LazyCollection(value=LazyCollectionOption.FALSE)
	private List<CmarData> cmarDataList;
    
    /**
     * Whether the pictures related to the raw data should be displayed as part of this data element 
     */
    @Column
    private Boolean picturesIncluded;
    
	/**
	 * Default constructor of DataElementCmar
	 */
	public DataElementCmar() {
		super();
	}
	
	/**
	 * Constructor of DataElementCmar specifying all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 * @param cmarDataList: the list of CMAR data contained in this CMAR data element
	 */
	public DataElementCmar(Date creationDate, String name, boolean included, int position, UserStory userStory, List<CmarData> cmarDataList, Boolean picturesIncluded) {
		super(creationDate, name, included, position, userStory);
		this.cmarDataList = cmarDataList;
		this.picturesIncluded = picturesIncluded;
	}
	
	/**
	 * Getter for the list of CMAR data in the Data Element
	 * @return The current list of CMAR data in the Data Element
	 */
	public List<CmarData> getCmarDataList() {
		return this.cmarDataList;
	}
	
	/**
	 * Setter for the list of CMAR data in the Data Element
	 * @param csiroDataList: the new list of CMAR data in the Data Element
	 */
	public void setCmarDataList(List<CmarData> cmarDataList) {
		this.cmarDataList = cmarDataList;
	}
	
	/**
	 * Getter for whether illustration pictures should be displayed or not for this data element
	 * @return The current display status of illustration pictures in the data element
	 */
	public Boolean getPicturesIncluded() {
		return this.picturesIncluded;
	}
	
	/**
	 * Setter for whether illustration pictures should be displayed or not for this data element
	 * @param picturesIncluded: the new display status of illustration pictures in the data element
	 */
	public void setPicturesIncluded(Boolean picturesIncluded) {
		this.picturesIncluded = picturesIncluded;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}