package com.musalasoft.execptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author mahmoud
 *{@link Custom exception
 */
public class FleetExceptions extends RuntimeException {
	HttpStatus status;

	public FleetExceptions() {
		this.status=HttpStatus.BAD_REQUEST;
	}
	
	FleetExceptions(HttpStatus status) {
	       this();
	       this.status = status;
	   }
	
}
