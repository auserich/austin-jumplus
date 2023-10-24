package weather_app.exception;

import weather_app.model.User;

public class UsernameTakenException extends Exception {

	private static final long serialVersionUID = 1L;

	public UsernameTakenException(User user) {
		super("Username " + user.getUsername() + " already exists");
	}
}