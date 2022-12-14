package com.musalasoft.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

/**
 * 
 * @author mahmoud 
 * test cases for get drone battary level
 */
public class DroneBatteryTest {

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
	public void testDoesnotExistDrone() {
		
		

		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.notFoundDrone);
		 service.getDroneBattaryLevel(10L);
	}

	//test correct response
	@Test
	public void testExistDrone() {

		Drones d = Drones.builder().batteryCapacityPercentage(50).build();
		Optional<Drones> drononOptional =Optional.of(d);
		when(droneRepository.findById(10L)).thenReturn(drononOptional );
		
		assertEquals(50, service.getDroneBattaryLevel(10L));
	}

}
