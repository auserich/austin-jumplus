package weather_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import weather_app.exception.ResourceNotFoundException;
import weather_app.exception.UsernameTakenException;
import weather_app.model.SavedLocation;
import weather_app.model.User;
import weather_app.model.UserDTO;
import weather_app.service.SavedLocationService;
import weather_app.service.UserService;

@RestController
@RequestMapping("/api")
@Tag(name = "User API", description = "The API for managing users")
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	SavedLocationService locationService;
	
	@Operation(summary = "Get all users", description = "Returns a list of all users")
	@ApiResponse(responseCode = "200", description = "Users retrieved")
	@GetMapping("/user")
	public List<User> getAllUsers() {
		
		return service.getAllUsers();
	}
	
	@Operation(summary = "Get a user by id", description = "Returns a user based on id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User retrieved"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) throws ResourceNotFoundException {
		
		User found = service.getUserById(id);
		
		return ResponseEntity.status(200).body(found);
	}
	
	@Operation(summary = "Gets currently logged in user",
			description = "Gets current user via credentials and creates UserDTO if found")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Current user retrieved"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@GetMapping("/user/whoami")
	public ResponseEntity<UserDTO> getCurrentUser() throws ResourceNotFoundException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User found = service.getUserByUsername(username);
		List<SavedLocation> userLocations = locationService.getUserSavedLocations(found);
		found.setSavedLocation(userLocations);
		UserDTO result = new UserDTO(found);
		return ResponseEntity.status(200).body(result);
	}
	
	@Operation(summary = "Creates a user", description = "Creates a user based on request body")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User created"),
			@ApiResponse(responseCode = "400", description = "Username taken")
	})
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) throws UsernameTakenException {
		
		User created = service.createUser(user);
		
		return ResponseEntity.status(200).body(created);
	}
	
	@Operation(summary = "Updates a user", description = "Updates a user based on request body")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@PutMapping("/user")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws ResourceNotFoundException {
		
		User updated = service.updateUser(user);
		
		return ResponseEntity.status(200).body(updated);
	}
	
	@Operation(summary = "Deletes a user", description = "Deletes a user based on id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User deleted"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable int id) throws ResourceNotFoundException {
		
		User deleted = service.deleteUser(id);
		
		return ResponseEntity.status(200).body(deleted);
	}
}