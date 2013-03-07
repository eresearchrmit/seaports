package war.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;

/**
 * Class representing variation data from the dataset available from CSIRO. 
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
    
	/**
	 * The parameters of the computed data (Region, Climate model, Emission Scenario)
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
	private CsiroVariable variable;

	/**
	 * The baseline value of the variable
	 */
	/*@ManyToOne
	@JoinColumn(name="csiro_data_baseline_id")*/
	@Transient
	private CsiroDataBaseline baseline;
	
	/**
	 * The year for which year the data is computed
	 */
	@Column
	private int year;
	
	/**
	 * The value of the variation of the variable
	 */
	private Double value;
	
	/**
	 * The picture representing the value
	 */
	@Column(columnDefinition = "BLOB")
	private byte[] picture;
	
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
	 * @param year: the year for which the data is computed
	 * @param value: the value of the data
	 * @param picture: the picture representing the value
	 */
	public CsiroData(Date creationDate, ClimateParams parameters, CsiroVariable variable, int year, Double value, byte[] picture) {
		setCreationDate(creationDate);
		setParameters(parameters);
		setVariable(variable);
		setYear(year);
		setValue(value);
		setPicture(picture);
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
	 * Getter for the baseline value of the variable
	 * @return: the current baseline value of the variable
	 */
	public CsiroDataBaseline getBaseline() {
		return baseline;
	}
	
	/**
	 * Getter for the baseline value of the variable
	 * @param value: the new baseline value of the variable
	 */
	public void setBaseline(CsiroDataBaseline baseline) {
		this.baseline = baseline;
	}
	
	 /**
	 * Getter for the year for which year the data is computed
	 * @return the year for which year the data is computed
	 */
	public int getYear() {
		return this.year;
	}
	
	/**
	 * Setter for the year for which year the data is computed
	 * @param year: the new year for which year the data is computed
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Getter for the value of the variation of the variable
	 * @return: the current value of the variation of the variable
	 */
	public Double getValue() {
		return value;
	}
	
	/**
	 * Getter for the value of the variation of the variable
	 * @param value: the new value of the variation of the variable
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * Getter for the picture representing the value
	 * @return: the current picture representing the value
	 */
	public byte[] getPicture() {
		return picture;
	}
	
	/**
	 * Getter for the picture representing the value
	 * @param value: the new picture representing the value
	 */
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	
	public String getStringPicture() {
		if (this.picture != null && this.picture.length > 0)
			return Base64.encodeBase64String(this.picture);
		else
			return "";
	}
}