package pl.lucasjasek.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TemperatureSensor extends AbstractEntity {

    private String tempSensorId;
    private int value;
    private int minValue;
    private int maxValue;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public TemperatureSensor() {
    }

    public String getTempSensorId() {
        return tempSensorId;
    }

    public void setTempSensorId(String tempSensorId) {
        this.tempSensorId = tempSensorId;
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

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}