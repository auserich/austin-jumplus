package weather_app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, unique = true)
	@NotBlank
	private String name;
	
	@Column(nullable = false)
	@NotBlank
	private String message;
	
	@Column
	private Integer min_temp;
	
	@Column
	private Integer max_temp;
	
	@Column
	private Boolean precipitation;

	public String getMessage() {
		return message;
	}

}
