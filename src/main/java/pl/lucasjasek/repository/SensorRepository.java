package pl.lucasjasek.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.lucasjasek.model.Sensor;

@Repository
public interface SensorRepository extends CrudRepository<Sensor, Integer> {

    @Query("select s.type from Sensor s where s.sensorId = :sensorId")
    String findSensorTypeBySensorId(@Param("sensorId") String sensorId);

    boolean existsBySensorId(String sensorId);
}