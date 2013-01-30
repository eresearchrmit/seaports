package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing a climate variable
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "ClimateVariable")
public class ClimateVariable {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * Unique ID of the variable
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the variable
	 */
	@Column
	private String name;
	
	/**
	 * The short name of the variable
	 */
	@Column
	private String shortName;

	/**
	 * The description of the variable
	 */
	@Column
	private String description;
	
	/**
	 * The unit of measure of the variable
	 */
	@Column
	private String uom;
	
	/**
	 * Default constructor of CsiroVariable
	 */
	public ClimateVariable() {
	}
	
	/**
	 * Constructor of CsiroVariable
	 * @param name: the name of the variable
	 * @param uom: the unit of measure of the variable
	 */
	public ClimateVariable(String name, String shortName, String description, String uom) {
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.uom = uom;
	}
	
	/**
	 * Getter for the unique ID of the variable
	 * @return The unique ID of the variable
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Getter for the name of the variable
	 * @return the current name of the variable
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name of the variable
	 * @param name: the new name of the variable
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the short name of the variable
	 * @return the current short name of the variable
	 */
	public String getShortName() {
		return this.shortName;
	}
	
	/**
	 * Setter for the short name of the variable
	 * @param name: the new short name of the variable
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	/**
	 * Getter for the description of the variable
	 * @return the current description of the variable
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Setter for the description of the variable
	 * @param name: the new description of the variable
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for the unit of measure of the variable
	 * @return the unit of measure of the variable, as a String
	 */
	public String getUom() {
		return this.uom;
	}
	
	/**
	 * Setter for the unit of measure of the variable
	 * @param uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}
}
