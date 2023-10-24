package weather_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:secrets.properties")
public class AppConfig {
	
	@Autowired
	Environment env;
	
	public String getKey() {
		return env.getProperty("weather_api_key");
	}

}
