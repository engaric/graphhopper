package com.graphhopper.routing.util;

import com.graphhopper.util.EdgeIteratorState;

public class BanPrivateWeighting implements Weighting
{

	private FlagEncoder encoder;
	private Weighting innerWeighting;

	public BanPrivateWeighting( FlagEncoder encoder, Weighting weighting )
	{
		this.encoder = encoder;
		this.innerWeighting = weighting;
	}

	@Override
	public double getMinWeight( double distance )
	{
		return innerWeighting.getMinWeight(distance);
	}

	@Override
	public double calcWeight( EdgeIteratorState edgeState, boolean reverse, int prevOrNextEdgeId )
	{
		if(encoder.getLong(edgeState.getFlags(), EscapePrivateWeighting.KEY)>0) {
			return Double.POSITIVE_INFINITY;
		}
		return innerWeighting.calcWeight(edgeState, reverse, prevOrNextEdgeId);
	}

}
