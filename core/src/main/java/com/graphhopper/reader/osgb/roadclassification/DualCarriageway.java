package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

public class DualCarriageway extends AbstractOsToOsmAttibuteMappingVisitor
{

	@Override
    protected void applyAttributes( Way way )
    {
	   	way.setTag("maxspeed:type", "GB:nsl_dual");
    }
}
