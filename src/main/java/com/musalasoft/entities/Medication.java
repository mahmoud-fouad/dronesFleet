package com.musalasoft.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private  long id;

	@Pattern(regexp="([A-Za-z]*[0-9]*\\_*\\-*)+")
	@Column(nullable=false)
	private String name;
	
	private int weight;
	
	@Column(nullable=false)
	@Pattern(regexp="^([A-Z]*\\_*[0-9]*)+")
	private String code;
	
	//picture url of the medication case
	private String imageURL ;

}
