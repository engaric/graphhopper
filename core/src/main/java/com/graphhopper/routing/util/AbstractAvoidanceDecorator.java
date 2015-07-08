package com.graphhopper.routing.util;

import com.graphhopper.reader.Way;
import com.graphhopper.util.InstructionAnnotation;
import com.graphhopper.util.Translation;

public abstract class AbstractAvoidanceDecorator implements EncoderDecorator
{

	protected EncodedValue wayTypeEncoder;
	protected int originalShift;
	public static final int KEY = 303;

	protected abstract void defineEncoder( int shift );

	protected abstract EdgeAttribute[] getEdgeAttributesOfInterest();

	@Override
	public int defineWayBits( int shift )
	{
		originalShift = shift;
		defineEncoder(shift);
		shift += wayTypeEncoder.getBits();
		return shift;
	}

	@Override
	public int getOriginalShift()
	{
		return originalShift;
	}

	@Override
	public InstructionAnnotation getAnnotation( long flags, Translation tr )
	{
		long wayType = wayTypeEncoder.getValue(flags);
		String wayName = getWayName(wayType, tr);
		return new InstructionAnnotation(0, wayName);
	}

	@Override
	public boolean supports( int key )
	{
		return key == KEY;
	};

	@Override
	public long getLong( long flags )
	{
		return wayTypeEncoder.getValue(flags);
	}

	@Override
	public double getDouble( long flags )
	{
		double avoidanceType = wayTypeEncoder.getValue(flags);
		return avoidanceType;
	}

	@Override
	public long handleWayTags( Way way )
	{
		long avoidanceValue = 0;

		for (EdgeAttribute aType : getEdgeAttributesOfInterest())
		{
			if (aType.isValidForWay(way))
			{
				avoidanceValue += aType.getValue();
			}
		}
		return wayTypeEncoder.setValue(0L, avoidanceValue);
	}

	private String getWayName( long wayType, Translation tr )
	{
		StringBuilder wayName = new StringBuilder();
		for (EdgeAttribute aType : getEdgeAttributesOfInterest())
		{
			if ((wayType & aType.getValue()) == aType.getValue())
			{
				wayName.append(aType.name());
				wayName.append(", ");
			}
		}
		int length = wayName.length();
		if(length>0) {
			wayName.delete(length -2, length);
		}
		return wayName.toString();
	}

	@Override
	public long getBitMask( String[] attributes )
	{
		long avoidanceValue = 0;
		for (EdgeAttribute aType : getEdgeAttributesOfInterest())
		{
			if (aType.representedIn(attributes))
			{
				avoidanceValue += aType.getValue();
			}
		}
		return avoidanceValue;
	}

	public String[] getEdgeAttributesOfInterestNames()
	{
		EdgeAttribute[] edgeAttributes = getEdgeAttributesOfInterest();
		String[] results = new String[edgeAttributes.length];
		for (int i = 0; i < edgeAttributes.length; i++)
		{
			results[i] = edgeAttributes[i].toString();
		}
		return results;
	}

}
