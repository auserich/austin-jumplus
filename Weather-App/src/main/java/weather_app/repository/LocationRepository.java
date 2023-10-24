package weather_app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import weather_app.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	@Query(value = "SELECT * FROM location WHERE zipcode = ?1", nativeQuery=true)
	public Optional<Location> getLocationByZipcode(String zipcode);
}