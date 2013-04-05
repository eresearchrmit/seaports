package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing an ACORN-Sat weather station
 * @author Guillaume Prevost
 * @since 4th Apr. 2013
 */
@Entity
@Table(name = "AcornSatStation")
public class AcornSatStation {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the station
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The number of the station
	 */
	private Double number;
	
	/**
	 * The name of the station
	 */
	private String name;
	
	/**
	 * The latitude of the weather station
	 */
	private Double latitude;
	
	/**
	 * The longitude of the weather station
	 */
	private Double longitude;
	
	/**
	 * The region where the station is located
	 */
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	
	/**
	 * Default constructor of AcornSatStation
	 */
	public AcornSatStation() {
	}
	
	/**
	 * Constructor of AcornSatStation specifying the name
	 * @param number: the number of the weather station
	 * @param name: the name of the weather station
	 * @param latitude: the latitude of the weather station
	 * @param longitude: the longitude of the weather station
	 * @param region: the region where the seaport is located
	 */
	public AcornSatStation(Double number, String name, Double latitude, Double longitude, Region region) {
		setNumber(number);
		setName(name);
		setLatitude(latitude);
		setLongitude(longitude);
		setRegion(region);
	}
	
	/**
	 * Getter for the unique ID of the parameters combination
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter for the number of the weather station
	 * @return the number of the weather station
	 */
	public Double getNumber() {
		return this.number;
	}
	
	/**
	 * Setter for the number of the weather station
	 * @param number: the new number of the weather station
	 */
	public void setNumber(Double number) {
		this.number = number;
	}
	
	/**
	 * Getter for the name for the weather station
	 * @return the name for the weather station
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name for the weather station
	 * @param name: the new name of the weather station
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the latitude of the weather station
	 * @return the current latitude of the weather station
	 */
	public Double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Setter for the latitude of the weather station
	 * @param latitude: the new latitude of the weather station
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Getter for the longitude of the weather station
	 * @return the current longitude of the weather station
	 */
	public Double getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Setter for the longitude of the weather station
	 * @param latitude: the new longitude of the weather station
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Getter for the seaport's region
	 * @return the region where the seaport is located
	 */
	public Region getRegion() {
		return this.region;
	}
	
	/**
	 * Setter for the seaport's region
	 * @param region: the new region where the seaport is located
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
}