package war.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	private String consequences;
	
	/**
	 * Description of the response to the weather event
	 */
	@Column
	private String response;
	
	/**
	 * Whether the response was adequate or not
	 */
	@Column
	private Boolean responseAdequate;
	
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
	 * @param consequences: the description of the consequences of the weather event
	 * @param consequencesRating: the rating of the consequences of the weather event
	 * @param response: the description of the response to the weather event
	 * @param responseAdequate: whether the response was adequate or not
	 */
	public WeatherEvent(String type, Integer year, Boolean direct, String impact, 
			String consequences, String response, Boolean responseAdequate) {
		setType(type);
		setYear(year);
		setDirect(direct);
		setImpact(impact);
		setConsequences(consequences);
		setResponse(response);
		setResponseAdequate(responseAdequate);
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
	 * Getter for the description of the consequences of the Weather Event
	 * @return: the current description of the consequences of the Weather Event
	 */
	public String getConsequences() {
		return this.consequences;
	}
	
	/**
	 * Setter for the description of the consequences Weather Event
	 * @param impact: the new description of the consequences of the Weather Event
	 */
	public void setConsequences(String consequences) {
		this.consequences = consequences;
	}
	
	/**
	 * Getter for the description of the response of the Weather Event
	 * @return: the current description of the response of the Weather Event
	 */
	public String getResponse() {
		return this.response;
	}
	
	/**
	 * Setter for the description of the response Weather Event
	 * @param impact: the new description of the response of the Weather Event
	 */
	public void setResponse(String response) {
		this.response = response;
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
}