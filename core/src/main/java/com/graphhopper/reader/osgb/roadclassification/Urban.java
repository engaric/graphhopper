package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;
import com.graphhopper.routing.util.CarFlagEncoder;

public class Urban extends AbstractOsToOsmAttibuteMappingVisitor
{
	private static final String _30_MPH = Integer.toString(CarFlagEncoder.THIRTY_MPH_IN_KPH);
	@Override
    protected void applyAttributes( Way way )
    {
		if(!way.hasTag("maxspeed")) {
			way.setTag("maxspeed", _30_MPH);
		}
    }
}
