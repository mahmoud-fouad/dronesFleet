package com.musalasoft;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musalasoft.entities.Audit;
import com.musalasoft.entities.Drones;
import com.musalasoft.services.IDroneCommunicationService;
import com.musalasoft.services.repositories.AuditRepository;
import com.musalasoft.services.repositories.DroneRepository;

@Service
public class UpdateDronesBatteryListener implements ApplicationListener<UpdateDronesBatteryEvent>{
	
	@Autowired
	AuditRepository auditRepository;
	
	@Autowired
	DroneRepository droneRepository;
	
	@Autowired
	IDroneCommunicationService droneCommunicationService;
	 
	@Override
	@Transactional
	// method that listen on UpdateDronesBatteryEvent to communicate with the drone throw droneCommunicationService and update the drone battery in db
	public void onApplicationEvent(UpdateDronesBatteryEvent event) {
		Drones drones = (Drones)event.getSource();
		
		StringBuilder builder=new StringBuilder("get updated battery level for drone [").append(drones.getSerialNumber()).append("] as it is now ").append(drones.getBatteryCapacityPercentage());
		Audit audit= Audit.builder().droneSerial(drones.getSerialNumber()).droneState(drones.getState()).creationDate(new Date()).description(builder.toString()).build();
		
		// audit battery level before calling 
		auditRepository.save(audit);
		
		int updatedLevel = droneCommunicationService.getDroneBattary(drones);
		 builder=new StringBuilder("the updated battery level for drone [").append(drones.getSerialNumber()).append("] is ").append(updatedLevel);
		 audit= Audit.builder().droneSerial(drones.getSerialNumber()).droneState(drones.getState()).creationDate(new Date()).description(builder.toString()).build();
		drones.setBatteryCapacityPercentage(updatedLevel);
		droneRepository.save(drones);
		// audit battery level after calling 
		auditRepository.save(audit);
		
		
	}

}
