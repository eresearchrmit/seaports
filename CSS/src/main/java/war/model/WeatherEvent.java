package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class representing a weather event that have impacted a seaport
 * @author Guillaume Prevost
 * @since 26th Mar. 2013
 */
@Entity
@Table(name = "WeatherEvent")
public class WeatherEvent
{
	private static final long serialVersionUID = -1308795024262635690L;
	
	/**
	 * The unique ID of the Weather Event
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	/**
	 * The type of the weather event
	 */
	@Column
	private String type;
	
	/**
	 * The year when the weather event happened
	 */
	@Column
	private int year;
	
	/**
	 * Whether the event had direct or indirect impact
	 */
	@Column
	private Boolean direct;
	
	/**
	 * Description of the impact of the weather event
	 */
	@Column
	private String impact;
	
	/**
	 * Description of the consequences of the weather event
	 */
	@Column
	private String consequencesRating;
	
	/**
	 * Description of the other consequences not listed in the rating
	 */
	@Column
	private String consequencesOther;
	
	/**
	 * Whether the response was adequate or not
	 */
	@Column
	private Boolean responseAdequate;
	
	/**
	 * Description of the changes implemented after the event
	 */
	@Column
	private String changes;
	
	/**
	 * Default constructor of WeatherEvent
	 */
	public WeatherEvent() {
		
	}
	
	/**
	 * Constructor of WeatherEvent specifying all the fields
	 * @param type: the type of the weather event
	 * @param year: the year when the weather event happened
	 * @param direct: whether the event had direct or indirect impact
	 * @param impact: the description of the impact of the weather event
	 * @param consequencesRating: the rating of the consequences of the weather event
	 * @param consequencesOther: the other consequences not listed in the rating
	 * @param response: the description of the response to the weather event
	 * @param responseAdequate: whether the response was adequate or not
	 * @param changes: description of the changes implemented after the Weather Event
	 */
	public WeatherEvent(String type, Integer year, Boolean direct, String impact, 
			String consequencesRating, String consequencesOther, Boolean responseAdequate, String changes) {
		setType(type);
		setYear(year);
		setDirect(direct);
		setImpact(impact);
		setConsequencesRating(consequencesRating);
		setConsequencesOther(consequencesOther);
		setResponseAdequate(responseAdequate);
		setChanges(changes);
	}

	/**
	 * Getter for the unique ID of the Weather Event
	 * @return: the current unique ID of the Weather Event
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Setter for the unique ID of the Weather Event
	 * @param id: the new unique ID of the Weather Event
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Getter for the type of the Weather Event
	 * @return: the current type of the Weather Event
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Setter for the type of the Weather Event
	 * @param type: the new type of the Weather Event
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Getter for the year of the Weather Event
	 * @return: the current year of the Weather Event
	 */
	public Integer getYear() {
		return this.year;
	}
	
	/**
	 * Setter for the year of the Weather Event
	 * @param year: the new year of the Weather Event
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	
	/**
	 * Getter for whether the event had direct or indirect impact
	 * @return: true if the weather event had direct impact, false if indirect
	 */
	public Boolean getDirect() {
		return this.direct;
	}
	
	/**
	 * Setter for whether the event had direct or indirect impact
	 * @param year: true if the weather event had direct impact, false if indirect
	 */
	public void setDirect(Boolean direct) {
		this.direct = direct;
	}
	
	/**
	 * Getter for the description of the impact of the Weather Event
	 * @return: the current description of the impact of the Weather Event
	 */
	public String getImpact() {
		return this.impact;
	}
	
	/**
	 * Setter for the description of the impact Weather Event
	 * @param impact: the new description of the impact of the Weather Event
	 */
	public void setImpact(String impact) {
		this.impact = impact;
	}
	
	/**
	 * Getter for the rating of the consequences of the Weather Event
	 * @return: the current rating of the consequences of the Weather Event
	 */
	public String getConsequencesRating() {
		return this.consequencesRating;
	}
	
	/**
	 * Setter for the description of the consequences Weather Event
	 * @param consequencesRating: the new rating of the consequences of the Weather Event
	 */
	public void setConsequencesRating(String consequencesRating) {
		this.consequencesRating = consequencesRating;
	}
	
	/**
	 * Getter for the other consequences not listed in the ratings
	 * @return: the current other consequences not listed in the ratings
	 */
	public String getConsequencesOther() {
		return this.consequencesOther;
	}
	
	/**
	 * Setter for the other consequences not listed in the ratings
	 * @param consequencesOther: the new other consequences not listed in the ratings
	 */
	public void setConsequencesOther(String consequencesOther) {
		this.consequencesOther = consequencesOther;
	}
	
	/**
	 * Getter for whether the response to the event was adequate
	 * @return: true if the response was adequate, false otherwise
	 */
	public Boolean getResponseAdequate() {
		return this.responseAdequate;
	}
	
	/**
	 * Setter for whether the response to the event was adequate
	 * @param adequate: true if the response was adequate, false otherwise
	 */
	public void setResponseAdequate(Boolean responseAdequate) {
		this.responseAdequate = responseAdequate;
	}

	/**
	 * Getter for the description of the changes implemented after the Weather Event
	 * @return: the current description of the changes implemented after the Weather Event
	 */
	public String getChanges() {
		return this.changes;
	}
	
	/**
	 * Setter for the description of the changes implemented after the Weather Event
	 * @param changes: the new description of the changes implemented after the Weather Event
	 */
	public void setChanges(String changes) {
		this.changes = changes;
	}
}