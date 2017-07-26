package pl.lucasjasek.model;

import javax.persistence.Entity;

@Entity
public class PressureSensor extends AbstractEntity {

    private String pressSensorId;
    private int value;
    private int minValue;
    private int maxValue;

    public PressureSensor() {
    }

    public String getPressSensorId() {
        return pressSensorId;
    }

    public void setPressSensorId(String pressSensorId) {
        this.pressSensorId = pressSensorId;
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