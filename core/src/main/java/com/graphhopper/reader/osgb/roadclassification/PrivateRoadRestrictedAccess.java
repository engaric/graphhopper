package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

public class PrivateRoadRestrictedAccess extends AbstractOsToOsmAttibuteMappingVisitor
{

	@Override
    protected void applyAttributes( Way way )
    {
	   	way.setTag("access", "no");
    }
}
