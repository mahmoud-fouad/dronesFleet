package com.musalasoft.execptions;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author mahmoud
 *{@link Custom exception
 */
public class FleetExceptions extends RuntimeException {
	HttpStatus status;
	
	public static String fullFleet="the fleet is full";
	public static String notFoundDrone= "can not find this drone in the Fleet";
	public static String inCompatableMode="incompatable drone model";
	public static String loadingError="Error while loading and I am unpacking";
	public static String overLoadedWeight="this is overloaded weight";
	public static String emptyPack="there is no medicien to load";
	public static String unAvailbeDrone="can not use this drone at this moment";
	
	public FleetExceptions(String message){
		super(message);
		this.status=HttpStatus.BAD_REQUEST;
	}

	public FleetExceptions(String message , HttpStatus status ){
		super(message);
		this.status= status;
	}
}
