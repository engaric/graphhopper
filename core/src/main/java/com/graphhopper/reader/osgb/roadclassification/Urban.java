package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

public class Urban extends AbstractOsToOsmAttibuteMappingVisitor
{
	@Override
    public void applyAttributes( RoutingElement way )
    {
		if(!way.hasTag("maxspeed")) {
			way.setTag("maxspeed", "30 mph");
		}
    }
}
