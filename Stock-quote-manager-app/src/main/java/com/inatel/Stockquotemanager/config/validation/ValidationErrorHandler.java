package com.inatel.Stockquotemanager.config.validation;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public FormErrorDTO handleIncorrectValue(NumberFormatException ex) {
		
		return new FormErrorDTO("Quote value input NOT supported", ex.getMessage()) ;
		
		
	}
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DateTimeParseException.class)
	public FormErrorDTO handleIncorrectDate(DateTimeParseException ex) {
		return new FormErrorDTO("Quote date input NOT supported", ex.getMessage()) ;
		
	}
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidIDException.class)
	public FormErrorDTO handleIncorrectID(InvalidIDException ex) {
		return new FormErrorDTO("ID input NOT supported", ex.getMessage()) ;
		
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public FormErrorDTO handleUnavailableStock(ValidationException ex) {
		
		return new FormErrorDTO("Specified Stock is not available for creation", ex.getMessage()) ;
		
		
	}

}
