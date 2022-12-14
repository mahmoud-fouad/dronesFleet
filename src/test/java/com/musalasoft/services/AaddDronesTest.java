package com.musalasoft.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.musalasoft.FleetConfigurations;
import com.musalasoft.entities.DroneModel;
import com.musalasoft.entities.DroneState;
import com.musalasoft.entities.Drones;
import com.musalasoft.entities.restApis.DroneDTO;
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
/**
 * 
 * @author mahmoud
 *test cases for add new drone
 */
public class AaddDronesTest {
	
	@Mock
	DroneRepository  droneRepository;
	
	DronesService service;
	
	FleetConfigurations fleetConfigurations;

	
	@Rule
	public ExpectedException  exceptionRule = ExpectedException.none();
	
	@Before
	public void init(){
		fleetConfigurations= new FleetConfigurations();
		fleetConfigurations.setCapacity(10);
		service = new DronesService(droneRepository,fleetConfigurations,null);
		
	}
	//test add new drone while the fleet capacity is full
	@Test
	public void testCompleteFleetCapacity(){
		when(droneRepository.count()).thenReturn(10L);
		exceptionRule.expect(FleetExceptions.class);
		exceptionRule.expectMessage(FleetExceptions.fullFleet);
		service.addDrone(DroneDTO.builder().id(1L).build());
		
	}
	// test that passing all checks and the drone 
	@Test
	public void testCorrectDroneData(){
		
		Drones drone = Drones.builder().batteryCapacityPercentage(100).model(DroneModel.Cruiserweight).serialNumber("123123SSDFf").weightLimit(200).build();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		 Set<ConstraintViolation<Drones>> violations = validator.validate(drone);
		 
		 assertThat(violations).hasSize(0);
	}
	
	//test that use define drone with weight exceed the drone weight limit
	@Test
	public void testDroneOverWeightData(){
		
		Drones drone = Drones.builder().batteryCapacityPercentage(100).model(DroneModel.Cruiserweight).serialNumber("123123SSDFf").weightLimit(600).build();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		 Set<ConstraintViolation<Drones>> violations = validator.validate(drone);
		 
		 assertThat(violations).hasSize(1);
	}
	
	//test the mapping from dto to check that the model will converted from string to enum
	@Test
	public void testDroneDTOMapping(){
		
		DroneDTO dto =DroneDTO.builder().model("Cruiserweight").serialNumber("123123SSDFf").weightLimit(300).build();
		Drones drone = Drones.builder().batteryCapacityPercentage(100).model(DroneModel.Cruiserweight).serialNumber("123123SSDFf").weightLimit(300).state(DroneState.IDLE).build();
		 service.addDrone(dto);
		
		verify(droneRepository).save(drone);
	}
	
	//test the mapping incorrect model
		@Test
		public void testIncompatableDroneDTOModel(){
			
			DroneDTO dto =DroneDTO.builder().model("notExist").serialNumber("123123SSDFf").weightLimit(300).build();
			exceptionRule.expect(FleetExceptions.class);
			exceptionRule.expectMessage(FleetExceptions.inCompatableMode);
			 service.addDrone(dto);
			
		}
	

}
