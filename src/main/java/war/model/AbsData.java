/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Class representing data from ABS
 * @author Guillaume Prevost
 * @since 4th Apr. 2013
 */
@Entity
@Table(name = "AbsData")
public class AbsData {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the measurement
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The seaport to which this data relates to
	 */
	@ManyToOne
	@JoinColumn(name="seaport_id")
	Seaport seaport;
	
	/**
	 * The variable that this data represents
	 */
	@ManyToOne
	@JoinColumn(name="abs_variable_id")
	private AbsVariable variable;
	
	/**
	 * The evolution of the variable value
	 * Formatted as "YYYY,Value1;YYYY,value2;...;YYYY,value3"
	 */
	@Column
	private String data;
	
    @Transient
    private Map<Integer, Float> values = new HashMap<Integer, Float>();
	
	/**
	 * Default constructor of AbsData
	 */
	public AbsData() {
	}
	
	/**
	 * Constructor of AbsData specifying all the fields
	 * @param seaport: the seaport to which this data relates to
	 * @param variable: the variable that this data represents
	 * @param value: the evolution of the variable value
	 */
	public AbsData(Seaport seaport, AbsVariable variable, String data) {
		setSeaport(seaport);
		setVariable(variable);
		setData(data);
	}
	
	/**
	 * Getter for the unique ID of the parameters combination
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter for the seaport to which this data relates to
	 * @return: the current seaport to which this data relates to
	 */
	public Seaport getSeaport() {
		return this.seaport;
	}
	
	/**
	 * Setter for the seaport to which this data relates to
	 * @param seaport: the new seaport to which this data relates to
	 */
	public void setSeaport(Seaport seaport) {
		this.seaport = seaport;
	}
	
	/**
	 * Getter for the variable that this Data represent
	 * @return: the variable represented by this data
	 */
	public AbsVariable getVariable() {
		return variable;
	}
	
	/**
	 * Setter for the variable that this Data represent
	 * @param variable: the new variable represented by this data
	 */
	public void setVariable(AbsVariable variable) {
		this.variable = variable;
	}
	
	/**
	 * Getter for the evolution of the variable value
	 * @return the current evolution of the variable value
	 */
	public String getData() {
		if (this.data == null)
			return generateData();
		else
			return this.data;
	}
	
	/**
	 * Setter for the evolution of the variable value
	 * @param data: the new evolution of the variable value (formatted as "YYYY,Value1;YYYY,value2;...;YYYY,value3")
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
}