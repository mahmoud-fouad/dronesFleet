package com.musalasoft.restApis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musalasoft.entities.DroneState;
import com.musalasoft.entities.Drones;
import com.musalasoft.entities.restApis.MedicationDTO;
import com.musalasoft.entities.restApis.MedicationsReqRes;
import com.musalasoft.execptions.FleetExceptions;
import com.musalasoft.services.repositories.DroneRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DronesMediciensApisTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	DroneRepository droneRepository;
	

	List<Drones> drones;
	List<Drones> avaibleDrones;
	List<MedicationDTO> medicines;

	@Before
	public void init() {
		drones = droneRepository.findAll();
		avaibleDrones = drones.stream().filter((dron) -> dron.getBatteryCapacityPercentage() > 25)
				.filter((dron) -> dron.getState() == DroneState.IDLE).collect(Collectors.toList());
		medicines = new ArrayList<MedicationDTO>();

		medicines = new ArrayList<MedicationDTO>();
		medicines.add(MedicationDTO.builder().name("12_ABC").weight(20).code("MED12").build());
		medicines.add(MedicationDTO.builder().name("13-ABE").weight(60).code("56MED12").build());
		medicines.add(MedicationDTO.builder().name("12_CB_C").weight(20).code("MED12M").build());
		medicines.add(MedicationDTO.builder().name("AB-sdf-3").weight(100).code("MED12T").build());
	}

	@Test
	public void invalideMedication() {

		MedicationsReqRes requestDto = new MedicationsReqRes(
				Collections.singletonList(MedicationDTO.builder().code("sdf").build()));
		try {
			RequestBuilder request = MockMvcRequestBuilders.post("/drone/" + drones.get(0).getId() + "/medications")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(new GsonBuilder().create().toJson(requestDto));

			mvc.perform(request).andExpect(status().isBadRequest()).andReturn();

			requestDto.getMedications().get(0).setCode("$%^^");
			request = MockMvcRequestBuilders.post("/drone/" + drones.get(0).getId() + "/medications")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(new GsonBuilder().create().toJson(requestDto));

			mvc.perform(request).andExpect(status().isBadRequest()).andReturn();

			requestDto.getMedications().get(0).setCode("DFHH");
			requestDto.getMedications().get(0).setName("#@$");
			request = MockMvcRequestBuilders.post("/drone/" + drones.get(0).getId() + "/medications")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(new GsonBuilder().create().toJson(requestDto));

			mvc.perform(request).andExpect(status().isBadRequest()).andReturn();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testUnAvailbeDrone() {
		try {
			List<Drones> unAvaile = drones.stream().filter((d) -> d.getBatteryCapacityPercentage() < 25)
					.collect(Collectors.toList());
			RequestBuilder request = MockMvcRequestBuilders.post("/drone/" + unAvaile.get(0).getId() + "/medications")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(new GsonBuilder().create().toJson(new MedicationsReqRes(medicines)));

			mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("$.message",is(FleetExceptions.unAvailbeDrone))).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testOverloadedDrone() {
		try {
			List<Drones> unAvaile = avaibleDrones.stream().filter((d) -> d.getWeightLimit() < 200)
					.collect(Collectors.toList());
			RequestBuilder request = MockMvcRequestBuilders.post("/drone/" + unAvaile.get(0).getId() + "/medications")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(new GsonBuilder().create().toJson(new MedicationsReqRes(medicines)));

			mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("$.message",is(FleetExceptions.overLoadedWeight))).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void tesSuccessfulDroneLoad() {
		try {
			List<Drones> availe = avaibleDrones.stream().filter((d) -> d.getWeightLimit() > 200)
					.collect(Collectors.toList());
			RequestBuilder request = MockMvcRequestBuilders.post("/drone/" + availe.get(0).getId() + "/medications")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(new GsonBuilder().create().toJson(new MedicationsReqRes(medicines)));

			mvc.perform(request).andExpect(status().isOk()).andReturn();
			
			Drones d= droneRepository.findById(availe.get(0).getId()).get();
			assertThat(d.getState()).isEqualTo(DroneState.LOADED);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGetMedications() {
		try {
			List<Drones> availe = avaibleDrones.stream().filter((d) -> d.getWeightLimit() > 200)
					.collect(Collectors.toList());
			RequestBuilder request = MockMvcRequestBuilders.get("/drone/" + availe.get(0).getId() + "/medications");

			MvcResult res = mvc.perform(request).andExpect(status().isOk())
			.andReturn();
			
			Drones d= droneRepository.findById(availe.get(0).getId()).get();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}