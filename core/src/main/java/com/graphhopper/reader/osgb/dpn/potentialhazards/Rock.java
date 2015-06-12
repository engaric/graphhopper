package com.graphhopper.reader.osgb.dpn.potentialhazards;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Created by sadam on 13/02/15.
 */
public class Rock extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way) {
        setOrAppendTag(way, "natural", "rocks");
    }

}
