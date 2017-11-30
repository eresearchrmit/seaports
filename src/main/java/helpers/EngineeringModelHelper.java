/**
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */
package helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class specifying 
 * @author Guillaume Prevost
 */
public class EngineeringModelHelper {

	/**
	 * Listing of the engineering model variable names (as defined in the database) linked to 
	 * the corresponding index of the column to extract in the uploaded Excel files.
	 * example: the 'Crack propagation time' variable due to 'Chloride' can be found in column 144 of the Excel file.
	 */
	public static final Map<String, Integer> ENGINEERING_MODEL_VARIABLES;
	static {
		ENGINEERING_MODEL_VARIABLES = new HashMap<String, Integer>();
		
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Corrosion initiation of reinforcing bar probability", 102); // Column CY
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Change in chloride concentration at concrete cover depth", 100); // Column CW 
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Change in corrosion rate of reinforcing bar", 105); // Column DB 
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Corrosion initiation time", 143); // Column EN
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Crack propagation time", 144); // Column EO
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Time to severe cracking (1mm crack width)", 145); // Column EP
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Chloride induced corrosion probability to severe cracking", 147); // Column ER 
		ENGINEERING_MODEL_VARIABLES.put("Chloride|Reduction or loss in reinforcing bar", 148); // Column ES
		
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Corrosion initiation of reinforcing bar probability", 70); // Column BS
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Change in carbonation depth", 68); // Column BQ
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Change in corrosion rate of reinforcing bar", 76); // Column BY
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Corrosion initiation time", 137); // Column EH
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Crack propagation time", 138); // Column EI
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Time to severe cracking (1mm crack width)", 139); // Column EJ
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Carbonation induced corrosion probability to severe cracking", 141); // Column EL
		ENGINEERING_MODEL_VARIABLES.put("Carbonation|Reduction or loss in reinforcing bar", 142); // Column EM
	}
	
	/**
	 * Number of assets IDs used for engineering models examples
	 */
	public static final int ENGINEERING_MODEL_EXAMPLE_ASSETS_COUNT = 3;
}
