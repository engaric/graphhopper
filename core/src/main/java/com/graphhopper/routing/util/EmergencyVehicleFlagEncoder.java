package com.graphhopper.routing.util;

import com.graphhopper.reader.Way;
import com.graphhopper.routing.EscapePrivateWeighting;

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
	    if(key==EscapePrivateWeighting.KEY) 
	    	return startStopEncoder.getValue(flags);
		return super.getLong(flags, key);
	}
	
	@Override
	public long handleWayTags( Way way, long allowed, long relationFlags )
	{
	    long superHandled =  super.handleWayTags(way, allowed, relationFlags);
	    System.err.println("SUPER"+ superHandled);
	    return superHandled |= startStopEncoder.setValue(superHandled, noThroughWayAccess(way));
	}

	private int noThroughWayAccess( Way way )
    {
	    return way.hasTag("access", "no")||way.hasTag("service", "alley")?1:0;
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
    
    @Override
    public boolean supports( Class<?> feature )
    {
        if (super.supports(feature))
            return true;

        return EscapePrivateWeighting.class.isAssignableFrom(feature);
    }
	
}
