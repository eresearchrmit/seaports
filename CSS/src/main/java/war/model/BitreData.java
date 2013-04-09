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
 * Class representing a piece BITRE data
 * @author Guillaume Prevost
 * @since 9th Apr. 2013
 */
@Entity
@Table(name = "BitreData")
public class BitreData {

	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the measurement
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The seaport to which this data is related to
	 */
	@ManyToOne
	@JoinColumn(name="seaport_id")
	private Seaport seaport;
	
	/**
	 * The variable that this data represents
	 */
	@ManyToOne
	@JoinColumn(name="bitre_variable_id")
	private BitreVariable variable;
	
	/**
	 * The value of the measured data
	 */
	@Column
	private String value;
	
	/**
	 * Default constructor of AcornSatData
	 */
	public BitreData() {
	}
	
	/**
	 * Constructor of AcornSatData specifying all the fields
	 * @param seaport: the seaport to which this data is related to
	 * @param variable: the variable that this data represents
	 * @param value: the value of the measured data
	 */
	public BitreData(Seaport seaport, BitreVariable variable, String value) {
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
	 * Getter for the seaport to which this data is related to
	 * @return: the current seaport to which this data is related to
	 */
	public Seaport getSeaport() {
		return this.seaport;
	}
	
	/**
	 * Setter for the seaport to which this data is related to
	 * @param variable: the new seaport to which this data is related to
	 */
	public void setSeaport(Seaport seaport) {
		this.seaport = seaport;
	}
	
	/**
	 * Getter for the variable that this Data represent
	 * @return: the variable represented by this data
	 */
	public BitreVariable getVariable() {
		return variable;
	}
	
	/**
	 * Setter for the variable that this Data represent
	 * @param variable: the new variable represented by this data
	 */
	public void setVariable(BitreVariable variable) {
		this.variable = variable;
	}

	/**
	 * Getter for the value of the data
	 * @return the current value of the data
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Setter for the value of the data
	 * @param value: the new value of the data
	 */
	public void setValue(String value) {
		this.value = value;
	}
}