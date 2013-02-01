package war.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Class representing a CSIRO Data Element
 * @author Guillaume Prevost
 * @since 25th Jan. 2013
 */
@Entity
@Table(name = "DataElementEngineeringModel")
@DiscriminatorValue(value = "EngineeringModel")
public class DataElementEngineeringModel extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of engineering model data contained in this data element
	 */
	/*@OneToMany(targetEntity=EngineeringModelData.class, cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)*/
	@ElementCollection(targetClass=EngineeringModelData.class, fetch=FetchType.LAZY)
	private List<EngineeringModelData> engineeringModelDataList;
	
	/**
	 * Default constructor of DataElementCsiro
	 */
	public DataElementEngineeringModel() {
		super();
	}
	
	/**
	 * Constructor of File specifying the name, login and password
	 * @param creationDate: the date when the data element was created
	 * @param name: the name of the data element
	 * @param position: the position of the data element in the user story it belongs to
	 * @param userStory: the user story to which this data element belongs
	 * @param csiroDataList: the list of CSIRO data contained in this CSIRO data element
	 */
	public DataElementEngineeringModel(Date creationDate, String name, int position, UserStory userStory, List<EngineeringModelData> engineeringModelDataList) {
		super(creationDate, name, position, userStory);
		this.engineeringModelDataList = engineeringModelDataList;
	}
	
	/**
	 * Getter for the list of engineering model data in the Data Element
	 * @return The current list of engineering model data in the Data Element
	 */
	public List<EngineeringModelData> getEngineeringModelDataList() {
		return this.engineeringModelDataList;
	}
	
	/**
	 * Setter for the list of engineering model data in the Data Element
	 * @return The new list of engineering model data in the Data Element
	 */
	public void setEngineeringModelDataList(List<EngineeringModelData> engineeringModelDataList) {
		this.engineeringModelDataList = engineeringModelDataList;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
