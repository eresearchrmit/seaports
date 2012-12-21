package war.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class representing a climate variable measured by CSIRO
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "CSIROVariable")
public class CsiroVariable {
	
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
	private String name;
	
	/**
	 * The unit of measure of the variable
	 */
	private String uom;
	
	public CsiroVariable() {
	}
	
	public CsiroVariable(String name, String uom) {
		this.name = name;
		this.uom = uom;
	}
	
	/**
	 * Getter for the unique ID of the variable
	 * @return The unique ID of the variable
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the name of the variable
	 * @param name: the new name of the variable
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter the name of the variable
	 * @return the current name of the variable
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the unit of measure of the variable
	 * @param uom
	 */
	public void setUom(String uom) {
		this.uom = uom;
	}

	/**
	 * Getter for the unit of measure of the variable
	 * @return the unit of measure of the variable, as a String
	 */
	public String getUom() {
		return uom;
	}
}
