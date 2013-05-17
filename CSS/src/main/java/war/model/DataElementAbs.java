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
 * Class representing an ABS Data Element
 * @author Guillaume Prevost
 * @since 10th Apr. 2013
 */
@Entity
@DiscriminatorValue(value = "Abs")
public class DataElementAbs extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of ABS data contained in this ABS data element
	 */
    @ManyToMany
    @JoinTable(name="data_element_abs_data", joinColumns={@JoinColumn(name="data_element_id")}, inverseJoinColumns={@JoinColumn(name="abs_data_id")})
    @LazyCollection(value=LazyCollectionOption.FALSE)
	private List<AbsData> absDataList;
        
	/**
	 * Default constructor of DataElementAbs
	 */
	public DataElementAbs() {
		super();
	}
	
	/**
	 * Constructor of DataElementAbs specifying the all the fields
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param displayType: the way the data element should be displayed
	 * @param userStory: the user story to which this data element belongs
	 * @param absDataList: the list of ABS data contained in this past data element
	 */
	public DataElementAbs(Date creationDate, String name, boolean included, int position, DisplayType displayType, UserStory userStory, List<AbsData> absDataList) {
		super(creationDate, name, included, position, displayType, userStory);
		setAbsDataList(absDataList);
	}
	
	/**
	 * Getter for the list of ABS data in the Data Element
	 * @return The current list of ABS data in the Data Element
	 */
	public List<AbsData> getAbsDataList() {
		return this.absDataList;
	}
	
	/**
	 * Setter for the list of ABS data in the Data Element
	 * @param absDataList: the new list of ABS data in the Data Element
	 */
	public void setAbsDataList(List<AbsData> absDataList) {
		this.absDataList = absDataList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}