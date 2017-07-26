package pl.lucasjasek.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.lucasjasek.model.PressureSensor;
import pl.lucasjasek.model.TemperatureSensor;
import pl.lucasjasek.model.request.SensorContent;
import pl.lucasjasek.service.EngineService;
import pl.lucasjasek.service.SensorService;

import java.util.Collections;
import java.util.List;

@RestController
public class EngineController {

    private Logger logger = LoggerFactory.getLogger(EngineController.class);

    private final EngineService engineService;
    private final SensorService sensorService;

    @Autowired
    public EngineController(EngineService engineService, SensorService sensorService) {
        this.engineService = engineService;
        this.sensorService = sensorService;
    }

    @GetMapping("/engines")
    public List<String> incorrectlyEngines(@RequestParam(value = "pressure_threshold") Integer pressureTreshold,
                                             @RequestParam(value = "temp_threshold") Integer tempTreshold) {

        List<String> incorrectlyEngines = engineService.getIncorrectlyEngine(pressureTreshold, tempTreshold);

        if (incorrectlyEngines.isEmpty()) {
            logger.info("*****-----     all engines work properly    -----*****");
            return Collections.emptyList();
        }

        return incorrectlyEngines;
    }

    @PostMapping("/sensors/{sensorId}")
    public ResponseEntity<?> updateSensorValuePost(@PathVariable int sensorId,
                                                   @RequestBody SensorContent sensorContent){

        if(updateSensorValueIfPossible(sensorId, sensorContent)){
            logger.info("***   HttpStatus.OK   ***");

            return ResponseEntity.status(HttpStatus.OK).build();
        }

        logger.info("***   HttpStatus.NOT_ACCEPTABLE   ***");

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

   private boolean updateSensorValueIfPossible(int sensorId, SensorContent sensorContent) {

       if(!sensorService.isSensorExists(String.valueOf(sensorId))){
           logger.info("***   sensor not exist!   ***");

           return false;
       }

       String sensorType = sensorService.getSensorTypeById(String.valueOf(sensorId));

       switch (sensorType) {
           case "pressure" :
               PressureSensor pressureSensor = sensorService.getPressureSensorById(String.valueOf(sensorId));
               boolean resultForPress = sensorService.isUpdatePressureSensorPossible(pressureSensor, sensorContent);

               return resultForPress && sensorService.updatePressureSensorValue(pressureSensor, sensorContent);

           case "temperature" :
               TemperatureSensor temperatureSensor = sensorService.getTemperatureSensorById(String.valueOf(sensorId));
               boolean resultForTemp = sensorService.isUpdateTemperatureSensorPossible(temperatureSensor, sensorContent);

               return resultForTemp && sensorService.updateTemperatureSensorValue(temperatureSensor, sensorContent);

           default:
               return false;
       }
   }
}