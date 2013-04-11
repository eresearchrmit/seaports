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
	 * The value of the BITRE data
	 */
	@Column
	private String data;
	
    @Transient
    private Map<Integer, Float> values = new HashMap<Integer, Float>();
	
	/**
	 * Default constructor of AcornSatData
	 */
	public BitreData() {
	}
	
	/**
	 * Constructor of AcornSatData specifying all the fields
	 * @param seaport: the seaport to which this data is related to
	 * @param variable: the variable that this data represents
	 * @param data: the value of the BITRE data
	 */
	public BitreData(Seaport seaport, BitreVariable variable, String data) {
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
	 * Getter for the value of the BITRE data
	 * @return the current value of the BITRE data
	 */
	public String getData() {
		if (this.data == null)
			return generateData();
		else
			return this.data;
	}
	
	/**
	 * Setter for the value of the BITRE data
	 * @param value: the new value of the BITRE data
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