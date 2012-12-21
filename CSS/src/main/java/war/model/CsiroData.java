package war.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class representing the dataset available from CSIRO. 
 * It associates the CSIRO variables and parameters to values.
 * @author Guillaume Prevost
 * @since 20th Dec. 2012
 */
@Entity
@Table(name = "CSIROData")
public class CsiroData
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	
	/**
	 * The unique ID of the CSIRO Data
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="region_id")
	private Region region;
	  
	@ManyToOne
	@JoinColumn(name="csiroparams_id")
	private CsiroParams parameter;

	@ManyToOne
	@JoinColumn(name="csirovariable_id")
	private CsiroVariable variable;

	private long value;
	
	
	public CsiroData() {
	}
	
	public CsiroData(Region region, CsiroParams parameter, CsiroVariable variable, long value) {
		setRegion(region);
		setParameter(parameter);
		setVariable(variable);
		setValue(value);
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
	
	public CsiroParams getParameter() {
		return parameter;
	}
	
	public void setParameter(CsiroParams parameter) {
		this.parameter = parameter;
	}
	
	public CsiroVariable getVariable() {
		return variable;
	}
	
	public void setVariable(CsiroVariable variable) {
		this.variable = variable;
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
}
