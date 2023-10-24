package weather_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Weather Application", version = "1.0",
		description = "Application that allows users to store locations on their profile and"
				+ "presents the weather/forecast for that location while providing helpful messages"
				+ "based on the weather"))
public class WeatherOutfitPlannerApplication {
	
	static {
		System.getProperties().setProperty("weather.configuration.filename", "secrets.properties");
	}

	public static void main(String[] args) {
		SpringApplication.run(WeatherOutfitPlannerApplication.class, args);
	}
}