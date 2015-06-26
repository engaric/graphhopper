package com.graphhopper.routing.util;

import com.graphhopper.reader.Way;

public class OsCarFlagEncoder extends CarFlagEncoder {
	
	private EncodedValue startStopEncoder;
	
	public OsCarFlagEncoder() {
		this(5, 5, 0);
//		setOsAvoidanceDecorator();
	}

	public OsCarFlagEncoder(String propertiesStr) {
		super(propertiesStr);
		maxPossibleSpeed = SEVENTY_MPH_IN_KPH;
		restrictedValues.clear();
//		setOsAvoidanceDecorator();
	}

	public OsCarFlagEncoder(int speedBits, double speedFactor, int maxTurnCosts) {
		super(speedBits, speedFactor, maxTurnCosts);
		maxPossibleSpeed = SEVENTY_MPH_IN_KPH;
		restrictedValues.clear();
//		setOsAvoidanceDecorator();
	}

//	private void setOsAvoidanceDecorator() {
//		if (null == encoderDecorators) {
//			encoderDecorators = new ArrayList<EncoderDecorator>(2);
//		}
//		encoderDecorators.add(new OsVehicleAvoidanceDecorator());
//	}
	
	@Override
    public boolean supports( Class<?> feature )
    {
        if (super.supports(feature))
            return true;

        return EscapePrivateWeighting.class.isAssignableFrom(feature);
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
	    return superHandled |= startStopEncoder.setValue(superHandled, noThroughWayAccess(way));
	}

	protected int noThroughWayAccess( Way way )
    {
		return way.hasTag("access", "private", "no")||way.hasTag("service", "alley")?1:0;
    }
}
