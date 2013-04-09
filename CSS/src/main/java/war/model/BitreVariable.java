package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class representing a climate variable
 * @author Guillaume Prevost
 * @since 9th Apr. 2013
 */
@Entity
@Table(name = "BitreVariable")
public class BitreVariable extends Variable {
	
	/**
	 * The category of the BITRE variable
	 */
	@Column
	private BitreVariableCategory category;
	
	/**
	 * Default constructor of EngineeringModelVariable
	 */
	public BitreVariable() {
	}
	
	/**
	 * Constructor of EngineeringModelVariable
	 * @param name: the name of the variable
	 * @param shortName: the short name for the variable
	 * @param description: the description of the variable
	 * @param category: the category of the variable
	 * @param uom: the unit of measure of the variable
	 */
	public BitreVariable(String name, String shortName, String description, BitreVariableCategory category, String uom) {
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.uom = uom;
		this.uomVariation = "%";
		this.category = category;
	}
	
	/**
	 * Constructor of EngineeringModelVariable
	 * @param name: the name of the variable
	 * @param shortName: the short name for the variable
	 * @param description: the description of the variable
	 * @param category: the category of the variable
	 * @param uom: the unit of measure of the variable
	 * @param uomVariation: the unit of measure of the variation of the variable
	 */
	public BitreVariable(String name, String shortName, String description, BitreVariableCategory category, String uom, String uomVariation) {
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.uom = uom;
		this.category = category;
		this.uomVariation = uomVariation;
	}
	
	/**
	 * Getter for the category of the variable
	 * @return the current category of the variable
	 */
	public BitreVariableCategory getCategory() {
		return this.category;
	}
	
	/**
	 * Setter for the category of the variable
	 * @param category: the new category of the variable
	 */
	public void setCategory(BitreVariableCategory category) {
		this.category = category;
	}
}