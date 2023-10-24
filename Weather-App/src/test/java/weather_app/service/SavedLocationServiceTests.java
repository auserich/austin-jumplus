package weather_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import weather_app.exception.ResourceNotFoundException;
import weather_app.model.Location;
import weather_app.model.SavedLocation;
import weather_app.model.User;
import weather_app.repository.LocationRepository;
import weather_app.repository.SavedLocationRepository;
import weather_app.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class SavedLocationServiceTests {

	@Mock
	private SavedLocationRepository repo;
	
	@Mock
	private UserRepository userRepo;
	
	@Mock
	private LocationRepository locationRepo;
	
	@InjectMocks
	private SavedLocationService service;
	
	@Test
	public void testGetAllLocations() {
		
		List<Location> locations = new ArrayList<>();
		locations.add(new Location(1, "Seattle", "98101", null));
		locations.add(new Location(1, "Chicago", "60007", null));
		
		when(locationRepo.findAll()).thenReturn(locations);
		
		List<Location> result = service.getAllLocations();
		
		assertEquals(locations, result);
		
		verify(locationRepo, times(1)).findAll();
		verifyNoMoreInteractions(locationRepo);
	}
	
	@Test
	public void testCreateLocationSuccessResourceFound() throws Exception {
		
		Location location = new Location(1, "Seattle", "98101", null);
		
		when(locationRepo.getLocationByZipcode(location.getZipcode())).thenReturn(Optional.of(location));
		
		Location result = service.createLocation(location.getZipcode());
		
		assertEquals(location, result);
		
		verify(locationRepo, times(1)).getLocationByZipcode(location.getZipcode());
		verifyNoMoreInteractions(locationRepo);
	}
	
	@Test
	public void testCreateLocationSuccessCreatesLocation() throws Exception {
		
		Location location = new Location(1, "Seattle", "98101", null);
		
		when(locationRepo.getLocationByZipcode(location.getZipcode())).thenReturn(Optional.empty());
		
		Location result = service.createLocation(location.getZipcode());
		
		assertNull(result);
	}
	
	@Test
	public void testGetAllSavedLocationsSuccess() {
		
		List<SavedLocation> savedLocations = new ArrayList<>();
		savedLocations.add(new SavedLocation(
			1,
			new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE),
			new Location(1, "Seattle", "98101", null)));
		savedLocations.add(new SavedLocation(
			1,
			new User(1, "Conner", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE),
			new Location(1, "Chicago", "60007", null)));
		
		when(repo.findAll()).thenReturn(savedLocations);
		
		List<SavedLocation> result = service.getAllSavedLocations();
		
		assertEquals(savedLocations, result);
		
		verify(repo, times(1)).findAll();
		verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void testGetSavedLocationById() throws Exception {
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		Location location = new Location(1, "Seattle", "98101", null);
		SavedLocation savedLocation = new SavedLocation(1, user, location);
		
		when(repo.findById(savedLocation.getId())).thenReturn(Optional.of(savedLocation));
		
		SavedLocation result = service.getSavedLocationById(location.getId());
		
		assertEquals(savedLocation, result);
		
		verify(repo, times(1)).findById(savedLocation.getId());
		verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void testGetSavedLocationByIdResourceNotFound() {
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		Location location = new Location(1, "Seattle", "98101", null);
		SavedLocation savedLocation = new SavedLocation(1, user, location);
		
		when(repo.findById(savedLocation.getId())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.getSavedLocationById(savedLocation.getId()));
	}
	
	@Test
	public void testCreateSavedLocationSuccess() throws Exception {
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		Location location = new Location(1, "Seattle", "98101", null);
		SavedLocation savedLocation = new SavedLocation(1, user, location);
		
		when(locationRepo.findById(location.getId())).thenReturn(Optional.of(location));
		when(repo.save(Mockito.any(SavedLocation.class))).thenReturn(savedLocation);
		
		SavedLocation result = service.createSavedLocation(user, location.getId());
		
		assertEquals(savedLocation, result);
		
		verify(locationRepo, times(1)).findById(location.getId());
		verifyNoMoreInteractions(locationRepo);
		verify(repo, times(1)).save(Mockito.any(SavedLocation.class));
		verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void testCreateSavedLocationResourceNotFound() {
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		Location location = new Location(1, "Seattle", "98101", null);
		SavedLocation savedLocation = new SavedLocation(1, user, location);
		
		when(locationRepo.findById(location.getId())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.createSavedLocation(user, location.getId()));
	}
	
	@Test
	public void testDeleteSavedLocationSuccess() throws Exception {
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		Location location = new Location(1, "Seattle", "98101", null);
		SavedLocation savedLocation = new SavedLocation(1, user, location);
		
		when(repo.findById(savedLocation.getId())).thenReturn(Optional.of(savedLocation));
		doNothing().when(repo).deleteById(savedLocation.getId());
		
		SavedLocation result = service.deleteSavedLocation(savedLocation.getId());
		
		assertEquals(savedLocation, result);
		
		verify(repo, times(1)).findById(savedLocation.getId());
		verify(repo, times(1)).deleteById(savedLocation.getId());
		verifyNoMoreInteractions(repo);
	}
	
	@Test
	public void testDeleteSavedLocationResourceNotFound() {
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		Location location = new Location(1, "Seattle", "98101", null);
		SavedLocation savedLocation = new SavedLocation(1, user, location);
		
		when(repo.findById(savedLocation.getId())).thenReturn(Optional.empty());
		
		assertThrows(ResourceNotFoundException.class, () -> service.deleteSavedLocation(savedLocation.getId()));
	}
}
