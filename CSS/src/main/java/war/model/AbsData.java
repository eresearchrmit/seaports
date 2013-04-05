package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private String value;
	
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
	public AbsData(Seaport seaport, AbsVariable variable, String value) {
		setSeaport(seaport);
		setVariable(variable);
		setValue(value);
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
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Setter for the evolution of the variable value
	 * @param value: the new evolution of the variable value (formatted as "YYYY,Value1;YYYY,value2;...;YYYY,value3")
	 */
	public void setValue(String value) {
		this.value = value;
	}
}