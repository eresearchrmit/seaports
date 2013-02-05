package war.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Class representing data from the dataset available from CSIRO. 
 * It associates values to the climate variables and climate parameters.
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
	
    /*@ManyToMany(mappedBy="csiroDataList")
    private List<DataElementCsiro> dataElements;
	
	public List<DataElementCsiro> getDataElements() {
		return dataElements;
	}
	public void setDataElements(List<DataElementCsiro> dataElements) {
		this.dataElements = dataElements;
	}*/
    
	/**
	 * The parameters of the computed data (Region, Climate model, Emission Scenario, Year)
	 */
	@ManyToOne
	@JoinColumn(name="climate_params_id")
	private ClimateParams parameters;

	/**
	 * The date when this data has been created
	 */
	private Date creationDate;
	
	/**
	 * The variable that this data represents
	 */
	@ManyToOne
	@JoinColumn(name="climate_variable_id")
	private ClimateVariable variable;

	/**
	 * The value of the data
	 */
	private Double value;
	
	/**
	 * Default constructor of CsiroData
	 */
	public CsiroData() {
		setCreationDate(new Date());
	}
	
	/**
	 * Constructor of CsiroData
	 * @param creationDate: the date when this data has been created
	 * @param parameters: the parameters of the computed data
	 * @param variable: the variable that this data represents
	 * @param value: the value of the data
	 */
	public CsiroData(Date creationDate, ClimateParams parameters, ClimateVariable variable, Double value) {
		setCreationDate(creationDate);
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
	 * Getter for the creation date of the Data
	 * @return: the creation date of the data
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Setter for the creation date of the Data
	 * @param value: the new creation date of the Data
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Getter for the parameters of the computed data (Climate model, Emission Scenario, Assessment Year)
	 * @return: the parameters of the computed data
	 */
	public ClimateParams getParameters() {
		return parameters;
	}
	
	/**
	 * Setter for the parameters of the computed data (Climate model, Emission Scenario, Assessment Year)
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