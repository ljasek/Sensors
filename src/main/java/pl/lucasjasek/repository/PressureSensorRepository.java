package pl.lucasjasek.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.lucasjasek.model.PressureSensor;

@Repository
public interface PressureSensorRepository extends CrudRepository<PressureSensor, Integer> {

    PressureSensor findByPressSensorId(String pressSensorId);
}
