package war.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	
	@Id 
	private CsiroDataPK primaryKey = new CsiroDataPK();
	
	// Bi-directional association! Needed to trick Hibernate !
	@SuppressWarnings("unused")
	@Column(name="region_id", nullable=false, updatable=false, insertable=false)
	private long region;
	
	@SuppressWarnings("unused")
	@Column(name="csiroparams_id", nullable=false, updatable=false, insertable=false)
	private long item;

	@SuppressWarnings("unused")
	@Column(name="csirovariable_id", nullable=false, updatable=false, insertable=false)
	private long product;
	//----

	private long value;
	
	public CsiroData() {
	}
	
	public CsiroData(Region region, CsiroParams parameter, CsiroVariable variable, long value) {
		setRegion(region);
		setParameter(parameter);
		setVariable(variable);
		setValue(value);
	}

	public void setRegion(Region region) {
		primaryKey.setRegion(region);
	}

	public Region getRegion(){
		return primaryKey.getRegion();
	}
	
	public void setParameter(CsiroParams params) {
		primaryKey.setParameter(params);
	}

	public CsiroParams getParameter(){
		return primaryKey.getParameter();
	}

	public void setVariable(CsiroVariable variable) {
		primaryKey.setVariable(variable);
	}

	public CsiroVariable getVariable() {
		return primaryKey.getVariable();
	}
	
	public float getValue() {
		return value;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
}

@Embeddable 
class CsiroDataPK implements Serializable
{
	private static final long serialVersionUID = -1308795024262635690L;

	@ManyToOne
	private Region region;
	  
	@ManyToOne
	private CsiroParams parameter;

	@ManyToOne
	private CsiroVariable variable;
	
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
}
