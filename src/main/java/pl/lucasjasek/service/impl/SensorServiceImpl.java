package pl.lucasjasek.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lucasjasek.model.PressureSensor;
import pl.lucasjasek.model.TemperatureSensor;
import pl.lucasjasek.model.request.SensorContent;
import pl.lucasjasek.repository.PressureSensorRepository;
import pl.lucasjasek.repository.SensorRepository;
import pl.lucasjasek.repository.TemperatureSensorRepository;
import pl.lucasjasek.service.SensorService;

@Service
@Transactional
public class SensorServiceImpl implements SensorService {

    private Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);

    private final SensorRepository sensorRepository;
    private final PressureSensorRepository pressureSensorRepository;
    private final TemperatureSensorRepository temperatureSensorRepository;

    @Autowired
    public SensorServiceImpl(SensorRepository sensorRepository, PressureSensorRepository pressureSensorRepository,
                             TemperatureSensorRepository temperatureSensorRepository) {
        this.sensorRepository = sensorRepository;
        this.pressureSensorRepository = pressureSensorRepository;
        this.temperatureSensorRepository = temperatureSensorRepository;
    }

    @Override
    public String getSensorTypeById(String sensorId) {
        return sensorRepository.findSensorTypeBySensorId(sensorId);
    }

    @Override
    public PressureSensor getPressureSensorById(String sensorId) {
        return pressureSensorRepository.findByPressSensorId(sensorId);
    }

    @Override
    public TemperatureSensor getTemperatureSensorById(String sensorId) {
        return temperatureSensorRepository.findByTempSensorId(sensorId);
    }

    @Override
    public boolean isSensorExists(String sensorId) {
        return sensorRepository.existsBySensorId(sensorId);
    }

    @Override
    public boolean updatePressureSensorValue(PressureSensor pressureSensor, SensorContent sensorContent) {

        switch (sensorContent.getOperation()){
            case "set" :
                pressureSensor.setValue(Integer.parseInt(sensorContent.getValue()));
                pressureSensorRepository.save(pressureSensor);

                logger.info("*** SET value for Pressure Sensor ***");

                return true;

            case "increment" :
                pressureSensor.setValue(pressureSensor.getValue() + Integer.parseInt(sensorContent.getValue()));
                pressureSensorRepository.save(pressureSensor);

                logger.info("*** INCREMENT value for Pressure Sensor ***");

                return true;

            case "decrement" :
                pressureSensor.setValue(pressureSensor.getValue() - Integer.parseInt(sensorContent.getValue()));
                pressureSensorRepository.save(pressureSensor);

                logger.info("*** DECREMENT value for Pressure Sensor ***");

                return true;

            default: return false;
        }
    }

    @Override
    public boolean updateTemperatureSensorValue(TemperatureSensor temperatureSensor, SensorContent sensorContent) {

        switch (sensorContent.getOperation()){
            case "set" :
                temperatureSensor.setValue(Integer.parseInt(sensorContent.getValue()));
                temperatureSensorRepository.save(temperatureSensor);

                logger.info("*** SET value for Temperature Sensor ***");

                return true;

            case "increment" :
                temperatureSensor.setValue(temperatureSensor.getValue() + Integer.parseInt(sensorContent.getValue()));
                temperatureSensorRepository.save(temperatureSensor);

                logger.info("*** INCREMENT value for Temperature Sensor ***");

                return true;

            case "decrement" :
                temperatureSensor.setValue(temperatureSensor.getValue() - Integer.parseInt(sensorContent.getValue()));
                temperatureSensorRepository.save(temperatureSensor);

                logger.info("*** DECREMENT value for Temperature Sensor ***");

                return true;

            default: return false;
        }
    }

    @Override
    public boolean isUpdatePressureSensorPossible(PressureSensor sensor, SensorContent sensorContent) {

        switch (sensorContent.getOperation())
        {
            case "increment" :
                return sensor.getMaxValue() >= (sensor.getValue() + Integer.parseInt(sensorContent.getValue()));

            case "decrement" :
                return sensor.getMinValue() <= (sensor.getValue() - Integer.parseInt(sensorContent.getValue()));

            case "set" :
                return (Integer.parseInt(sensorContent.getValue()) >= sensor.getMinValue()) &&
                        (Integer.parseInt(sensorContent.getValue()) <= sensor.getMaxValue());

            default: return false;
        }
    }

    @Override
    public boolean isUpdateTemperatureSensorPossible(TemperatureSensor sensor, SensorContent sensorContent) {

        switch (sensorContent.getOperation())
        {
            case "increment" :
                return sensor.getMaxValue() >= (sensor.getValue() + Integer.parseInt(sensorContent.getValue()));

            case "decrement" :
                return sensor.getMinValue() <= (sensor.getValue() - Integer.parseInt(sensorContent.getValue()));

            case "set" :
                return (Integer.parseInt(sensorContent.getValue()) >= sensor.getMinValue()) && (Integer.parseInt(sensorContent.getValue()) <= sensor.getMaxValue());

            default: return false;
        }
    }
}