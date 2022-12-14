package com.musalasoft.services;

import org.springframework.stereotype.Service;

import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.entities.restApis.MedicationDTO;

//this service simulate the loading process
@Service
public class MedicationLoadingService implements IMedicationloadingService{

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

	@Override
	public int getTotalLoad(MedicationDTO dto) {
		// TODO Auto-generated method stub
		return 0;
	}

}
