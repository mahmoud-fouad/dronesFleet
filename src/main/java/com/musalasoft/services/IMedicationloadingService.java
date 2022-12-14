package com.musalasoft.services;

import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.entities.restApis.MedicationDTO;

public interface IMedicationloadingService {
	
	public boolean loadMedication(Drones drones, Medication medication);
	
	public Medication getMedicateEntityFromDto(MedicationDTO dto);
	
	public int getTotalLoad(MedicationDTO dto);
	
	public void unpack(Drones drone);

}
