package com.graphhopper.http.validation;

import java.util.Arrays;
import java.util.List;

public class CaseInsensitiveStringListValidator {
    public boolean isValid(String value, String... validValues) {
        return isValid(value, Arrays.asList(validValues));
    }

    public boolean isValid(String value, List<String> validValues) {
        for (String validValue : validValues) {
            if (value.equalsIgnoreCase(validValue)) {
                return true;
            }
        }
        return false;
    }
}
