package com.musalasoft.services;

import java.util.List;

import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.entities.restApis.DroneDTO;
import com.musalasoft.entities.restApis.MedicationsReqRes;

public interface IDronesService {
	
	public void addDrone(DroneDTO droneDto);
	
	public List<Medication> getDroneMedication(long droneId);
	
	public int getDroneBattaryLevel(long droneId);
	
	public List<Drones> getAvailableDrones();
	
	public void loadDroneMedication(long droneId , MedicationsReqRes dto);

}
