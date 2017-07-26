package pl.lucasjasek.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.Engine;
import pl.lucasjasek.model.PressureSensor;
import pl.lucasjasek.model.Sensor;
import pl.lucasjasek.model.TemperatureSensor;
import pl.lucasjasek.repository.EngineRepository;
import pl.lucasjasek.repository.SensorRepository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Transactional
public class SaveDataConfiguration {

    private Logger logger = LoggerFactory.getLogger(SaveDataConfiguration.class);

    private final SensorRepository sensorRepository;
    private final EngineRepository engineRepository;
    private final Environment environment;

    @Autowired
    public SaveDataConfiguration(SensorRepository sensorRepository, EngineRepository engineRepository,
                                 Environment environment) {
        this.sensorRepository = sensorRepository;
        this.engineRepository = engineRepository;
        this.environment = environment;
    }

    @PostConstruct
    public void init() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            List<Sensor> sensors = mapper.readValue(new URL(environment.getProperty("config")), new TypeReference<List<Sensor>>() {});

            logger.info("*****-----     save sensor(s) into database    -----*****");
            for (Sensor s : sensors) {
                sensorRepository.save(s);
            }

            List<Engine> engines = sensors.stream()
                    .filter(s -> (s.getEngine() != null) && (!(s.getEngine().equals(""))))
                    .map(s -> {
                        Engine engine = new Engine();
                        PressureSensor pressureSensor = new PressureSensor();

                        engine.setPressureSensor(pressureSensor);
                        engine.setEngineId(s.getEngine());
                        engine.getPressureSensor().setPressSensorId(s.getSensorId());
                        engine.getPressureSensor().setMinValue(s.getMinValue());
                        engine.getPressureSensor().setMaxValue(s.getMaxValue());
                        engine.getPressureSensor().setValue(s.getValue());

                        List<TemperatureSensor> tempSensors = sensors.stream()
                                .filter(t -> engine.getPressureSensor().getPressSensorId().equals(t.getMasterSensorId()))
                                .map(t -> {
                                    TemperatureSensor temperatureSensor = new TemperatureSensor();

                                    temperatureSensor.setTempSensorId(t.getSensorId());
                                    temperatureSensor.setMinValue(t.getMinValue());
                                    temperatureSensor.setMaxValue(t.getMaxValue());
                                    temperatureSensor.setValue(t.getValue());
                                    temperatureSensor.setEngine(engine);

                                    return temperatureSensor;
                                })
                                .collect(Collectors.toList());

                        engine.setTemperatureSensors(tempSensors);

                        return engine;
                    })
                    .collect(Collectors.toList());

            if (engines.size() > 0) {
                logger.info("*****-----     save engine(s) into database    -----*****");
                for (Engine e : engines) {
                    engineRepository.save(e);
                }
            } else {
                throw new RuntimeException("*****-----     engine(s) not found in input data file    -----*****");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}