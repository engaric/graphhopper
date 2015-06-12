package com.graphhopper.reader.osgb.dpn.additionalrights;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: A link part of the National Cycle Network
 *
 * Confirmed Allowable users: Pedestrians, Cyclists
 *
 * @author phopkins
 *
 */
public class AdoptedByNationalCycleRoute extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way) {
        // Assign value to use for priority
        way.setTag("network", "ncn");

        way.setTag("bicycle", "yes");
        way.setTag("foot", "yes");
    }

}
