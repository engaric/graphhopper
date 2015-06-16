package com.graphhopper.routing.util;

import com.graphhopper.reader.Way;

public class EmergencyVehicleFlagEncoder extends CarFlagEncoder
{
	private EncodedValue startStopEncoder;

	public EmergencyVehicleFlagEncoder( String entryVal )
	{
		super(entryVal);
		restrictedValues.clear();
	}

	@Override
	public String toString()
	{
		return "emv";
	}

	@Override
	public long acceptWay( Way way )
	{
		if(way.hasTag("motorcar", "no")) 
			return 0; 
	    return super.acceptWay(way);
	}
	
	@Override
	public long getLong( long flags, int key )
	{
	    if(key==100) 
	    	return startStopEncoder.getValue(flags);
		return super.getLong(flags, key);
	}
	
	@Override
	public long handleWayTags( Way way, long allowed, long relationFlags )
	{
	    long superHandled =  super.handleWayTags(way, allowed, relationFlags);
	    System.err.println("SUPER"+ superHandled);
	    return superHandled |= startStopEncoder.setValue(superHandled, way.hasTag("access", "no")?1:0);
	}
	
	/**
     * Define the place of the speedBits in the edge flags for car.
     */
    @Override
    public int defineWayBits( int index, int shift )
    {
        // first two bits are reserved for route handling in superclass
        shift = super.defineWayBits(index, shift);
        speedEncoder = new EncodedDoubleValue("Speed", shift, speedBits, speedFactor, defaultSpeedMap.get("secondary"), 
                                              maxPossibleSpeed);
        shift += speedEncoder.getBits();
        
        startStopEncoder = new EncodedValue("StartStop", shift, 1, 1, 0, 1, true);
        shift += startStopEncoder.getBits();
        
        return shift;
    }
	
}
