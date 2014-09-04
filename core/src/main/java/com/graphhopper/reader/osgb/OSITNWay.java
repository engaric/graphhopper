/*
 *  Licensed to GraphHopper and Peter Karich under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for 
 *  additional information regarding copyright ownership.
 * 
 *  GraphHopper licenses this file to you under the Apache License, 
 *  Version 2.0 (the "License"); you may not use this file except in 
 *  compliance with the License. You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.reader.osgb;

import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.reader.Way;

/**
 * Represents an OSM Way
 * <p/>
 * 
 * @author Nop
 */
public class OSITNWay extends OSITNElement implements Way {
	private static final long WAY_NODE_PREFIX_MOD = 100000000000000000L;
	protected final TLongList nodes = new TLongArrayList(5);
	private String[] wayCoords;
	private long lastNode;
	private static final Logger logger = LoggerFactory
			.getLogger(OSITNWay.class);

	/**
	 * Constructor for XML Parser
	 */
	public static OSITNWay create(long id, XMLStreamReader parser)
			throws XMLStreamException {
		OSITNWay way = new OSITNWay(id);
		parser.nextTag();
		way.readTags(parser);
		way.setTag("highway", "motorway");
		logger.info(way.toString());
		return way;
	}

	public OSITNWay(long id) {
		super(id, WAY);
	}

	public TLongList getNodes() {
		return nodes;
	}

	@Override
	public String toString() {
		return "Way (" + getId() + ", " + nodes.size() + " nodes)";
	}

	@Override
	protected void parseCoords(String lineDefinition) {
		String[] lineSegments = lineDefinition.split(" ");
		wayCoords = Arrays
				.copyOfRange(lineSegments, 1, lineSegments.length -1);
		logger.info(toString() + " "  + ((wayCoords.length == 0)?"0":wayCoords[0]));
	}

	@Override
	protected void parseNetworkMember(String elementText) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void addDirectedNode(String nodeId, String grade,
			String orientation) {
		String idStr = nodeId.substring(5);
		if (null != grade) {
			idStr = grade + idStr;
		}
		long id = Long.parseLong(idStr);

		if (0 == nodes.size()) {
			nodes.add(id);
		} else {
			addWayNodes();
			nodes.add(id);
		}
		logger.info(toString());
	}

	private void addWayNodes() {
		for (int i = 1; i <= wayCoords.length; i++) {
			long idPrefix = i * WAY_NODE_PREFIX_MOD;
			long extraId = idPrefix + getId();
			nodes.add(extraId);
		}
	}

	@Override
	protected void addDirectedLink(String nodeId, String orientation) {
		throw new UnsupportedOperationException();

	}

	public List<OSITNNode> evaluateWayNodes() {
		List<OSITNNode> wayNodes = new ArrayList<>();

		for (int i = 0; i < wayCoords.length; i++) {
			String wayCoord = wayCoords[i];
			long idPrefix = (i+1) * WAY_NODE_PREFIX_MOD;
			long id = idPrefix + getId();
			OSITNNode wayNode = new OSITNNode(id);
			wayNode.parseCoords(wayCoord);
			wayNodes.add(wayNode);
		}
		return wayNodes;
	}
}