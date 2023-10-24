package weather_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import weather_app.model.Weather;
import weather_app.service.MessageService;

@RestController
@RequestMapping("/api")
@Tag(name = "Message API", description = "The API for managing Messages")
public class MessageController {
	
	@Autowired
	MessageService service;
	
	@Operation(summary = "Get a message by weather", description = "Returns a message based on weather")
	@PostMapping("/message")
	public String getMessage(@RequestBody Weather weather) {
		
		return service.getMessage(weather);
	}
}