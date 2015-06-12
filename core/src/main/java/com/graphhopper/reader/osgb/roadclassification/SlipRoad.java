package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

public class SlipRoad extends AbstractOsToOsmAttibuteMappingVisitor
{
	private static final String LINK = "_link";
	@Override
    public void applyAttributes( RoutingElement way )
    {
		if(way.hasTag("highway")) {
			way.setTag("highway", way.getTag("highway") + LINK);
		}
    }
}
