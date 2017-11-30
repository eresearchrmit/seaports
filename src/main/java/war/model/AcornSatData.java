/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
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

/**
 * Class representing a data measurement done by an ACORN-Sat weather station
 * @author Guillaume Prevost
 * @since 4th Apr. 2013
 */
@Entity
@Table(name = "AcornSatData")
public class AcornSatData {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the measurement
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The weather station that performed this measure
	 */
	@ManyToOne
	@JoinColumn(name="acorn_sat_station_id")
	private AcornSatStation acornSatStation;
	
	/**
	 * The variable that this data represents
	 */
	@ManyToOne
	@JoinColumn(name="acorn_sat_variable_id")
	private AcornSatVariable variable;
	
	/**
	 * Whether the recorded value is an extreme or a mean value
	 */
	@Column
	private Boolean extreme;
	
	/**
	 * The start date of the period this data relates to
	 */
	private Date periodStart;
	
	/**
	 * The end date of the period this data relates to
	 */
	private Date periodEnd;
	
	/**
	 * The value of the measured data
	 */
	@Column
	private Double value;
	
	/**
	 * The date when the data was measured
	 */
	@Column
	private Date dateMeasured;
	
	/**
	 * Default constructor of AcornSatData
	 */
	public AcornSatData() {
	}
	
	/**
	 * Constructor of AcornSatData specifying all the fields
	 * @param acornSatStation: the weather station that performed this measure
	 * @param variable: the variable that this data represents
	 * @param extreme: whether the recorded value is an extreme (true) or a mean (false) value
	 * @param periodStart: the start date of the period this data relates to
	 * @param periodEnd: the end date of the period this data relates to
	 * @param value: the value of the measured data
	 * @param dateMeasured: the date when the data was measured
	 */
	public AcornSatData(AcornSatStation acornSatStation, AcornSatVariable variable, Boolean extreme, Date periodStart, Date periodEnd, Double value, Date dateMeasured) {
		setAcornSatStation(acornSatStation);
		setVariable(variable);
		setExtreme(extreme);
		setPeriodStart(periodStart);
		setPeriodEnd(periodEnd);
		setValue(value);
		setDateMeasured(dateMeasured);
	}
	
	/**
	 * Getter for the unique ID of the parameters combination
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter for the weather station that performed this measure
	 * @return: the current weather station that performed this measure
	 */
	public AcornSatStation getAcornSatStation() {
		return this.acornSatStation;
	}
	
	/**
	 * Setter for the weather station that performed this measure
	 * @param variable: the new weather station that performed this measure
	 */
	public void setAcornSatStation(AcornSatStation acornSatStation) {
		this.acornSatStation = acornSatStation;
	}
	
	/**
	 * Getter for the variable that this Data represent
	 * @return: the variable represented by this data
	 */
	public AcornSatVariable getVariable() {
		return variable;
	}
	
	/**
	 * Setter for the variable that this Data represent
	 * @param variable: the new variable represented by this data
	 */
	public void setVariable(AcornSatVariable variable) {
		this.variable = variable;
	}
	
	/**
	 * Getter for whether the value is extreme (true) or mean (false)
	 * @return whether the value is extreme (true) or mean (false)
	 */
	public Boolean getExtreme() {
		return this.extreme;
	}
	
	/**
	 * Setter for whether the value is extreme (true) or mean (false)
	 * @param extreme: whether the value is extreme (true) or mean (false)
	 */
	public void setExtreme(Boolean extreme) {
		this.extreme = extreme;
	}
	

	/**
	 * Getter for the start date of the period this data relates to
	 * @return: The start date of the period this data relates to
	 */
	public Date getPeriodStart() {
		return this.periodStart;
	}
	
	/**
	 * Setter for the start date of the period this data relates to
	 * @param periodStart: the new start date of the period this data relates to
	 */
	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}
	
	/**
	 * Getter for the end date of the period this data relates to
	 * @return: The end date of the period this data relates to
	 */
	public Date getPeriodEnd() {
		return this.periodEnd;
	}
	
	/**
	 * Setter for the end date of the period this data relates to
	 * @param periodEnd: the new end date of the period this data relates to
	 */
	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}
	
	/**
	 * Getter for the value of the data
	 * @return the current value of the data
	 */
	public Double getValue() {
		return this.value;
	}
	
	/**
	 * Setter for the value of the data
	 * @param value: the new value of the data
	 */
	public void setValue(Double value) {
		this.value = value;
	}
	
	/**
	 * Getter for the date when the data was measured
	 * @return the current date when the data was measured
	 */
	public Date getDateMeasured() {
		return this.dateMeasured;
	}
	
	/**
	 * Setter for the date when the data was measured
	 * @param dateMeasured: the new date when the data was measured
	 */
	public void setDateMeasured(Date dateMeasured) {
		this.dateMeasured = dateMeasured;
	}
}