package com.graphhopper.routing.util;

import com.graphhopper.util.EdgeIteratorState;

public class PrivateEdgeFilter extends DefaultEdgeFilter
{

	public PrivateEdgeFilter( FlagEncoder encoder )
    {
	    super(encoder);
    }

	@Override
	public boolean accept( EdgeIteratorState edgeState )
	{
		if(encoder.getLong(edgeState.getFlags(), EscapePrivateWeighting.KEY)>0) {
			return false;
		}
		return super.accept(edgeState);
	}

}
