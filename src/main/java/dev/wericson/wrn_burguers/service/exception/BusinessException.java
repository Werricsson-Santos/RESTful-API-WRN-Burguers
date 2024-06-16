package dev.wericson.wrn_burguers.service.exception;

import jakarta.persistence.PersistenceException;

public class BusinessException extends PersistenceException {
	
	private static final long serialVersionUID = 1L;
	
	public BusinessException(String message) {
		super(message);
	}
}
