package war.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Class representing a CSIRO Data Element
 * @author Guillaume Prevost
 * @since 25th Jan. 2013
 */
@Entity
@DiscriminatorValue(value = "EngineeringModel")
public class DataElementEngineeringModel extends DataElement {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The list of engineering model data contained in this data element
	 */
    @ManyToMany
    @JoinTable(name="DataElement_EngineeringModelData", joinColumns={@JoinColumn(name="DataElement_Id")}, inverseJoinColumns={@JoinColumn(name="EngineeringModelData_Id")})
	@Cascade(value = CascadeType.DELETE)
	@LazyCollection(value=LazyCollectionOption.FALSE)
	private List<EngineeringModelData> engineeringModelDataList;
	
    @Transient
    private Map<String, List<EngineeringModelData>> distinctEngineeringModelDataMap;
    
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
	public DataElementEngineeringModel(Date creationDate, String name, boolean included, int position, UserStory userStory, List<EngineeringModelData> engineeringModelDataList) {
		super(creationDate, name, included, position, userStory);
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
	
	public void generateDistinctDataList() {
		distinctEngineeringModelDataMap = new HashMap<String, List<EngineeringModelData>>();
		
		for (EngineeringModelData data : engineeringModelDataList) {
			String key = data.getParameters().getEmissionScenario().getName() + " " + data.getParameters().getModel().getName();
			if (distinctEngineeringModelDataMap.containsKey(key)) {
				distinctEngineeringModelDataMap.get(key).add(data);
			}
			else {
				distinctEngineeringModelDataMap.put(key, new ArrayList<EngineeringModelData>());
				distinctEngineeringModelDataMap.get(key).add(data);
			}
		}
	}
	
	/**
	 * Getter for the map of engineering model data grouped by climate models & emission scenarios
	 * This is used to sort out the data and be able to plot graphs from it
	 * @return the map of engineering model data grouped by climate models & emission scenarios
	 */
	public Map<String, List<EngineeringModelData>> getDistinctEngineeringModelDataMap() {
		return this.distinctEngineeringModelDataMap;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
