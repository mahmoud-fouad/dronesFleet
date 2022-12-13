package com.musalasoft.entities.restApis;

import javax.validation.constraints.Pattern;

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
public class MedicationDTO {
	
	@Pattern(regexp="^([A-Z]*\\_*[0-9]*)+")
	private String name;
	
	@Pattern(regexp="([A-Za-z]*\\_*\\-*)+")
	private String code;
	
	private int weight;
	
	private String imageURL ;

}
