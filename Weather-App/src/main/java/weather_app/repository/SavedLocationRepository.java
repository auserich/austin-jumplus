package weather_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import weather_app.model.SavedLocation;

@Repository
public interface SavedLocationRepository extends JpaRepository<SavedLocation, Integer> {

//	@Query(value = "INSERT * FROM saved_location WHERE user_id = ?1 AND location_id = ?2", nativeQuery = true)
//	public Optional<SavedLocation> saveLocation(int userId, int locationId);
	
	@Query(value = "SELECT * FROM saved_location WHERE user_id = ?1", nativeQuery = true)
	public List<SavedLocation> getUserLocations(int userId);
	
}