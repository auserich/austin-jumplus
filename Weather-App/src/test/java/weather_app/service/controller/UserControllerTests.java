package weather_app.service.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import weather_app.controller.UserController;
import weather_app.exception.ResourceNotFoundException;
import weather_app.exception.UsernameTakenException;
import weather_app.model.User;
import weather_app.service.MyUserDetailsService;
import weather_app.service.SavedLocationService;
import weather_app.service.UserService;
import weather_app.util.JwtUtil;

@WebMvcTest(UserController.class)
public class UserControllerTests {

	private static final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private UserService service;
	
	@InjectMocks
	private UserController controller;
	
	@MockBean
	private MyUserDetailsService userDetailService;
	
	@MockBean
	private SavedLocationService savedLocationService;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@Test
	public void testGetAllUsersSuccess() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		List<User> users = new ArrayList<>();
		
		users.add(new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE));
		users.add(new User(2, "Conner", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE));
		
		when(service.getAllUsers()).thenReturn(users);
		
		mvc.perform(get(uri)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.length()").value(users.size()))
			.andExpect(jsonPath("$[0].id").value(users.get(0).getId()))
			.andExpect(jsonPath("$[0].username").value(users.get(0).getUsername()))
			.andExpect(jsonPath("$[0].password").value(users.get(0).getPassword()))
			.andExpect(jsonPath("$[0].role").value(users.get(0).getRole().name()))
			.andExpect(jsonPath("$[0].enabled").value(users.get(0).isEnabled()))
			.andExpect(jsonPath("$[1].id").value(users.get(1).getId()))
			.andExpect(jsonPath("$[1].username").value(users.get(1).getUsername()))
			.andExpect(jsonPath("$[1].password").value(users.get(1).getPassword()))
			.andExpect(jsonPath("$[1].role").value(users.get(1).getRole().name()))
			.andExpect(jsonPath("$[1].enabled").value(users.get(1).isEnabled()));
		
		verify(service, times(1)).getAllUsers();
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testGetUserByIdSuccess() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		User user = new User(id, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.getUserById(id)).thenReturn(user);
		
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(user.getId()))
			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.password").value(user.getPassword()))
			.andExpect(jsonPath("$.role").value(user.getRole().name()))
			.andExpect(jsonPath("$.enabled").value(user.isEnabled()));
		
		verify(service, times(1)).getUserById(user.getId());
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testGetUserByIdResourceNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		when(service.getUserById(id)).thenThrow(new ResourceNotFoundException("User", id));
		
		mvc.perform(get(uri, id)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
		verify(service, times(1)).getUserById(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testCreateUserSuccess() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.createUser(Mockito.any(User.class))).thenReturn(user);
		
		mvc.perform(post(uri)
			.content(user.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(user.getId()))
			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.password").value(user.getPassword()))
			.andExpect(jsonPath("$.role").value(user.getRole().name()))
			.andExpect(jsonPath("$.enabled").value(user.isEnabled()));
		
		verify(service, times(1)).createUser(Mockito.any(User.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void testCreateUserResourceNotFound() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.createUser(Mockito.any(User.class))).thenThrow(new UsernameTakenException(user));
		
		mvc.perform(post(uri)
			.content(user.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isBadRequest());
		
		verify(service, times(1)).createUser(Mockito.any(User.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void updateUserSuccess() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		User updated = new User(1, "Conner", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.updateUser(Mockito.any(User.class))).thenReturn(updated);
		
		mvc.perform(put(uri)
			.content(user.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(updated.getId()))
			.andExpect(jsonPath("$.username").value(updated.getUsername()))
			.andExpect(jsonPath("$.password").value(updated.getPassword()))
			.andExpect(jsonPath("$.role").value(user.getRole().name()))
			.andExpect(jsonPath("$.enabled").value(updated.isEnabled()));
		
		verify(service, times(1)).updateUser(Mockito.any(User.class));
		verifyNoMoreInteractions(service);
		
	}
	
	@Test
	public void updateUserResourceNotFound() throws Exception {
		
		String uri = STARTING_URI + "/user";
		
		User user = new User(1, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.updateUser(Mockito.any(User.class))).thenThrow(new ResourceNotFoundException("User", user.getId()));
		
		mvc.perform(put(uri)
			.content(user.toJson())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
		verify(service, times(1)).updateUser(Mockito.any(User.class));
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void deleteUserSuccess() throws Exception {
		
		String uri = STARTING_URI + "/user/{id}";
		int id = 1;
		
		User user = new User(id, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.deleteUser(user.getId())).thenReturn(user);
		
		mvc.perform(delete(uri, id)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(user.getId()))
			.andExpect(jsonPath("$.username").value(user.getUsername()))
			.andExpect(jsonPath("$.password").value(user.getPassword()))
			.andExpect(jsonPath("$.role").value(user.getRole().name()))
			.andExpect(jsonPath("$.enabled").value(user.isEnabled()));
		
		verify(service, times(1)).deleteUser(id);
		verifyNoMoreInteractions(service);
	}
	
	@Test
	public void deleteUserResourceNotFound() throws Exception {
		
		String uri = STARTING_URI + "/user/{id}";
		int id = 1;
		
		User user = new User(id, "Mitch", "pw123", User.Role.ROLE_USER, true, null, User.Tolerance.MODERATE);
		
		when(service.deleteUser(user.getId())).thenThrow(new ResourceNotFoundException("User", id));
		
		mvc.perform(delete(uri, id)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.with(SecurityMockMvcRequestPostProcessors.jwt()))
			.andDo(print())
			.andExpect(status().isNotFound());
		
		verify(service, times(1)).deleteUser(id);
		verifyNoMoreInteractions(service);
	}
}