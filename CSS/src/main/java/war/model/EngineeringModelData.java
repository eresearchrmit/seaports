package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing data from the concrete deterioration engineering model output. 
 * It associates values with the climate variables and climate parameters.
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "EngineeringModelData")
public class EngineeringModelData
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	
	/**
	 * The unique ID of the Engineering Model Data
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The parameters of the computed data (Region, Climate model, Emission Scenario, Year)
	 */
	@ManyToOne
	@JoinColumn(name="climate_params_id")
	private ClimateParams parameters;

	/**
	 * The variable that this data represents
	 */
	@ManyToOne
	@JoinColumn(name="climate_variable_id")
	private ClimateVariable variable;

	/**
	 * The asset to which this data is related to
	 */
	@ManyToOne
	@JoinColumn(name="engineering_model_asset_id")
	private EngineeringModelAsset asset;
	
	/**
	 * The value of the data
	 */
	private Double value;
	
	/**
	 * Default constructor of EngineeringModelData
	 */
	public EngineeringModelData() {
	}
	
	/**
	 * Constructor of EngineeringModelData
	 * @param asset: the asset to which this data is related to
	 * @param parameters: the parameters of the computed data
	 * @param variable: the variable that this data represents
	 * @param value: the value of the data
	 */
	public EngineeringModelData(EngineeringModelAsset asset, ClimateParams parameters, ClimateVariable variable, Double value) {
		setAsset(asset);
		setParameters(parameters);
		setVariable(variable);
		setValue(value);
	}

	/**
	 * Getter for the unique ID of the Engineering Model Data
	 * @return: the unique ID of the Engineering Model Data
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for the parameters of the computed data (Climate model, Emission Scenario, Year...)
	 * @return: the parameters of the computed data
	 */
	public ClimateParams getParameters() {
		return parameters;
	}
	
	/**
	 * Setter for the parameters of the computed data (Climate model, Emission Scenario, Year...)
	 * @param parameters: the new parameters of the computed data
	 */
	public void setParameters(ClimateParams parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Getter for the variable that this Data represent
	 * @return: the variable represented by this data
	 */
	public ClimateVariable getVariable() {
		return variable;
	}
	
	/**
	 * Setter for the variable that this Data represent
	 * @param variable: the new variable represented by this data
	 */
	public void setVariable(ClimateVariable variable) {
		this.variable = variable;
	}
	
	/**
	 * Getter for the asset to which this data is related to
	 * @return: the asset to which this data is related to
	 */
	public EngineeringModelAsset getAsset() {
		return asset;
	}
	
	/**
	 * Setter for the asset to which this data is related to
	 * @param asset: the new asset to which this data is related to
	 */
	public void setAsset(EngineeringModelAsset asset) {
		this.asset = asset;
	}
	
	/**
	 * Getter for the value of the Data
	 * @return: the value of the data
	 */
	public Double getValue() {
		return value;
	}
	
	/**
	 * Getter for the value of the Data
	 * @param value: the new value of the Data
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}
