package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing data from the dataset available from CSIRO. 
 * It associates values to the NRM regions, CSIRO variables and parameters.
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "CSIROData")
public class CsiroData
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	
	/**
	 * The unique ID of the CSIRO Data
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The region to which the data applies
	 */
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	
	/**
	 * The parameters of the computed data (Climate model, Emission Scenario, Assessment Year)
	 */
	@ManyToOne
	@JoinColumn(name="csiroparams_id")
	private CsiroParams parameters;

	/**
	 * The variable that this data represents
	 */
	@ManyToOne
	@JoinColumn(name="csirovariable_id")
	private CsiroVariable variable;

	/**
	 * The value of the data
	 */
	private long value;
	
	/**
	 * Default constructor of CsiroData
	 */
	public CsiroData() {
	}
	
	/**
	 * Constructor of CsiroVariable
	 * @param region: the region to which the data applies
	 * @param parameters: the parameters of the computed data
	 * @param variable: the variable that this data represents
	 * @param value: the value of the data
	 */
	public CsiroData(Region region, CsiroParams parameters, CsiroVariable variable, long value) {
		setRegion(region);
		setParameters(parameters);
		setVariable(variable);
		setValue(value);
	}

	/**
	 * Getter for the unique ID of the CSIRO Data
	 * @return: the unique ID of the CSIRO Data
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for the region to which the data applies
	 * @return
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Setter for the region to which the data applies
	 * @param region: the new region to which the data applies
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
	
	/**
	 * Getter for the parameters of the computed data (Climate model, Emission Scenario, Assessment Year)
	 * @return: the parameters of the computed data
	 */
	public CsiroParams getParameters() {
		return parameters;
	}
	
	/**
	 * Setter for the parameters of the computed data (Climate model, Emission Scenario, Assessment Year)
	 * @param parameters: the new parameters of the computed data
	 */
	public void setParameters(CsiroParams parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * Getter for the variable that this Data represent
	 * @return: the variable represented by this data
	 */
	public CsiroVariable getVariable() {
		return variable;
	}
	
	/**
	 * Setter for the variable that this Data represent
	 * @param variable: the new variable represented by this data
	 */
	public void setVariable(CsiroVariable variable) {
		this.variable = variable;
	}
	
	/**
	 * Getter for the value of the Data
	 * @return: the value of the data
	 */
	public float getValue() {
		return value;
	}
	
	/**
	 * Getter for the value of the Data
	 * @param value: the new value of the Data
	 */
	public void setValue(long value) {
		this.value = value;
	}
}
