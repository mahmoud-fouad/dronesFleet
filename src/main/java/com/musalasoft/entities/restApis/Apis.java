package com.musalasoft.entities.restApis;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.services.IDronesService;
import com.musalasoft.services.IDroneCommunicationService;

@RestController

public class Apis {
	@Autowired
	IDronesService dronesService;
	
	@Autowired
	IDroneCommunicationService medicationloadingService;

	@PostMapping("/registerDone")
	public ResponseEntity<Void> registerDrone( @Valid @RequestBody(required=true) DroneDTO droneDto) {
		dronesService.addDrone(droneDto);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/drone/{id}/medications")
	public ResponseEntity<Void> loadDroneMedication(@PathVariable(name="id") long droneId , @Valid @RequestBody(required=true) MedicationsReqRes req) {
		List<Medication> medicien = req.getMedications().stream().map(medicationloadingService::getMedicateEntityFromDto).collect(Collectors.toList());
		
		dronesService.loadDroneMedication(droneId, medicien);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/drone/{id}/medications")
	public ResponseEntity<MedicationsReqRes> getDroneMedication(@PathVariable(name="id") long droneId) {
		
		List<Medication> medications = dronesService.getDroneMedication(droneId);
		MedicationsReqRes res = new MedicationsReqRes(medications.stream().map(this::getMedicationDTOFromEntity).collect(Collectors.toList()));
		return new ResponseEntity<MedicationsReqRes>(res, HttpStatus.OK);
	}
	
	@GetMapping("/drone/{id}/batteryLevel")
	public ResponseEntity<DroneBatteryRes> getDroneBatteryLevel(@PathVariable(name="id") long droneId) {

		DroneBatteryRes res = DroneBatteryRes.builder().batteryLevel(dronesService.getDroneBattaryLevel(droneId)).build();
		return new ResponseEntity<DroneBatteryRes>(res, HttpStatus.OK);
	}

	@GetMapping("/getAvailableDrones")
	public ResponseEntity<DronesRes> getAvailableDrones() {

		List<Drones> drones= dronesService.getAvailableDrones();
		DronesRes res = new DronesRes(drones.stream().map(this::getDroneDTOFromEntity).collect(Collectors.toList()));
		return new ResponseEntity<DronesRes>(res, HttpStatus.OK);
	}
	
	private DroneDTO getDroneDTOFromEntity(Drones drones){
		
		return DroneDTO.builder()
				.id(drones.getId())
				.model(drones.getModel().toString())
				.serialNumber(drones.getSerialNumber())
				.weightLimit(drones.getWeightLimit()).build();
	}
	
private MedicationDTO getMedicationDTOFromEntity(Medication medication){
		MedicationDTO dto =new MedicationDTO();
		BeanUtils.copyProperties(medication, dto);
		return dto;
	}
}
