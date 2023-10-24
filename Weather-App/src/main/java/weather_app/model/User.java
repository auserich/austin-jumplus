package weather_app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Role {
		ROLE_USER, ROLE_ADMIN
	}
	
	public static enum Tolerance {
		VERY_COLD, COLD, MODERATE, HOT, VERY_HOT
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	@NotBlank
	private String username;
	
	@Column(nullable = false)
	@NotBlank
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Column(columnDefinition = "boolean default true")
	private boolean enabled; // true/false if user enabled/disabled
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<SavedLocation> savedLocation;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Tolerance tolerance;
	
	public User() {
		super();
	}

	public User(Integer id, @NotBlank String username, @NotBlank String password, Role role, boolean enabled,
			List<SavedLocation> savedLocation, Tolerance tolerance) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
		this.savedLocation = savedLocation;
		this.tolerance = tolerance;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<SavedLocation> getSavedLocation() {
		return savedLocation;
	}

	public void setSavedLocation(List<SavedLocation> savedLocation) {
		this.savedLocation = savedLocation;
	}
	
	public Tolerance getTolerance() {
		return tolerance;
	}

	public void setTolerance(Tolerance tolerance) {
		this.tolerance = tolerance;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", enabled="
				+ enabled + ", savedLocation=" + savedLocation + ", tolerance=" + tolerance + "]";
	}

	public String toJson() {
		return "{\"id\" : " + id
				+ ", \"username\" : \"" + username + "\""
				+ ", \"password\" : \"" + password + "\""
				+ ", \"role\" : \"" + role + "\""
				+ ", \"enabled\" : \"" + enabled + "\"}";
	}
}