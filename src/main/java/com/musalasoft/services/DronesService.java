package com.musalasoft.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.musalasoft.FleetConfigurations;
import com.musalasoft.entities.DroneModel;
import com.musalasoft.entities.DroneState;
import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.entities.restApis.DroneDTO;
import com.musalasoft.entities.restApis.MedicationsReqRes;
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DronesService implements IDronesService{
	
	private DroneRepository droneRepository;
	
	private FleetConfigurations fleetConfigurations;
	
	private IMedicationloadingService medicationloadingService; 
	
	
	public DronesService(DroneRepository droneRepository,FleetConfigurations fleetConfigurations,IMedicationloadingService medicationloadingService) {
		this.droneRepository=droneRepository;
		this.fleetConfigurations=fleetConfigurations;
		this.medicationloadingService=medicationloadingService;
	}
	
	
	@Override
	public void addDrone(DroneDTO droneDto) {
		long dronesCount = droneRepository.count();
		
		if(dronesCount==fleetConfigurations.getCapacity())
			throw new FleetExceptions(FleetExceptions.fullFleet);
		
		Drones drone = dtoToEntity(droneDto);
		drone.setBatteryCapacityPercentage(100);
		drone.setState(DroneState.IDLE);
		
		droneRepository.save(drone);
	}
	
	
	


	@Override
	public List<Medication> getDroneMedication(long droneId) {
		return getDroneById(droneId).getLoadedMedications();
	}


	@Override
	public int getDroneBattaryLevel(long droneId) {
		return getDroneById(droneId).getBatteryCapacityPercentage();
	}


	@Override
	public List<Drones> getAvailableDrones() {
		//select drones which in idle state and its battery more Than 25;
		return droneRepository.getDronesByStatesandBatteryLevel(DroneState.IDLE,25);
	}


	@Override
	public void loadDroneMedication(long droneId, List<Medication> medications) {
		
		Drones drones = getDroneById(droneId);
		
		//check if the drone is available or not by check on its state and battery level
		if(drones.getState()!=DroneState.IDLE || drones.getBatteryCapacityPercentage() <25)
			throw new FleetExceptions(FleetExceptions.unAvailbeDrone);
		
		
		if(medications.isEmpty())
			throw new FleetExceptions(FleetExceptions.emptyPack);
		// begin check on load weight
		int totalLoad =0;
		for(Medication med : medications)
			totalLoad+= med.getWeight();
		
		if(drones.getWeightLimit() < totalLoad)
			throw new FleetExceptions(FleetExceptions.overLoadedWeight);
		
		// set drone state as loading
		drones.setState(DroneState.LOADING);
		droneRepository.save(drones);
		
		for(Medication med: medications){
			
			// call loading service to process and if error happened unpack and throw loading Error
			if(!medicationloadingService.loadMedication(drones, med)){
				medicationloadingService.unpack(drones);
				drones.setState(DroneState.IDLE);
				droneRepository.save(drones);
				throw new FleetExceptions(FleetExceptions.loadingError,HttpStatus.EXPECTATION_FAILED);
			}
			//add the medicine after successful load
			else
				drones.getLoadedMedications().add(med);
		}
		//after successful load all medicines drones states is loaded 
		drones.setState(DroneState.LOADED);
		droneRepository.save(drones);
		
	}
	
	private Drones getDroneById(long id){
		Drones drone= droneRepository.findById(id).orElseThrow(()-> new FleetExceptions(FleetExceptions.notFoundDrone));
		if(drone.getLoadedMedications()==null)
			drone.setLoadedMedications(new ArrayList<Medication>());
		return drone;
	}
	
	 private Drones dtoToEntity(DroneDTO droneDto){
		 try{
	        Drones drone= Drones.builder()
	        		.serialNumber(droneDto.getSerialNumber())
	        		.weightLimit(droneDto.getWeightLimit())
	        		.model(DroneModel.valueOf(droneDto.getModel()))
	        		.build();
	        return drone;}
		 catch (IllegalArgumentException e) {
			 log.error("can not map the dto "+e.getMessage());
			 throw new FleetExceptions(FleetExceptions.inCompatableMode);
		}
		 
	    }
	
}
