package com.musalasoft.entities.restApis;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {
	
	private Long id;
	@NotEmpty(message= "serialNumber is mandatory")
	@Size(max=100 ,message="serial number lenght can not exceed 100")
	private String serialNumber;
	@NotEmpty(message = "model is mandatory")
	private String model;
	@Max(500)
	private int weightLimit;

}
