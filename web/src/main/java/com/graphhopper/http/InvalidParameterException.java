package com.graphhopper.http;

import org.eclipse.jetty.http.HttpStatus.Code;

public class InvalidParameterException extends APIException {

    public InvalidParameterException(String message) {
        super(Code.BAD_REQUEST, message);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
