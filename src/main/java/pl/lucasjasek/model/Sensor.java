package pl.lucasjasek.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;

@Entity
public class Sensor extends AbstractEntity{

    @JsonProperty("id")
    private String sensorId;
    @JsonProperty("master-sensor-id")
    private String masterSensorId;
    private String engine;
    private String type;
    private String name;
    private String location;
    private int value;
    @JsonProperty("min_value")
    private int minValue;
    @JsonProperty("max_value")
    private int maxValue;

    public Sensor() {
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getMasterSensorId() {
        return masterSensorId;
    }

    public void setMasterSensorId(String masterSensorId) {
        this.masterSensorId = masterSensorId;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}