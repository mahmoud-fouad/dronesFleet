package com.musalasoft.execptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class FleetExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(FleetExceptions.class)
	protected ResponseEntity<Object> handleFleetExceptions(FleetExceptions ex) {
		ErrorBean apiError = new ErrorBean(ex.getMessage());
		apiError.setMessage(ex.getMessage());
		return new ResponseEntity<>(apiError, ex.getStatus());
	}
	

}
