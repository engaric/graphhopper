package com.graphhopper.http;

import org.eclipse.jetty.http.HttpStatus.Code;

public class NoSuchParameterException extends APIException {

    public NoSuchParameterException(String message) {
        super(Code.BAD_REQUEST, message);
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

}
