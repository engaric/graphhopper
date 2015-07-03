package com.graphhopper.routing.util;

import com.graphhopper.storage.Graph;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.EdgeIteratorState;

public class EscapePrivateWeighting implements Weighting
{

	private static final double ESCAPE_WEIGHT = 1.1;
	public static final int KEY = 100;
	private Weighting innerWeighting;
	private FlagEncoder encoder;
	private Graph graph;
	private int start;
	private int end;

	public EscapePrivateWeighting( Graph graph, FlagEncoder encoder, Weighting weighting)
    {
	    super();
	    this.graph = graph;
	    this.encoder = encoder;
	    this.innerWeighting = weighting;
    }
	
	public void setFromTo(int from , int to) {
		start = from;
		end = to;
	}

	@Override
	public double getMinWeight( double distance )
	{
		return innerWeighting.getMinWeight(distance);
	}

	@Override
	public double calcWeight( EdgeIteratorState edgeState, boolean reverse, int prevOrNextEdgeId )
	{
		double calcWeight = innerWeighting.calcWeight(edgeState, reverse, prevOrNextEdgeId);
		if(encoder.getLong(edgeState.getFlags(), KEY)>0) {
			EdgeIteratorState edgeProps=null;
			if(!isStartOrEnd(edgeState) && prevOrNextEdgeId>EdgeIterator.NO_EDGE) {
				edgeProps = graph.getEdgeProps(prevOrNextEdgeId, Integer.MIN_VALUE);
			}
			if(null==edgeProps || encoder.getLong(edgeProps.getFlags(), KEY)>0) {
				calcWeight *= ESCAPE_WEIGHT;
			}
			else {
				calcWeight = Double.POSITIVE_INFINITY;
			}
		}
		return calcWeight;
	}

	private boolean isStartOrEnd( EdgeIteratorState edgeState )
    {
	    int baseNode = edgeState.getBaseNode();
		int adjNode = edgeState.getAdjNode();
		boolean startEnd = baseNode == start  || baseNode == end  || adjNode == start || adjNode == end;
		return startEnd;
    }

}
