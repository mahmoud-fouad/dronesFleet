package com.musalasoft.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.musalasoft.UpdateDronesBatteryEvent;
import com.musalasoft.entities.Drones;
import com.musalasoft.services.repositories.DroneRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
/**
 * 
 * @author mahmoud
 * Schedualed job to update the drone battery 
 */
public class ScheduledDroneBattryCheck {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	//each minutes check on drone battery will run
	
	@Autowired
	DroneRepository droneRepository;
	
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@Scheduled(fixedRate = 60000)
	public void reportCurrentTime() {
		log.info("starting check on  batteries at {}", dateFormat.format(new Date()));
		
		
		List<Drones> drones =  droneRepository.findAll();
		
		drones.forEach( (drone) ->
		updateDroneBAtterylevel(drone));
		
		log.info("end checking on batteries at {}", dateFormat.format(new Date()));
	}
	
	// Raise async event to update all drones battery
	@Async
	public void updateDroneBAtterylevel(Drones drone){
		log.info("check on drone with serial {} and get updated battery level as its now {} %",drone.getSerialNumber(), drone.getBatteryCapacityPercentage()) ;
		applicationEventPublisher.publishEvent(new UpdateDronesBatteryEvent(drone));
		
	}

}
