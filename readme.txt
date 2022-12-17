this is spring boot application v2.0.2.RELEASE created on eclipse -STS- and java 1.8 regarding connecting to drones to upload and deliver medicines
the project compile as jar by maven
it uses h2 (in-memory) db which its data loaded from data.sql file in resources folder.
to run the project inside your IDE you need to import the project then run com.musalasoft.DronesFleetApplication
or build it by maven as jar file.

the project has 2 environmental variables 1- fleet.capacity for the fleet drones count ,2- fleet.updateDroneBatteryInterval for the update drones battery scheduler interval.

The project has test cases cover all system business cases , validation , db queries and apis .
As communications with the drone is outside the scope The DroneCommunicationService is mocking for potential required services

The project produces 4 apis 
1- "/drone/{droneID}/medications" get http method to retrieve specific drone medications
2- "/drone/{droneID}/medications" post http method to upload medications into specific drone 
3- "/registerDone"  post http method to add/register new drone into the fleet
4- "/drone/{droneID}/batteryLevel" get http method to retrieve specific drone battery level
5- "/getAvailableDrones" get http method to retrieve all available drones for loading

you can access the project using http on port 8080

also there is a scheduler to update all registered drones battery level and audit each drone updated batter level