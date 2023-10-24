package weather_app.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import weather_app.model.Message;
import weather_app.model.Weather;
import weather_app.repository.MessageRepository;

@Service
@Tag(name = "Message Service", description = "The service for message business logic")
public class MessageService {
	
	@Autowired
	MessageRepository repo;

	@Operation(summary = "Get a message by weather", description = "Returns a message based on weather")
	public String getMessage(Weather weather) {
		
		List<Message> messages = repo.findAllApplicableMessages(weather.getTemp());
		
		try {
			Random rand = new Random();
			
			Message message = messages.get(rand.nextInt(messages.size()));
			
			return message.getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "If you don't like the weather now, just wait ten minutes.";
	}
}