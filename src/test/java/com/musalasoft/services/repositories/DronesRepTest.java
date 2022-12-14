package com.musalasoft.services.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.musalasoft.entities.DroneModel;
import com.musalasoft.entities.DroneState;
import com.musalasoft.entities.Drones;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
public class DronesRepTest {
	
	@Autowired
	DroneRepository droneRepository;
	
	Drones drones;
	
	@Before
	public void init(){
		
		drones = Drones.builder()
				.batteryCapacityPercentage(100)
				.model(DroneModel.Heavyweight)
				.serialNumber("2342342")
				.state(DroneState.IDLE)
				.weightLimit(350)
				.build();
	}
	
	
	@Test
	public void addDrones(){
		assertNotNull(droneRepository);
		droneRepository.save(drones);
		assertThat(drones.getId()).isGreaterThan(0);
	}

}
