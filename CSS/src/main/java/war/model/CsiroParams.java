package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing a combination of CSIRO Parameters. 
 * Includes a climate model, an emissions scenario and a year of assessment.
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "CSIROParams")
public class CsiroParams {
	
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the combination of parameters
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The name of the climate model
	 */
	private String modelName;
	
	/**
	 * The name of the emissions scenario
	 */
	private String emissionScenario;
	
	/**
	 * The year of assessment (i.e. for which year the value is computed
	 */
	private int assessmentYear;
	
	/**
	 * Default Constructor of CsiroParams
	 */
	public CsiroParams() {
	}
	
	/**
	 * Constructor of CsiroParams
	 * @param modelName: the name of the climate model
	 * @param emissionScenario: the name of the emission scenario
	 * @param assessmentYear: the year for which the data are computed
	 */
	public CsiroParams(String modelName, String emissionScenario, int assessmentYear) {
		this.modelName = modelName;
		this.emissionScenario = emissionScenario;
		this.assessmentYear = assessmentYear;
	}
	
	/**
	 * Getter for the unique ID of the parameters combination
	 * @return the unique ID of the parameters
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the name of the climate model
	 * @param modelName: the new name of the climate model 
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * Getter for the name of the climate model
	 * @return the name of the climate model
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * Setter for the name of the emission scenario
	 * @param emissionScenario: the new name of the emission scenario
	 */
	public void setEmissionScenario(String emissionScenario) {
		this.emissionScenario = emissionScenario;
	}

	/**
	 * Getter for the name of the emission scenario
	 * @return the name of the emission scenario
	 */
	public String getEmissionScenario() {
		return emissionScenario;
	}

	/**
	 * Setter for the year of assessment
	 * @param assessmentYear: the new year of assessment
	 */
	public void setAssessmentYear(int assessmentYear) {
		this.assessmentYear = assessmentYear;
	}

	/**
	 * Getter for the year of assessment
	 * @return the year of assessment
	 */
	public int getAssessmentYear() {
		return assessmentYear;
	}
}
