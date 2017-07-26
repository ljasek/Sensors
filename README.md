# Sensors
A service with a REST/HTTP interface that updates sensor values and user requests returns a list of all malfunctioning engines.

> There is a defined list of sensors mounted on different engines. Each engine has exactly one pressure sensor and at least one temperature sensor.
The server should retrieve the input containing a list of sensors from a YAML file located in external resources at URL address.
I assume that the contents of the sensor list file can not be changed while the server is running. The input is in YAML format, for example:
    
    - id: "3142"
      engine: "123"
      type: "pressure"
      name: "Engine 123"
      value: 70
      min_value: 0
      max_value: 120
    - id: "32234"
      master-sensor-id: "3142"
      type: "temperature"
      value: 82
      min_value: -50
      max_value: 150
    - id: "57230"
      master-sensor-id: "3142"
      type: "temperature"
      value: 101
      min_value: 0
      max_value: 273
    - id: "5703"
      engine: "156"
      location: ""
      type: "pressure"
      name: "Engine 156"
      value: 73
      min_value: 0
      max_value: 150
    - id: "89145"
      master-sensor-id: "5703"
      type: "temperature"
      value: 99
      min_value: 0
      max_value: 100
      
      
 > "id" - sensor identifier</br>
 "type" - contains information about sensor type (allowed values are _pressure_ and _temperature_)</br>
 "engine" - the engine identifier assigned to the pressure sensor</br>
 "master-sensor-id" - reference to the master pressure sensor, only temperature sensors include references to the master sensor</br>
 "value" - the initial value of the sensor</br>

## How to run the app:
To run the application, navigate to the project directory, and then execute the command:</br>

    mvn spring-boot:run -Dconfig=https://raw.githubusercontent.com/ljasek/Sensors/master/SensorsData/sensors.yml
> The _-Dconfig_ parameter specifies the URL of the sensor file</br>

After launching the server we can execute the following commands:
##### 1. Download a list of engines that are not working properly:  

    curl -XGET "http://localhost:8080/engines?pressure_threshold=40&temp_threshold=50"
    
> - _pressure_threshold_ - minimum pressure below which we recognize that the engine is malfunctioning</br>
> - _temp_threshold_ - maximum temperature value above which we recognize that the engine is malfunctioning</br></br>
The result returned in response to the HTTP body contains a list of engines whose pressure has fallen below the specified level and at the same time the temperature on at least one sensor has risen above the specified value. 
The result is in the form of a list in JSON format.

##### 2. Updated values for the sensor:

    curl -XPOST "http://localhost:8080/sensors/89145" -H "Content-Type: application/json" -d '{"operation": "increment", "value": "5"}'
    
> where possible values for the operation field are:</br>
> - _set_</br>
> - _increment_</br>
> - _decrement_</br>

## How to run a service as a Docker container using Maven:
To run an application in a Docker container, navigate to the project directory and then execute the command:</br>

Build and push images:

    mvn package docker:build -DpushImage -Dconfig=https://raw.githubusercontent.com/ljasek/Sensors/master/SensorsData/sensors.yml
    
Run:

    docker run -p 8080:8080 -t lucasjasek/sensors
  
