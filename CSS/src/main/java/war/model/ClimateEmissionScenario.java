package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing a possible scenario of CO2 emission in the atmosphere.
 * It is not holding the actual CO2 emission level, but just the name and description.
 * @author Guillaume Prevost
 * @since 24th Jan. 2013
 */
@Entity
@Table(name = "ClimateEmissionScenario")
public class ClimateEmissionScenario {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the emission scenario
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the emission scenario
	 */
	@Column
	private String name;

	/**
	 * The description of the emission scenario
	 */
	@Column
	private String description;
	
	/**
	 * Default constructor of Emission Scenario
	 */
	public ClimateEmissionScenario() {
		
	}

	/**
	 * Constructor of Emission Scenario specifying the name and description
	 */
	public ClimateEmissionScenario(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Getter for the unique ID of the emission scenario
	 * @return the unique ID of the emission scenario
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * 
	 * @param id: the new unique ID of the emission scenario
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for the name for the emission scenario
	 * @return the name for the emission scenario
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name for the emission scenario
	 * @param name: the new name of the emission scenario
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for the description for the emission scenario
	 * @return the description for the emission scenario
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Setter for the description for the emission scenario
	 * @param name: the new description of the emission scenario
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
