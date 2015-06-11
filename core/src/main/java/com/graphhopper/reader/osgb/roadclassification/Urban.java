package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;
import com.graphhopper.routing.util.CarFlagEncoder;

public class Urban extends AbstractOsToOsmAttibuteMappingVisitor
{
	@Override
    protected void applyAttributes( Way way )
    {
		if(!way.hasTag("maxspeed")) {
			way.setTag("maxspeed", "30 mph");
		}
    }
}
