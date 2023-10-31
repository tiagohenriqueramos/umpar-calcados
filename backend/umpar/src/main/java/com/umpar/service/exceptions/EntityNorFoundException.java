package com.umpar.service.exceptions;

public class EntityNorFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EntityNorFoundException(String msg) {
		super(msg);
	}
}
