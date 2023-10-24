package weather_app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import weather_app.model.User;
import weather_app.repository.UserRepository;

@Service
@Tag(name = "User Details Service", description = "The service for user details business logic")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repo;
	
	// method will by called by Spring Security when a request comes in
	// credentials (username + password) passed through the request will be loaded in
	// username will be passed to this method (as an argument), then will call the
	// UserRepository in order to find a user with that username
	// As long as this user is found, User info will be passed to a UserDetails
	// object and returned

	@Operation(summary = "Load user by username", description = "Loads a user based on username")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> customerFound = repo.findByUsername(username);
		
		// if username doesn't exist in the table, throw exception...
		if(customerFound.isEmpty()) {
			throw new UsernameNotFoundException("Username of " + username + " not found");
		}
		
		// ...but if it does, then we want to return the user we found
		return new MyUserDetails( customerFound.get() );
	}

}