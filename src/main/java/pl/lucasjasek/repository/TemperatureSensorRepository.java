package pl.lucasjasek.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.lucasjasek.model.TemperatureSensor;

@Repository
public interface TemperatureSensorRepository extends CrudRepository<TemperatureSensor, Integer> {

    TemperatureSensor findByTempSensorId(String tempSensorId);
}