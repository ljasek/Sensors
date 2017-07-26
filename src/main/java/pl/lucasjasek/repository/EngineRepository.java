package pl.lucasjasek.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.lucasjasek.model.Engine;

import java.util.List;

@Repository
public interface EngineRepository extends CrudRepository<Engine, Integer> {

    @Query("select e from Engine e where e.pressureSensor.value <= :pressureTreshold")
    List<Engine> getEnginesWithIncorrectlyPressure(@Param("pressureTreshold") int pressureTreshold);
}