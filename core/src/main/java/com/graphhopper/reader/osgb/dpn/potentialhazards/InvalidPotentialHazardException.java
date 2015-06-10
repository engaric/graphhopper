package com.graphhopper.reader.osgb.dpn.potentialhazards;

import javax.xml.stream.XMLStreamException;

public class InvalidPotentialHazardException extends XMLStreamException {

    public InvalidPotentialHazardException(String msg) {
        super(msg);
    }
}
