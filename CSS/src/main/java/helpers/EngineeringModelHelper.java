package helpers;

import java.util.HashMap;
import java.util.Map;

public class EngineeringModelHelper {

	/**
	 * Listing of the engineering model variable names linked to the index of the column to extract in the Excel file
	 */
	public static final Map<String, Integer> ENGINEERING_MODEL_VARIABLES;
	static {
		ENGINEERING_MODEL_VARIABLES = new HashMap<String, Integer>();
		
		ENGINEERING_MODEL_VARIABLES.put("Corrosion initiation of reinforcing bar probability", 102); // Column CY
		ENGINEERING_MODEL_VARIABLES.put("Change in chloride concentration at concrete cover depth", 100); // Column CW 
		ENGINEERING_MODEL_VARIABLES.put("Change in corrosion rate of reinforcing bar", 105); // Column DB 
		ENGINEERING_MODEL_VARIABLES.put("Corrosion initiation time", 143); // Column EN
		ENGINEERING_MODEL_VARIABLES.put("Crack propagation time", 144); // Column EO
		ENGINEERING_MODEL_VARIABLES.put("Time to severe cracking (1mm crack width)", 145); // Column EP
		ENGINEERING_MODEL_VARIABLES.put("Chloride induced corrosion probability to severe cracking", 147); // Column ER 
		ENGINEERING_MODEL_VARIABLES.put("Reduction or loss in reinforcing bar", 148); // Column ES
		
		ENGINEERING_MODEL_VARIABLES.put("Corrosion initiation of reinforcing bar probability", 70); // Column BS
		ENGINEERING_MODEL_VARIABLES.put("Change in carbonation depth", 68); // Column BQ
		ENGINEERING_MODEL_VARIABLES.put("Change in corrosion rate of reinforcing bar", 76); // Column BY
		ENGINEERING_MODEL_VARIABLES.put("Corrosion initiation time", 137); // Column EH
		ENGINEERING_MODEL_VARIABLES.put("Crack propagation time", 138); // Column EI
		ENGINEERING_MODEL_VARIABLES.put("Time to severe cracking (1mm crack width)", 139); // Column EJ
		ENGINEERING_MODEL_VARIABLES.put("Carbonation induced corrosion probability to severe cracking", 141); // Column EL
		ENGINEERING_MODEL_VARIABLES.put("Reduction or loss in reinforcing bar", 142); // Column EM
	}
}
