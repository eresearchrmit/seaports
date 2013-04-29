package war.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Class representing an NRM region / NRM cluster of NRM regions
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 * @see NRM Regions: {@link http://www.nrm.gov.au/about/nrm/regions/index.html}
 */
@Entity
@Table(name = "Region")
public class Region {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the Region
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the region
	 */
	private String name;
	
	/**
	 * The coordinates of the region, in KML format
	 */
	private String coordinates;
	
	/**
	 * A list of the seaports located in the region
	 */
	@OneToMany(targetEntity=Seaport.class, mappedBy="region",cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Seaport> ports;
	
	/**
	 * Default constructor of region
	 */
	public Region() {
	}
	
	/**
	 * Constructor of Region specifying the name and the coordinates
	 * @param name: the name of the region
	 * @param coordinates: the coordinates of the region
	 */
	public Region(String name, String coordinates) {
		setName(name);
		setCoordinates(coordinates);
	}
	
	/**
	 * Getter for the unique ID of the region
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for the name of the region
	 * @return the current name of the region
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter for the name of the region
	 * @param name: the new name of the region
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the coordinates of the region
	 * @return the current coordinates of the region
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Setter for the coordinates of the region
	 * @param name: the new coordinates of the region
	 */
	public String getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Getter for the seaports located in this region
	 * @return a list of the Ports located in the region
	 */
	public List<Seaport> getPorts() {
		return ports;
	}

	/**
	 * Setter for the seaports located in this region
	 * @param ports: the new list of ports in this region
	 */
	public void setPorts(List<Seaport> ports) {
		this.ports = ports;
	}
}