package com.musalasoft.services;

import org.springframework.stereotype.Service;

import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.entities.restApis.MedicationDTO;

//this service simulate the the drone communication services
@Service
public class DroneCommunicationService implements IDroneCommunicationService{

	@Override
	public boolean loadMedication(Drones drones, Medication medication) {
		return true;
	}

	@Override
	public Medication getMedicateEntityFromDto(MedicationDTO dto) {
		return Medication.builder().code(dto.getCode()).imageURL(dto.getImage()).name(dto.getName()).weight(dto.getWeight()).build();
	}

	// process to unpack the loaded medicines in case of loading failure
	@Override
	public void unpack(Drones drone) {
		// TODO Auto-generated method stub
	}

	// communicate with the drone to get updated battery level
	@Override
	public int getDroneBattary(Drones drone) {
		if(drone.getBatteryCapacityPercentage()==0)
		return 0;
		else
			// mock that batteries level decreasing by 5 % each time
			return drone.getBatteryCapacityPercentage()-5 ;
	}

}
