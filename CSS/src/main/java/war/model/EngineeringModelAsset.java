package war.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing an asset from a seaport, used by the engineering model for computation.
 * @author Guillaume Prevost
 * @since 25th Jan. 2012
 */
@Entity
@Table(name = "EngineeringModelAsset")
public class EngineeringModelAsset
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the Engineering Model Asset
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The unique code of the asset
	 */
	private String assetCode;
	
	/**
	 * The description of the asset
	 */
	private String description;
	
	/**
	 * The year when the asset was built
	 */
	private int yearBuilt;
	
	/**
	 * The zone where the asset is located (atmospheric, tidal, splash, submerged)
	 */
	private String zone;
	
	/**
	 * The distance between the asset and the coast
	 */
	private Double distanceFromCoast;
	
	/**
	 * The class of exposure endured by the asset (B1, B2, C1, C2)
	 */
	private String exposureClass;
	
	/**
	 * The class of carbonation endured by the asset (CB1, CB2, CB3, CB4)
	 */
	private String carbonationClass;
	
	/**
	 * The class of chloride endured by the asset (CL1, CL2, CL3, CL4, CL5)
	 */
	private String chlorideClass;

	/**
	 * Depth of concrete cover (mm)
	 */
	private int cover;
	
	/**
	 * Size of concrete element, either width or Diameter (mm)
	 */
	private int dMember;
	
	/**
	 * f'c : design strength (MPa)
	 */
	private int fPrimeC;

	/**
	 * w/c: Water to cement ratio (0 to 1)
	 */
	private int wc;
	
	/**
	 * Ce: cement content (kg/m3)
	 */
	private int ce;
	
	/**
	 * Diameter of rebar (mm)
	 */
	private int dBar;
	
	/**
	 * Default constructor of EngineeringModelAsset
	 */
	public EngineeringModelAsset() {
	}
	
	/**
	 * Constructor of EngineeringModelAsset
	 * @param assetCode: the asset code
	 * @param variable: the variable that this data represents
	 * @param yearBuilt: the year when the asset was built
	 */
	public EngineeringModelAsset(String assetCode, String description, int yearBuilt, String zone, 
			Double distanceFromCoast, String exposureClass, String carbonationClass, String chlorideClass, 
			int cover, int dMember, int fPrimeC, int wc, int ce, int dBar) {
		setAssetCode(assetCode);
		setDescription(description);
		setYearBuilt(yearBuilt);
		setZone(zone);
		setDistanceFromCoast(distanceFromCoast);
		setExposureClass(exposureClass);
		setCarbonationClass(carbonationClass);
		setChlorideClass(chlorideClass);
		setCover(cover);
		setDMember(dMember);
		setFPrimeC(fPrimeC);
		setWc(wc);
		setCe(ce);
		setDBar(dBar);
	}

	/**
	 * Getter for the unique ID of the asset
	 * @return: the unique ID of the asset
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Getter for the asset code
	 * @return: the asset code
	 */
	public String getAssetCode() {
		return this.assetCode;
	}
	
	/**
	 * Setter for the asset code
	 * @param assetCode: the new asset code
	 */
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	
	/**
	 * Getter for the asset description
	 * @return: the asset description
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * Setter for the asset description
	 * @param description: the new asset description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter for the year when the asset was built
	 * @return the year when the asset was built
	 */
	public int getYearBuilt() {
		return this.yearBuilt;
	}
	
	/**
	 * Setter for the year when the asset was built
	 * @param yearBuilt the yearBuilt to set
	 */
	public void setYearBuilt(int yearBuilt) {
		this.yearBuilt = yearBuilt;
	}
	
	/**
	 * Getter for the zone where the asset is located (atmospheric, tidal, splash, submerged)
	 * @return: the zone of the asset
	 */
	public String getZone() {
		return this.zone;
	}
	
	/**
	 * Setter for the zone where the asset is located (atmospheric, tidal, splash, submerged)
	 * @param zone: the new zone of the asset
	 */
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	/**
	 * Getter for the distance between the asset and the coast
	 * @return: the distance from the coast
	 */
	public Double getDistanceFromCoast() {
		return this.distanceFromCoast;
	}
	
	/**
	 * Setter for the distance between the asset and the coast
	 * @param distanceFromCoast: the new distance from coast
	 */
	public void setDistanceFromCoast(Double distanceFromCoast) {
		this.distanceFromCoast = distanceFromCoast;
	}
	
	/**
	 * Getter for the class of exposure endured by the asset (B1, B2, C1, C2)
	 * @return: the exposure class of the asset
	 */
	public String getExposureClass() {
		return this.exposureClass;
	}
	
	/**
	 * Setter for the class of exposure endured by the asset (B1, B2, C1, C2)
	 * @param exposureClass: the new exposure class of the asset
	 */
	public void setExposureClass(String exposureClass) {
		this.exposureClass = exposureClass;
	}
	
	/**
	 * Getter for the class of carbonation endured by the asset (CB1, CB2, CB3, CB4)
	 * @return: the carbonation class of the asset
	 */
	public String getCarbonationClass() {
		return this.carbonationClass;
	}
	
	/**
	 * Setter for the class of carbonation endured by the asset (CB1, CB2, CB3, CB4)
	 * @param carbonationClass: the new carbonation class of the asset
	 */
	public void setCarbonationClass(String carbonationClass) {
		this.carbonationClass = carbonationClass;
	}
	
	/**
	 * Getter for the class of chloride endured by the asset (CL1, CL2, CL3, CL4, CL5)
	 * @return: the carbonation class of the asset
	 */
	public String getChlorideClass() {
		return this.chlorideClass;
	}
	
	/**
	 * Setter for the class of chloride endured by the asset (CL1, CL2, CL3, CL4, CL5)
	 * @param chlorideClass: the new chloride class of the asset
	 */
	public void setChlorideClass(String chlorideClass) {
		this.chlorideClass = chlorideClass;
	}
	
	/**
	 * Getter for the depth of concrete cover (mm)
	 * @return: the concrete cover of the asset
	 */
	public int getCover() {
		return this.cover;
	}
	
	/**
	 * Setter for the depth of concrete cover (mm)
	 * @param cover: the new concrete cover of the asset
	 */
	public void setCover(int cover) {
		this.cover = cover;
	}
	
	/**
	 * Getter for the size of the concrete element
	 * @return: the size of the concrete element
	 */
	public int getDMember() {
		return this.dMember;
	}
	
	/**
	 * Setter for the size of the concrete element
	 * @param dMember: the new size of the concrete element
	 */
	public void setDMember(int dMember) {
		this.dMember = dMember;
	}
	
	/**
	 * Getter for the design strength
	 * @return: the design strength of the asset
	 */
	public int getFPrimeC() {
		return this.fPrimeC;
	}
	
	/**
	 * Setter for the design strength
	 * @param fPrimeC: the new design strength of the asset
	 */
	public void setFPrimeC(int fPrimeC) {
		this.fPrimeC = fPrimeC;
	}
	
	/**
	 * Getter for the water to cement ratio
	 * @return: the water to cement ratio of the asset
	 */
	public int getWc() {
		return this.wc;
	}
	
	/**
	 * Setter for the water to cement ratio
	 * @param wc: the new water to cement ratio of the asset
	 */
	public void setWc(int wc) {
		this.wc = wc;
	}
	
	/**
	 * Getter for the cement content
	 * @return: the cement content of the asset
	 */
	public int getCe() {
		return this.ce;
	}
	
	/**
	 * Setter for the cement content
	 * @param ce: the new cement content of the asset
	 */
	public void setCe(int ce) {
		this.ce = ce;
	}
	
	/**
	 * Getter for the diameter of rebar
	 * @return: the diameter of rebar of the asset
	 */
	public int getDBar() {
		return this.dBar;
	}
	
	/**
	 * Setter for the diameter of rebar
	 * @param dBar: the new diameter of rebar of the asset
	 */
	public void setDBar(int dBar) {
		this.dBar = dBar;
	}
}
