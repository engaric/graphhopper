package com.graphhopper.reader;

import com.graphhopper.routing.util.TurnCostEncoder;
import com.graphhopper.util.EdgeExplorer;

import java.util.Collection;

public interface TurnRelation {

	long getOsmIdFrom();

	long getOsmIdTo();

	/**
	 * transforms this relation into a collection of node cost entries
	 * <p>
	 * @param edgeOutExplorer an edge filter which only allows outgoing edges
	 * @param edgeInExplorer an edge filter which only allows incoming edges
	 * @return a collection of node cost entries which can be added to the graph later
	 */
	Collection<ITurnCostTableEntry> getRestrictionAsEntries(
			TurnCostEncoder encoder, EdgeExplorer edgeOutExplorer,
			EdgeExplorer edgeInExplorer, DataReader osmReader);

}