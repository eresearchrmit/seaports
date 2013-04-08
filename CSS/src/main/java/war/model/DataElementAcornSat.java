package war.model;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Class representing an Acorn-Sat Data Element
 * @author Guillaume Prevost
 * @since 8th Apr. 2013
 */
@Entity
@DiscriminatorValue(value = "AcornSat")
public class DataElementAcornSat extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of Acorn-Sat data contained in this Acorn-Sat data element
	 */
    @ManyToMany
    @JoinTable(name="data_element_acorn_sat_data", joinColumns={@JoinColumn(name="data_element_id")}, inverseJoinColumns={@JoinColumn(name="acorn_sat_data_id")})
    @Cascade(value = CascadeType.DELETE)
    @LazyCollection(value=LazyCollectionOption.FALSE)
	private List<AcornSatData> acornSatDataList;
        
	/**
	 * Default constructor of DataElementAcornSat
	 */
	public DataElementAcornSat() {
		super();
	}
	
	/**
	 * Constructor of DataElementAcornSat specifying the all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 * @param acornSatDataList: the list of past data contained in this past data element
	 */
	public DataElementAcornSat(Date creationDate, String name, boolean included, int position, UserStory userStory, List<AcornSatData> acornSatDataList) {
		super(creationDate, name, included, position, userStory);
		setAcornSatDataList(acornSatDataList);
	}
	
	/**
	 * Getter for the list of Acorn-Sat data in the Data Element
	 * @return The current list of Acorn-Sat data in the Data Element
	 */
	public List<AcornSatData> getAcornSatDataList() {
		return this.acornSatDataList;
	}
	
	/**
	 * Setter for the list of Acorn-Sat data in the Data Element
	 * @param acornSatDataList: the new list of Acorn-Sat data in the Data Element
	 */
	public void setAcornSatDataList(List<AcornSatData> acornSatDataList) {
		this.acornSatDataList = acornSatDataList;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}