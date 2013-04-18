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
 * Class representing a CSIRO Data Element
 * @author Guillaume Prevost
 * @since 25th Jan. 2013
 */
@Entity
@DiscriminatorValue(value = "Csiro")
public class DataElementCsiro extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of CSIRO data contained in this CSIRO data element
	 */
    @ManyToMany
    @JoinTable(name="data_element_csiro_data", joinColumns={@JoinColumn(name="data_element_id")}, inverseJoinColumns={@JoinColumn(name="csiro_data_id")})
    @LazyCollection(value=LazyCollectionOption.FALSE)
	private List<CsiroData> csiroDataList;
        
	/**
	 * Default constructor of DataElementCsiro
	 */
	public DataElementCsiro() {
		super();
	}
	
	/**
	 * Constructor of DataElementCsiro specifying the all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this data element belongs
	 * @param csiroDataList: the list of CSIRO data contained in this CSIRO data element
	 */
	public DataElementCsiro(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory, List<CsiroData> csiroDataList) {
		super(creationDate, name, included, position, displayType, userStory);
		this.csiroDataList = csiroDataList;
	}
	
	/**
	 * Getter for the list of CSIRO data in the Data Element
	 * @return The current list of CSIRO data in the Data Element
	 */
	public List<CsiroData> getCsiroDataList() {
		return this.csiroDataList;
	}
	
	/**
	 * Setter for the list of CSIRO data in the Data Element
	 * @param csiroDataList: the new list of CSIRO data in the Data Element
	 */
	public void setCsiroDataList(List<CsiroData> csiroDataList) {
		this.csiroDataList = csiroDataList;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}