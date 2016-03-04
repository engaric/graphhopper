package com.graphhopper.http;

import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.HttpStatus.Code;

public class APIException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private HttpStatus.Code statusCode;

    public APIException(Code statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus.Code getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return getMessage();
    }

}
