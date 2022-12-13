package com.musalasoft.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.entities.restApis.DroneDTO;
import com.musalasoft.entities.restApis.MedicationsReqRes;

@Service
public class DronesService implements IDronesService{

	@Override
	public void addDrone(DroneDTO droneDto) {
		
	}
	
	
	 private Drones dtoToEntity(DroneDTO droneDto){
	        Drones drone= Drones.builder()
	        		.serialNumber(droneDto.getSerialNumber())
	        		.weightLimit(droneDto.getWeightLimit()).build();
	        return drone;
	    }


	@Override
	public List<Medication> getDroneMedication(long droneId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getDroneBattaryLevel(long droneId) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public List<Drones> getAvailableDrones() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void loadDroneMedication(long droneId, MedicationsReqRes dto) {
		// TODO Auto-generated method stub
		
	}

}
