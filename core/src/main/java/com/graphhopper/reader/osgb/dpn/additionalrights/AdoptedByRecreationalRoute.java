package com.graphhopper.reader.osgb.dpn.additionalrights;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 *
 * Description: A link part of a Recreational Route
 *
 * Confirmed Allowable users: Pedestrians
 *
 * @author phopkins
 *
 */
public class AdoptedByRecreationalRoute extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    protected void applyAttributes(Way way) {
        // Assign value to use for priority
        way.setTag("network", "lwn");
        way.setTag("foot", "yes");
    }

}
