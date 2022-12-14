package com.musalasoft.execptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class FleetExceptionHandler {

	@ExceptionHandler(FleetExceptions.class)
	protected ResponseEntity<ErrorBean> handleFleetExceptions(FleetExceptions ex) {
		ErrorBean apiError = new ErrorBean(ex.getMessage());
		apiError.setMessage(ex.getMessage());
		return new ResponseEntity<>(apiError, ex.getStatus());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	   public ResponseEntity<Map<String, String>> handleArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
	      BindingResult result = ex.getBindingResult();
	      Map<String, String> resEntity = new HashMap<>(); 
	       result.getFieldErrors()
	              .forEach(err-> resEntity.put(err.getField() , err.getDefaultMessage()));
	      return ResponseEntity.badRequest().body(resEntity);
	}
	

}
