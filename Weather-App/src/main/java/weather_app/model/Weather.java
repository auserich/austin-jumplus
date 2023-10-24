package weather_app.model;

public class Weather {

	private double temp;
	
	private Boolean precipitation;

	public Weather(double temp, Boolean precipitation) {
		super();
		this.temp = temp;
		this.precipitation = precipitation;
	}

	public double getTemp() {
		return temp;
	}

	public Boolean isPrecipitation() {
		return precipitation;
	}

	public void setPrecipitation(boolean precipitation) {
		this.precipitation = precipitation;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public Weather() {
		super();
	}
	
	
	
}
