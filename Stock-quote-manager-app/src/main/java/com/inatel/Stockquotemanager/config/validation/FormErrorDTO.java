package com.inatel.Stockquotemanager.config.validation;

public class FormErrorDTO {
	
	private String field;
	private String error;
	
	
	public FormErrorDTO(String field, String error) {
		super();
		this.field = field;
		this.error = error;
	}
	
	public String getField() {
		return field;
	}

	public String getError() {
		return error;
	}

}
