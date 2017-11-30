/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing a category of BITRE variable
 * @author Guillaume Prevost
 * @since 9th Apr. 2013
 */
@Entity
@Table(name = "BitreVariableCategory")
public class BitreVariableCategory {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the measurement
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the BITRE data type
	 */
	@Column
	private String name;
		
	/**
	 * Default constructor of BitreDataType
	 */
	public BitreVariableCategory() {
	}
	
	/**
	 * Constructor of BitreDataType specifying its name
	 * @param name: the name of the BITRE data type
	 */
	public BitreVariableCategory(String name) {
		setName(name);
	}
	
	/**
	 * Getter for the unique ID of the 
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Getter for the name of the BITRE data type
	 * @return the current name of the BITRE data type
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Setter for the name of the BITRE data type
	 * @param name: the new name of the BITRE data type
	 */
	public void setName(String name) {
		this.name = name;
	}
}