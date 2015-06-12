package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

public class Alley extends AbstractOsToOsmAttibuteMappingVisitor
{

	@Override
    public void applyAttributes( RoutingElement way )
	{
		way.setTag("highway", "service");
		way.setTag("service", "alley");
	}
}
