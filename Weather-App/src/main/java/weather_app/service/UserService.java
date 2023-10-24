package weather_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import weather_app.exception.ResourceNotFoundException;
import weather_app.exception.UsernameTakenException;
import weather_app.model.User;
import weather_app.repository.UserRepository;

@Service
@Tag(name = "User Service", description = "The service for user business logic")
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Operation(summary = "Get all users", description = "Returns a list of all users")
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public User getUserById(int id) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User", id);
		}
		
		return found.get();
	}
	
	@Operation(summary = "Get a user by id", description = "Returns a user based on id")
	public User getUserByUsername(String username) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findByUsername(username);
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User", -1);
		}
		
		return found.get();
	}
	
	@Operation(summary = "Creates a user", description = "Creates a user based on request body")
	public User createUser(User user) throws UsernameTakenException {
		
		Optional<User> exists = repo.findByUsername(user.getUsername());
		
		if (!exists.isEmpty()) {
			throw new UsernameTakenException(user);
		}
		
		user.setId(null);
		user.setPassword(encoder.encode(user.getPassword()));
		User created = repo.save(user);
		
		return created;
	}
	
	@Operation(summary = "Updates a user", description = "Updates a user based on request body")
	public User updateUser(User user) throws ResourceNotFoundException {
		
		if (repo.existsById(user.getId())) {
			User updated = repo.save(user);
			return updated;
		}
		
		throw new ResourceNotFoundException("User", user.getId());
	}
	
	@Operation(summary = "Deletes a user", description = "Deletes a user based on id")
	public User deleteUser(int id) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findById(id);
		
		if (found.isEmpty()) {
			throw new ResourceNotFoundException("User", id);
		}
		
		repo.deleteById(id);
		
		return found.get();
	}
}