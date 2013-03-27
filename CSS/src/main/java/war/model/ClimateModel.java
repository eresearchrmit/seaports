package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing a climate model.
 * It is not holding the actual model but its name and description.
 * @author Guillaume Prevost
 * @since 24th Jan. 2013
 */
@Entity
@Table(name = "climatemodel")
public class ClimateModel {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the Climate Model
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The code of the climate model
	 */
	@Column
	private String code;

	
	/**
	 * The name of the climate model
	 */
	@Column
	private String name;

	/**
	 * The description of the climate model
	 */
	@Column
	private String description;
	
	/**
	 * Default constructor of Climate Model
	 */
	public ClimateModel() {
		
	}

	/**
	 * Constructor of Climate Model specifying the code, name and description
	 */
	public ClimateModel(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Getter for the unique ID of the climate model
	 * @return the unique ID of the climate model
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id: the new unique ID of the climate model
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for the code for the climate model
	 * @return the current code for the climate model
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Setter for the code for the climate model
	 * @param name: the new code of the climate model
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * Getter for the name for the climate model
	 * @return the current name for the climate model
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name for the climate model
	 * @param name: the new name of the climate model
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the description for the climate model
	 * @return the current description for the climate model
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Setter for the description for the climate model
	 * @param name: the new description of the climate model
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
