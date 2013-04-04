package war.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Class representing a variable measured by an Acorn-Sat weather station 
 * @author Guillaume Prevost
 * @since 4th Apr. 2013
 */
@Entity
@Table(name = "AcornSatVariable")
public class AcornSatVariable extends Variable {
	/**
	 * Default constructor of AcornSatVariable
	 */
	public AcornSatVariable() {
	}
	
	/**
	 * Constructor of AcornSatVariable
	 * @param name: the name of the variable
	 * @param shortName: the short name for the variable
	 * @param description: the description of the variable
	 * @param uom: the unit of measure of the variable
	 */
	public AcornSatVariable(String name, String shortName, String description, String uom) {
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.uom = uom;
		this.uomVariation = "%";
	}
	
	/**
	 * Constructor of AcornSatVariable
	 * @param name: the name of the variable
	 * @param shortName: the short name for the variable
	 * @param description: the description of the variable
	 * @param uom: the unit of measure of the variable
	 * @param uomVariation: the unit of measure of the variation of the variable
	 */
	public AcornSatVariable(String name, String shortName, String description, String uom, String uomVariation) {
		this.name = name;
		this.shortName = shortName;
		this.description = description;
		this.uom = uom;
		this.uomVariation = uomVariation;
	}
}