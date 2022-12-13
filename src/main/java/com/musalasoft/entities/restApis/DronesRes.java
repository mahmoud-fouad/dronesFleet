package com.musalasoft.entities.restApis;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder

public class DronesRes {
	
	List<DroneDTO> drones;

}
