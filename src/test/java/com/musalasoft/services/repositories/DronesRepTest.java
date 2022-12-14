package com.musalasoft.services.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
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
import com.musalasoft.entities.Medication;

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
	@Test
	public void testMedicienInsertion(){
		try{
		Drones d=  getIdelDroneHasBattryMorethan25();
		List<Medication> medicines = new ArrayList<Medication>();
		medicines.add(Medication.builder().name("12_ABC").weight(20).code("MED12").build());
		medicines.add(Medication.builder().name("13-ABE").weight(60).code("56MED12").build());
		medicines.add(Medication.builder().name("12_CB_C").weight(20).code("MED12M").build());
		medicines.add(Medication.builder().name("AB-sdf-3").weight(100).code("MED12T").build());
		
		d.setLoadedMedications(medicines);
		
		droneRepository.save(d);
		
		assertThat(d.getLoadedMedications()).hasSize(4);
		assertThat(d.getLoadedMedications().get(0).getId()).isGreaterThan(0L);
		
		}
		catch (Exception e) {
			fail();
		}
		
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
