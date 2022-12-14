package com.musalasoft.entities.restApis;

import java.util.Collections;
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
import com.musalasoft.services.IMedicationloadingService;

@RestController
public class Apis {
	@Autowired
	IDronesService dronesService;
	
	@Autowired
	IMedicationloadingService medicationloadingService;

	@PostMapping("/registerDone")
	public ResponseEntity<Void> registerDrone(@Valid @RequestBody DroneDTO droneDto) {
		dronesService.addDrone(droneDto);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/drone/{id}/loadMedications")
	public ResponseEntity<Void> loadDroneMedication(@PathVariable long droneId , @Valid @RequestBody MedicationsReqRes req) {
		List<Medication> medicien = req.getMedications().stream().map(medicationloadingService::getMedicateEntityFromDto).collect(Collectors.toList());
		
		dronesService.loadDroneMedication(droneId, medicien);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/drone/{id}/medications")
	public ResponseEntity<MedicationsReqRes> getDroneMedication(@PathVariable long droneId) {
		
		List<Medication> medications = dronesService.getDroneMedication(droneId);
		MedicationsReqRes res = new MedicationsReqRes(medications.stream().map(this::getMedicationDTOFromEntity).collect(Collectors.toList()));
		return new ResponseEntity<MedicationsReqRes>(res, HttpStatus.OK);
	}
	
	@GetMapping("/drone/{id}/batteryLevel")
	public ResponseEntity<DroneBatteryRes> getDroneBatteryLevel(@PathVariable long droneId) {

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
