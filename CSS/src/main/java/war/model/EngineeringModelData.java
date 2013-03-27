package war.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	
	public static final String PAIR_SEPARATOR = ";";
	public static final String YEAR_VALUE_SEPARATOR = ",";
	
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
	@JoinColumn(name="engineering_model_variable_id")
	private EngineeringModelVariable variable;

	/**
	 * The asset to which this data is related to
	 */
	@ManyToOne
	@JoinColumn(name="engineering_model_asset_id")
	private EngineeringModelAsset asset;
	
	/**
	 * The Data Elements using this data
	 */
	@ManyToMany(mappedBy="engineeringModelDataList")
	private List<DataElementEngineeringModel> dataElementEngineeringModelList;
	
    /**
     * The string content of the engineering model data
     * Under the form "YYYY,VALUE1;YYYY,VALUE2;...YYYY,VALUE70"
     */
	@Column(columnDefinition = "TEXT")
    private String data;
    
    @Transient
    private Map<Integer, Float> values = new HashMap<Integer, Float>();
	
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
	 * @param year: the year for which the data is computed
	 * @param value: the value of the data
	 */
	public EngineeringModelData(EngineeringModelAsset asset, ClimateParams parameters, EngineeringModelVariable variable, Map<Integer, Float> values) {
		setAsset(asset);
		setParameters(parameters);
		setVariable(variable);
		setValues(values);
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
	 * @return: the current parameters of the computed data
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
	 * @return: the current variable represented by this data
	 */
	public EngineeringModelVariable getVariable() {
		return variable;
	}
	
	/**
	 * Setter for the variable that this Data represent
	 * @param variable: the new variable represented by this data
	 */
	public void setVariable(EngineeringModelVariable variable) {
		this.variable = variable;
	}
	
	/**
	 * Getter for the asset to which this data is related to
	 * @return: the current asset to which this data is related to
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
	 * Getter for the list of Data Element using this data
	 * @return: the current list of Data Element using this data
	 */
	public List<DataElementEngineeringModel> getDataElementEngineeringModelList() {
		return dataElementEngineeringModelList;
	}
	
	/**
	 * Setter for the list of Data Element using this data
	 * @param asset: the new list of Data Element using this data
	 */
	public void setDataElementEngineeringModelList(List<DataElementEngineeringModel> dataElementEngineeringModelList) {
		this.dataElementEngineeringModelList = dataElementEngineeringModelList;
	}
	
	
	/**
	 * Getter for the data string (under the form "YYYY,VALUE1;YYYY,VALUE2;...YYYY,VALUE70")
	 * @return: the current data string
	 */
	public String getData() {
		if (this.data == null)
			return generateData();
		else
			return this.data;
	}
	
	/**
	 * Setter for the data string (under the form "YYYY,VALUE1;YYYY,VALUE2;...YYYY,VALUE70")
	 * @param data: the new data string
	 */
	public void setData(String data) {
		this.data = data;
		generateValues();
	}
	
	/**
	 * Getter for the map of year & values of the data
	 * @return: the current map of year & values of the data
	 */
	public Map<Integer, Float> getValues() {
		if (this.values == null)
			return generateValues();
		else
			return this.values;
	}
	
	/**
	 * Setter for the map of year & values of the data
	 * @param value: the new map of year & values of the data
	 */
	public void setValues(Map<Integer, Float> values) {
		this.values = values;
		generateData();
	}
	
	/**
	 * Generate the map of year & values from the data string
	 * @return: the new generated map of year & values
	 */
	public Map<Integer, Float> generateValues() {
		this.values.clear();
		
		String[] arrPairs = this.data.split(EngineeringModelData.PAIR_SEPARATOR);
		for (String pair : arrPairs) {
			String[] splittedPair = pair.split(EngineeringModelData.YEAR_VALUE_SEPARATOR);
			this.values.put(Integer.valueOf(splittedPair[0]), Float.valueOf(splittedPair[1]));
		}
		return this.values;
	}
	
	/**
	 * Generate the data string from the map of year & values
	 * @return the new generated data string
	 */
	public String generateData() {
		String val = "";
		Iterator<Map.Entry<Integer, Float>> it = this.values.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Integer, Float> pairs = (Map.Entry<Integer, Float>)it.next();
	        val += pairs.getKey() + EngineeringModelData.YEAR_VALUE_SEPARATOR + pairs.getValue();
	        if (it.hasNext())
	        	val += EngineeringModelData.PAIR_SEPARATOR;
	        it.remove();
	    }
	    this.data = val;
	    return this.data;
	}
	
	
	@Override
	public String toString() {
		return (this.variable.getName() + "(" + this.variable.getUom() + ") " + this.data);
	}
}