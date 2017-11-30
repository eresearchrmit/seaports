/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.awt.Point;

import org.hibernate.annotations.Type;

/**
 * Class representing the sea level rise data extracted from CMAR
 * @author Guillaume Prevost
 * @since 12th Mar. 2013
 */
@Entity
@Table(name = "CmarData")
public class CmarData
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the CMAR Data
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
	 * The year for which year the data is computed
	 */
	@Column
	private int year;
	
	/**
	 * The values of the sea level rise change for various locations
	 * String formated as follows: X1,Y1,Value1;X2,Y2,Value2;...;XN,YN,ValueN
	 */
	@Column
	@Type(type="text")
	private String value;
	
	/**
	 * The picture representing the values
	 */
	@Column//(columnDefinition = "LONGBLOB")
	private String picture;
	
	/**
	 * Default constructor of CsiroData
	 */
	public CmarData() {
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
	public CmarData(Date creationDate, ClimateParams parameters, CsiroVariable variable, int year, String value, String picture) {
		setCreationDate(creationDate);
		setParameters(parameters);
		setVariable(variable);
		setYear(year);
		setValue(value);
		setPicture(picture);
	}

	/**
	 * Getter for the unique ID of the CMAR Data
	 * @return: the unique ID of the CMAR Data
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
	 * Getter for the value of the variable for various positions
	 * @return: the current value of the variation of the variable
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Getter for the value of the variable for various positions
	 * @return: the current value of the variation of the variable
	 */
	public Map<Point,Double> getValues() {
		Map<Point,Double> values = new HashMap<Point,Double>();
		
		String[] split = this.value.split(";");
		for (String str : split) {
			String[] tmp = str.split(",");
			Point location = new Point();
			location.setLocation(Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]));
			values.put(location, Double.parseDouble(tmp[2]));
		}
		return values;
	}
	
	/**
	 * Getter for the value of the variable for various positions
	 * @param value: the new value of the variable for various positions
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getter for the picture representing the value
	 * @return: the current picture representing the value
	 */
	public String getPicture() {
		return picture;
	}
	
	/**
	 * Getter for the picture representing the value
	 * @param value: the new picture representing the value
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}
}