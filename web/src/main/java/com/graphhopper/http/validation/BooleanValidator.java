package com.graphhopper.http.validation;

public class BooleanValidator {

    public boolean isValid(String param) {
        return "true".equalsIgnoreCase(param) || "false".equalsIgnoreCase(param);
    }

}
