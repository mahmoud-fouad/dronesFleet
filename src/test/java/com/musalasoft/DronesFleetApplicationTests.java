package com.musalasoft;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DronesFleetApplicationTests {
	
	@Autowired
	FleetConfigurations fleetConfigurations;

	@Test
	public void contextLoads() {
		
		assertNotNull(fleetConfigurations);
		assertEquals(10, fleetConfigurations.getCapacity());
	}

}
