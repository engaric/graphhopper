package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

public class DualCarriageway extends AbstractOsToOsmAttibuteMappingVisitor
{

	@Override
    public void applyAttributes( RoutingElement way )
    {
	   	way.setTag("maxspeed:type", "GB:nsl_dual");
    }
}
