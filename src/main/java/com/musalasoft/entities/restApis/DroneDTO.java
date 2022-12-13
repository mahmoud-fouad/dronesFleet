package com.musalasoft.entities.restApis;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class DroneDTO {
	
	private Long id;
	private String serialNumber;
	private String model;
	private int weightLimit;

}
