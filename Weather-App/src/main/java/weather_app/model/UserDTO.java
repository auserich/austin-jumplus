package weather_app.model;

import java.util.List;

import weather_app.util.Constants;

public class UserDTO {
	
	private Integer id;
	private String username;
	private int toleranceChange;
	private List<SavedLocation> savedLocation;
	
	public UserDTO() {}
	public UserDTO(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.toleranceChange = Constants.TOLERANCES[user.getTolerance().ordinal()];
		this.savedLocation = user.getSavedLocation();
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
	public int getToleranceChange() {
		return toleranceChange;
	}
	public void setToleranceChange(int toleranceChange) {
		this.toleranceChange = toleranceChange;
	}
	public List<SavedLocation> getSavedLocation() {
		return savedLocation;
	}
	public void setSavedLocation(List<SavedLocation> savedLocation) {
		this.savedLocation = savedLocation;
	}
	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", toleranceChange=" + toleranceChange
				+ ", trackedZipcode=" + savedLocation + "]";
	}

}
