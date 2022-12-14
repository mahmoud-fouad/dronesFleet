package com.musalasoft.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
	
	
	
	@Value("${fleet.capacity}")
	private int fleetCapacity;
	
	FleetConfigurations fleetConfigurations;
	
	public DronesService(DroneRepository droneRepository,FleetConfigurations fleetConfigurations) {
		this.droneRepository=droneRepository;
		this.fleetConfigurations=fleetConfigurations;
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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void loadDroneMedication(long droneId, MedicationsReqRes dto) {
		// TODO Auto-generated method stub
		
	}
	
	private Drones getDroneById(long id){
		return droneRepository.findById(id).orElseThrow(()-> new FleetExceptions(FleetExceptions.notFoundDrone));
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
