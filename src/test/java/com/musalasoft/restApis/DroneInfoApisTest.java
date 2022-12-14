package com.musalasoft.restApis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.GsonBuilder;
import com.musalasoft.entities.Drones;
import com.musalasoft.entities.restApis.DroneDTO;
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

/**
 * 
 * @author mahmoud
 *test cases related to drone it self (register and get battery level)
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DroneInfoApisTest {
	
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	DroneRepository droneRepository;

	List<Drones> drones;
	
	
	@Before
	public void init(){
		drones= droneRepository.findAll();
		
	}
	
	@Test
	public void testNotExistDrone(){
		
		RequestBuilder request = MockMvcRequestBuilders.get("/drone/2000/medications");
		try {
			 MvcResult result = mvc.perform(request)
					 .andExpect(status().isBadRequest())
					 .andExpect(jsonPath("$.batteryLevel",is(FleetExceptions.notFoundDrone)))
					 .andReturn();
			 System.out.println( result.getResponse().getContentAsString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testNotInvalideDroneModelRegisterRequest(){
		try {
			DroneDTO requestBody =DroneDTO.builder().model("invalidMode").serialNumber("234").weightLimit(400).build();
			
			;
			RequestBuilder request = MockMvcRequestBuilders.post("/registerDone").contentType(MediaType.APPLICATION_JSON_UTF8)
			        .content(new GsonBuilder().create().toJson(requestBody));
			
			 MvcResult result = mvc.perform(request)
					 .andExpect(status().isBadRequest())
					 .andExpect(jsonPath("$.message",is(FleetExceptions.inCompatableMode)))
					 .andReturn();
			 System.out.println( result.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testNotInvalideDroneSerialRegisterRequest(){
		try {
			 int leftLimit = 48; // numeral '0'
			    int rightLimit = 122; // letter 'z'
			    int targetStringLength = 150;
			    Random random = new Random();

			    String generatedString = random.ints(leftLimit, rightLimit + 1)
			      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			      .limit(targetStringLength)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
			    assertEquals(generatedString.length(), 150);
			DroneDTO requestBody =DroneDTO.builder().model("Cruiserweight").serialNumber(generatedString).weightLimit(400).build();
			
			String jsonRequest = new GsonBuilder().create().toJson(requestBody);
			System.out.println(jsonRequest);
			
			RequestBuilder request = MockMvcRequestBuilders.post("/registerDone").contentType(MediaType.APPLICATION_JSON_UTF8)
			        .content(jsonRequest);
			
			 MvcResult result = mvc.perform(request)
					 .andExpect(status().isBadRequest())
					 .andReturn();
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testBlackDroneSerialRegisterRequest(){
		try {
			DroneDTO requestBody =DroneDTO.builder().weightLimit(400).build();
			 String jsonRequest = new GsonBuilder().create().toJson(requestBody);
				System.out.println(jsonRequest);
			 
			 RequestBuilder  request = MockMvcRequestBuilders.post("/registerDone").contentType(MediaType.APPLICATION_JSON_UTF8)
				        .content(jsonRequest);
			  
			 MvcResult result = mvc.perform(request)
						 .andExpect(status().isBadRequest())
						 .andReturn();
			 System.out.println(result.getResponse().getContentAsString());
			 
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testValidDroneSerialRegisterRequest(){
		try {
			 int leftLimit = 48; // numeral '0'
			    int rightLimit = 122; // letter 'z'
			    int targetStringLength = 150;
			    Random random = new Random();

			    String generatedString = random.ints(leftLimit, rightLimit + 1)
			      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			      .limit(targetStringLength)
			      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
			      .toString();
			    assertEquals(generatedString.length(), 150);
			DroneDTO requestBody =DroneDTO.builder().model("Cruiserweight").serialNumber("123123").weightLimit(400).build();
			
			String jsonRequest = new GsonBuilder().create().toJson(requestBody);
			System.out.println(jsonRequest);
			
			RequestBuilder request = MockMvcRequestBuilders.post("/registerDone").contentType(MediaType.APPLICATION_JSON_UTF8)
			        .content(jsonRequest);
			
			 MvcResult result = mvc.perform(request)
					 .andExpect(status().isOk())
					 .andReturn();
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void getBatterlevel(){
		try {
			
			assertThat(drones.size()).isGreaterThan(0);
			
			RequestBuilder request = MockMvcRequestBuilders.get("/drone/"+drones.get(0).getId()+"/batteryLevel");
		
		 MvcResult result = mvc.perform(request)
				 .andExpect(status().isOk())
				 .andExpect(jsonPath("$.batteryLevel",is(drones.get(0).getBatteryCapacityPercentage())))
				 .andReturn();
		 
	} catch (Exception e) {
		e.printStackTrace();
		fail();
	}
		
	}

}
