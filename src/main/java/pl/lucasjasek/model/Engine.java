package pl.lucasjasek.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Engine extends AbstractEntity{

    private String engineId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pressureSensor_id")
    private PressureSensor pressureSensor;

    @OneToMany(mappedBy = "engine", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TemperatureSensor> temperatureSensors;

    public Engine() {
    }

    public String getEngineId() {
        return engineId;
    }

    public void setEngineId(String engineId) {
        this.engineId = engineId;
    }

    public PressureSensor getPressureSensor() {
        return pressureSensor;
    }

    public void setPressureSensor(PressureSensor pressureSensor) {
        this.pressureSensor = pressureSensor;
    }

    public List<TemperatureSensor> getTemperatureSensors() {
        return temperatureSensors;
    }

    public void setTemperatureSensors(List<TemperatureSensor> temperatureSensors) {
        this.temperatureSensors = temperatureSensors;
    }
}