package pl.lucasjasek.service;

import pl.lucasjasek.model.PressureSensor;
import pl.lucasjasek.model.TemperatureSensor;
import pl.lucasjasek.model.request.SensorContent;

public interface SensorService {

    String getSensorTypeById(String sensorId);

    PressureSensor getPressureSensorById(String sensorId);

    TemperatureSensor getTemperatureSensorById(String sensorId);

    boolean isSensorExists(String sensorId);

    boolean isUpdatePressureSensorPossible(PressureSensor sensor, SensorContent sensorContent);

    boolean isUpdateTemperatureSensorPossible(TemperatureSensor sensor, SensorContent sensorContent);

    boolean updatePressureSensorValue(PressureSensor pressureSensor, SensorContent sensorContent);

    boolean updateTemperatureSensorValue(TemperatureSensor temperatureSensor, SensorContent sensorContent);
}