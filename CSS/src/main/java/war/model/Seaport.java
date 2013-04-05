package war.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing an Australian seaport. It is related to the NRM region where it is located.
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "Seaport")
public class Seaport {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The code of the seaport
	 */
	@Id
	private String code;
	
	/**
	 * The name of the seaport
	 */
	private String name;
	
	/**
	 * The region where the seaport is located
	 */
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	
	/**
	 * Default constructor of Seaport
	 */
	public Seaport() {
	}
	
	/**
	 * Constructor of Seaport specifying the name
	 * @param code: the unique code of the seaport
	 * @param name: the name of the seaport
	 * @param region: the region where the seaport is located
	 */
	public Seaport(String code, String name, Region region) {
		setCode(code);
		setName(name);
		setRegion(region);
	}
	
	/**
	 * Getter for the code of the seaport
	 * @return the current code of the seaport
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Setter for the code for the seaport
	 * @param code: the new code of the seaport
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Getter for the name for the seaport
	 * @return the name for the seaport
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name for the seaport
	 * @param name: the new name of the seaport
	 */
	public void setName(String name) {
		this.name = name;
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