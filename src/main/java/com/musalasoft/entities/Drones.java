package com.musalasoft.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;

import org.hibernate.annotations.Cascade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * 
 * @author mahmoud
 * Drones entity class
 *
 */
public class Drones {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(length=100,nullable=false,unique=true)
	private String serialNumber;
	
	@Enumerated(EnumType.STRING)
	private DroneModel model;
	
	@Max(value=500,message="Drone weight can not be more than 500gr")
	private int weightLimit;
	
	private int batteryCapacityPercentage; 
	
	@Enumerated(EnumType.STRING)
	private DroneState state ;
	
	// the loaded medications 
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="drone_id")
	List<Medication> loadedMedications;
	

}
