package com.musalasoft.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;

import com.musalasoft.FleetConfigurations;
import com.musalasoft.entities.DroneModel;
import com.musalasoft.entities.DroneState;
import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

/**
 * 
 * @author mahmoud 
 * test cases for Drone mediation loading and displaying
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DroneMedicationTest {

	@Mock
	DroneRepository droneRepository;

	DronesService service;
	@Mock
	FleetConfigurations fleetConfigurations;
	
	
	@Mock
	IDroneCommunicationService medicationLoadingServiceMock;
	
	
	
	List<Medication> medicines;
	
	@Before
	public void init() {
		service = new DronesService(droneRepository, fleetConfigurations,medicationLoadingServiceMock);
		medicines=new ArrayList<Medication>();
		medicines.add(Medication.builder().name("12_ABC").weight(20).code("MED12").build());
		medicines.add(Medication.builder().name("13-ABE").weight(60).code("56MED12").build());
		medicines.add(Medication.builder().name("12_CB_C").weight(20).code("MED12M").build());
		medicines.add(Medication.builder().name("AB-sdf-3").weight(100).code("MED12T").build());
		

	}

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	// check that system will throw FleetExceptions.notFoundDrone in case not exist drone
	@Test
	public void getDroneMedicationForNoneExistingDrone() {
		
		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.notFoundDrone);
		 service.getDroneMedication(10L);
	}

	// get drone Medications
	@Test
	public void getDroneMedication() {
		Medication m =Medication.builder().code("medCode").build();

		Drones d = Drones.builder().batteryCapacityPercentage(50).loadedMedications(Collections.singletonList(m)).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(10L)).thenReturn(drononOptional );
		
		assertEquals("medCode", service.getDroneMedication(10L).get(0).getCode());
	}
	
	@Test
	public void testUnAvailbaleDrone(){
		Drones d = Drones.builder().id(2L).state(DroneState.RETURNING).batteryCapacityPercentage(50).model(DroneModel.Lightweight).weightLimit(100).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(2L)).thenReturn(drononOptional );
		
		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.unAvailbeDrone);
		 service.loadDroneMedication(2L,medicines);
		 
		 Drones d2 = Drones.builder().id(3L).state(DroneState.IDLE).batteryCapacityPercentage(20).model(DroneModel.Heavyweight).weightLimit(500).build();
			Optional<Drones> drononOptional2 =Optional.of(d2);
			when(droneRepository.findById(3L)).thenReturn(drononOptional2 );
			
			exceptionRule.expect(FleetExceptions.class);
			exceptionRule.expectMessage(FleetExceptions.unAvailbeDrone);
			 service.loadDroneMedication(3L,medicines);
	}

	
	@Test
	public void testOverloadedPack(){
		Drones d = Drones.builder().id(2L).state(DroneState.IDLE).batteryCapacityPercentage(100).model(DroneModel.Lightweight).weightLimit(100).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(2L)).thenReturn(drononOptional );
		
		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.overLoadedWeight);
		 service.loadDroneMedication(2L,medicines);
	}
	
	@Test
	public void testEmptyPack(){
		Drones d = Drones.builder().id(2L).state(DroneState.IDLE).batteryCapacityPercentage(100).model(DroneModel.Lightweight).weightLimit(100).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(2L)).thenReturn(drononOptional );
		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.emptyPack);
		 service.loadDroneMedication(2L,Collections.EMPTY_LIST);
		
		
	}
	
	@Test
	public void testUnSuccessfulloading(){
		Drones d = Drones.builder().id(2L).state(DroneState.IDLE).batteryCapacityPercentage(100).model(DroneModel.Middleweight).weightLimit(300).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(2L)).thenReturn(drononOptional );
		
		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.loadingError);
		
		 service.loadDroneMedication(2L,medicines);
		 
	}
	
	@Test
	public void testloadSuccess(){
		
		service = new DronesService(droneRepository, fleetConfigurations,new DroneCommunicationService());
		
		Drones d = Drones.builder().id(2L).state(DroneState.IDLE).batteryCapacityPercentage(100).model(DroneModel.Middleweight).weightLimit(300).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(2L)).thenReturn(drononOptional );
		
		
		
		service.loadDroneMedication(2L,medicines);
		 
		 VerificationMode invcount = times(2);
		 verify(droneRepository, invcount).save(d);
	}
	
	

}
