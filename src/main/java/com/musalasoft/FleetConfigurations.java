package com.musalasoft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "fleet")
@Data
@Getter
@Setter
/**
 * 
 * @author mahmoud
 * configurations
 * 
 */
public class FleetConfigurations {
	
	//make fleet capacity environmental variable to be configurable
	private int capacity;
	
	

}
