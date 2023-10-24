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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import weather_app.exception.ResourceNotFoundException;
import weather_app.model.SavedLocation;
import weather_app.model.User;
import weather_app.service.SavedLocationService;
import weather_app.service.UserService;

@RestController
@RequestMapping("/api")
@Tag(name = "Saved Location API", description = "The API for managing saved locations")
public class SavedLocationController {
	
	@Autowired
	SavedLocationService service;
	
	@Autowired
	UserService userService;
	
	@Operation(summary = "Get all saved locations", description = "Returns a list of all saved locations")
	@ApiResponse(responseCode = "200", description = "Saved locations retrieved")
	@GetMapping("/saved_location")
	public List<SavedLocation> getSavedLocations() {
		
		return service.getAllSavedLocations();
	}
	
	@Operation(summary = "Get a saved location by id", description = "Returns a saved location based on id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Saved location retrieved"),
			@ApiResponse(responseCode = "404", description = "Saved location not found")
	})
	@GetMapping("/saved_location/{id}")
	public ResponseEntity<SavedLocation> getSavedLocationById(int id) throws ResourceNotFoundException {
		
		SavedLocation found = service.getSavedLocationById(id);
		
		return ResponseEntity.status(200).body(found);
	}
	
	@Operation(summary = "Create a saved location", description = "Creates a saved location based on id and current user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Saved location created"),
			@ApiResponse(responseCode = "404", description = "Saved location not found")
	})
	@PostMapping("/saved_location/{locationId}")
	public ResponseEntity<SavedLocation> createSavedLocation(@PathVariable int locationId) throws ResourceNotFoundException {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User found = userService.getUserByUsername(username);
		
		System.out.println(found);
		SavedLocation created = service.createSavedLocation(found, locationId);
		
		return ResponseEntity.status(200).body(created);
	}
	
	@Operation(summary = "Delete a saved location", description = "Deletes a saved location based on id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Saved location deleted"),
			@ApiResponse(responseCode = "404", description = "Saved location not found")
	})
	@DeleteMapping("/saved_location/{id}")
	public ResponseEntity<SavedLocation> deleteSavedLocation(@PathVariable int id) throws ResourceNotFoundException {
		
		SavedLocation deleted = service.deleteSavedLocation(id);
		
		return ResponseEntity.status(200).body(deleted);
	}
}