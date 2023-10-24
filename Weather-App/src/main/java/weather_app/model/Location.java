package weather_app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String zipcode;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private List<SavedLocation> savedLocation;

	public Location() {
		super();
	}

	public Location(Integer id, String city, String zipcode,
			List<SavedLocation> savedLocation) {
		super();
		this.id = id;
		this.city = city;
		this.zipcode = zipcode;
		this.savedLocation = savedLocation;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public List<SavedLocation> getSavedLocation() {
		return savedLocation;
	}

	public void setSavedLocation(List<SavedLocation> savedLocation) {
		this.savedLocation = savedLocation;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", city=" + city + ", zipcode=" + zipcode + ", savedLocation=" + savedLocation + "]";
	}
}