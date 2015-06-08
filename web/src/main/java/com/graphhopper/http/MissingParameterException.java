package com.graphhopper.http;

import org.eclipse.jetty.http.HttpStatus;

public class MissingParameterException extends APIException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static String message = "No %s parameter provided.";

	public MissingParameterException(String parameter) {
		super(HttpStatus.Code.BAD_REQUEST, String.format(message , parameter));
	}

}
