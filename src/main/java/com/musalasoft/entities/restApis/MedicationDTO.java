package com.musalasoft.entities.restApis;

import javax.validation.constraints.NotEmpty;
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
	
	@NotEmpty
	@Pattern(regexp="^([A-Za-z]*[0-9]*\\_*\\-*)+")
	private String name;
	
	@NotEmpty
	@Pattern(regexp="^([A-Z]*\\_*[0-9]*)+")
	private String code;
	
	private int weight;
	
	private String image ;

}
