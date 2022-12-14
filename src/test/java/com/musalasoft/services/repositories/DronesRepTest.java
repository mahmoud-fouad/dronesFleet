package com.musalasoft.services.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
	
	@Test
	public void getAvalialbeDrones(){
		droneRepository.save(getIdelDroneHasBattrylessthan25());
		droneRepository.save(getIdelDroneHasBattryMorethan25());
		droneRepository.save(getLoadingDroneHasBattryMorethan25());
		List<Drones> drones= droneRepository.getDronesByStatesandBatteryLevel(DroneState.IDLE, 25);
		assertThat(drones).hasSize(1);
		assertEquals(drones.get(0).getSerialNumber(), "1231ABC");
		
		
	}
	
private Drones getIdelDroneHasBattryMorethan25(){
	
	return Drones.builder().model(DroneModel.Cruiserweight)
	.batteryCapacityPercentage(60).serialNumber("1231ABC")
	.state(DroneState.IDLE).build();
}

private Drones getIdelDroneHasBattrylessthan25(){
	
	return Drones.builder().model(DroneModel.Cruiserweight)
	.batteryCapacityPercentage(20).serialNumber("1231ABF")
	.state(DroneState.IDLE).build();
}

private Drones getLoadingDroneHasBattryMorethan25(){
	
	return Drones.builder().model(DroneModel.Cruiserweight)
	.batteryCapacityPercentage(60).serialNumber("1251ABF")
	.state(DroneState.LOADING).build();
}
	
}
