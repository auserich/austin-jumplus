package weather_app.service.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import weather_app.controller.SavedLocationController;
import weather_app.exception.ResourceNotFoundException;
import weather_app.model.Location;
import weather_app.model.SavedLocation;
import weather_app.model.User;
import weather_app.service.MyUserDetailsService;
import weather_app.service.SavedLocationService;
import weather_app.util.JwtUtil;

@WebMvcTest(MockitoExtension.class)
public class SavedLocationControllerTests {

	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private SavedLocationService service;
	
	@InjectMocks
	private SavedLocationController controller;
	
	@MockBean
	private MyUserDetailsService userDetailService;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Test
	public void testGetSavedLocation() throws Exception {
		
		String uri = STARTING_URI + "/saved_location";
		
		List<SavedLocation> savedLocations = new ArrayList<>();
		savedLocations.add(new SavedLocation(
			1,
			new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE),
			new Location(1, "Seattle", "98101", null)));
		savedLocations.add(new SavedLocation(
			1,
			new User(1, "Conner", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE),
			new Location(1, "Chicago", "60007", null)));
		
		when(service.getAllSavedLocations()).thenReturn(savedLocations);
		
		mvc.perform(get(uri)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.length()").value(savedLocations.size()))
			.andExpect(jsonPath("$[0].id").value(savedLocations.get(0).getId()))
			.andExpect(jsonPath("$[0].user").value(savedLocations.get(0).getUser()))
			.andExpect(jsonPath("$[0].location").value(savedLocations.get(0).getLocation()))
			.andExpect(jsonPath("$[1].id").value(savedLocations.get(1).getId()))
			.andExpect(jsonPath("$[1].user").value(savedLocations.get(1).getUser()))
			.andExpect(jsonPath("$[1].location").value(savedLocations.get(1).getLocation()));
		
		verify(service, times(1)).getAllSavedLocations();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testGetSavedLocationById() throws Exception {
		
		String uri = STARTING_URI + "/saved_location/{id}";
		
		int id = 1;
		SavedLocation savedLocation = new SavedLocation(
			id,
			new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE),
			new Location(1, "Seattle", "98101", null));
		
		when(service.getSavedLocationById(savedLocation.getId())).thenReturn(savedLocation);
		
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
