package com.musalasoft.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * 
 * @author mahmoud
 *Audit entity
 */
public class Audit {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	private String droneSerial;
	@Enumerated(EnumType.STRING)
	private DroneState droneState;
	
}
