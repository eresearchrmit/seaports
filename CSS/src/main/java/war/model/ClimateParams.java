package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing a combination of Climate Parameters. 
 * Includes a year, a CO2 emissions scenario, a climate model, a region, and the name of the climate model in that region.
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "ClimateParams")
public class ClimateParams {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the combination of parameters
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The region to which the data is related
	 */
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	
	/**
	 * The climate model used to compute the data
	 */
	@ManyToOne
	@JoinColumn(name="climate_model_id")
	private ClimateModel model;
	
	/**
	 * The name of the climate model
	 */
	@Column
	private String modelName;
	
	/**
	 * The CO2 emissions scenario
	 */
	@ManyToOne
	@JoinColumn(name="climate_emission_scenario_id")
	private ClimateEmissionScenario emissionScenario;
	
	/**
	 * Default Constructor of ClimateParams
	 */
	public ClimateParams() {
	}
	
	/**
	 * Constructor of ClimateParams
	 * @param region: the region to which the data is related
	 * @param model: the climate model used to compute the data
	 * @param modelName: the name of the climate model
	 * @param emissionScenario: the name of the emission scenario
	 * @param year: the year for which the data are computed
	 */
	public ClimateParams(Region region, ClimateModel model, String modelName, ClimateEmissionScenario emissionScenario) {
		this.region = region;
		this.model = model;
		this.modelName = modelName;
		this.emissionScenario = emissionScenario;
	}
	
	/**
	 * Getter for the unique ID of the parameters combination
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Setter for the unique ID of the parameters combination
	 * @param id: the new unique ID of the parameters combination
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Getter for the data's region
	 * @return the region to which this data is related
	 */
	public Region getRegion() {
		return this.region;
	}
	
	/**
	 * Setter for the data's region
	 * @param region: the new region to which this data is related
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
	
	/**
	 * Getter for the climate model used to compute the data
	 * @return the climate model used to compute the data
	 */
	public ClimateModel getModel() {
		return this.model;
	}
	
	/**
	 * Setter for the climate model used to compute the data
	 * @param modelName: the new climate model used to compute the data
	 */
	public void setModel(ClimateModel model) {
		this.model = model;
	}
	
	/**
	 * Getter for the name of the climate model within the region
	 * @return the name of the climate model within the region
	 */
	public String getModelName() {
		return this.modelName;
	}

	/**
	 * Setter for the name of the climate model within the region
	 * @param modelName: the new name of the climate model within the region 
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
	/**
	 * Getter for the emission scenario
	 * @return the emission scenario
	 */
	public ClimateEmissionScenario getEmissionScenario() {
		return this.emissionScenario;
	}
	

	/**
	 * Setter for the emission scenario
	 * @param emissionScenario: the new emission scenario
	 */
	public void setEmissionScenario(ClimateEmissionScenario emissionScenario) {
		this.emissionScenario = emissionScenario;
	}
	
	/**
	 * String representation of the ClimateParams object
	 */
	@Override
	public String toString() {
		return new String(modelName + "(" + model + "), " + emissionScenario);
	}
}
