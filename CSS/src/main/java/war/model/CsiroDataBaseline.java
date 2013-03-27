package war.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing the baseline data from the dataset available from CSIRO. 
 * It associates values to the climate variables and climate parameters.
 * @author Guillaume Prevost
 * @since 01th Feb. 2013
 */
@Entity
@Table(name = "csirodatabaseline")
public class CsiroDataBaseline
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the CSIRO Baseline Data
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	/**
	 * The date when this baseline data has been created
	 */
	private Date creationDate;
	
	/**
	 * The region this baseline data applies to
	 */
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	
	/**
	 * The variable that this baseline data represents
	 */
	@ManyToOne
	@JoinColumn(name="climate_variable_id")
	private CsiroVariable variable;
	
	/**
	 * The value of the data
	 */
	private Double value;
	
	/**
	 * Default constructor of CsiroDataBaseline
	 */
	public CsiroDataBaseline() {
		setCreationDate(new Date());
	}
	
	/**
	 * Constructor of CsiroDataBaseline
	 * @param creationDate: the date when this baseline data has been created
	 * @param region: the region for which the data is computed
	 * @param variable: the variable that this baseline data represents
	 * @param value: the value of the baseline data
	 */
	public CsiroDataBaseline(Date creationDate, Region region, CsiroVariable variable, Double value) {
		setCreationDate(creationDate);
		setRegion(region);
		setVariable(variable);
		setValue(value);
	}

	/**
	 * Getter for the unique ID of the CSIRO baseline data
	 * @return: the unique ID of the CSIRO baseline data
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for the region this baseline data applies to
	 * @return: the current region this baseline data applies to
	 */
	public Region getRegion() {
		return this.region;
	}
	
	/**
	 * Setter for the region this baseline data applies to
	 * @param value: the new region this baseline data applies to
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
	
	/**
	 * Getter for the creation date of the baseline data
	 * @return: the current creation date of the baseline data
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}
	
	/**
	 * Setter for the creation date of the baseline data
	 * @param value: the new creation date of the baseline data
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Getter for the variable that this baseline data represent
	 * @return: the current variable represented by this baseline data
	 */
	public CsiroVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * Setter for the variable that this baseline data represent
	 * @param variable: the new variable represented by this baseline data
	 */
	public void setVariable(CsiroVariable variable) {
		this.variable = variable;
	}
	
	/**
	 * Getter for the value of the baseline data
	 * @return: the current value of the baseline data
	 */
	public Double getValue() {
		return this.value;
	}
	
	/**
	 * Getter for the value of the baseline data
	 * @param value: the new value of the baseline data
	 */
	public void setValue(Double value) {
		this.value = value;
	}
}