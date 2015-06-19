package com.graphhopper.routing.util;

import com.graphhopper.reader.Way;

public class EmergencyVehicleFlagEncoder extends OsCarFlagEncoder
{
	public EmergencyVehicleFlagEncoder( String entryVal )
	{
		super(entryVal);
	}

	@Override
	public String toString()
	{
		return "emv";
	}

	protected int noThroughWayAccess( Way way )
    {
	    return way.hasTag("access", "no")||way.hasTag("service", "alley")?1:0;
    }
}
