package com.musalasoft.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Builder
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
	private String droneName;
	private DroneState droneState;
	
}
