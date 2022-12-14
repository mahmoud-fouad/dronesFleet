package com.musalasoft.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.musalasoft.FleetConfigurations;
import com.musalasoft.entities.Drones;
import com.musalasoft.entities.Medication;
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

/**
 * 
 * @author mahmoud 
 * test cases for Drone mediation
 */
public class DroneMedicationTest {

	@Mock
	DroneRepository droneRepository;

	DronesService service;

	FleetConfigurations fleetConfigurations;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void init() {
		fleetConfigurations = new FleetConfigurations();
		fleetConfigurations.setCapacity(10);
		service = new DronesService(droneRepository, fleetConfigurations);

	}

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

}
