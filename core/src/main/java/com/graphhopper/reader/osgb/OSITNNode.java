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

import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.api.srs.GeodeticPoint;
import uk.co.ordnancesurvey.api.srs.MapPoint;
import uk.co.ordnancesurvey.api.srs.OSGrid2LatLong;
import uk.co.ordnancesurvey.api.srs.OutOfRangeException;

import com.graphhopper.reader.Node;
import com.graphhopper.util.PointAccess;

/**
 * Represents an OSM Node
 * <p/>
 * 
 * @author Nop
 */
public class OSITNNode extends OSITNElement implements Node {
	private double lat;
	private double lon;
	private static OSGrid2LatLong coordConvertor = new OSGrid2LatLong();
	private static final Logger logger = LoggerFactory
			.getLogger(OSITNNode.class);
	private boolean[] clones = {false,false,false,false};

	public static OSITNNode create(long id, XMLStreamReader parser)
			throws XMLStreamException {
		OSITNNode node = new OSITNNode(id);

		parser.nextTag();
		node.readTags(parser);
		return node;
	}

	public OSITNNode(long id, PointAccess pointAccess, int accessId) {
		super(id, NODE);

		this.lat = pointAccess.getLatitude(accessId);
		this.lon = pointAccess.getLongitude(accessId);
		if (pointAccess.is3D())
			setTag("ele", pointAccess.getElevation(accessId));
	}
	
	public OSITNNode(long id) {
		super(id, NODE);

	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getEle() {
		Object ele = getTags().get("ele");
		if (ele == null)
			// return Double.NaN;
			return 1d;
		return (Double) ele;
	}

	@Override
	public void setTag(String name, Object value) {
		if ("ele".equals(name)) {
			if (value == null)
				value = null;
			else if (value instanceof String) {
				String str = (String) value;
				str = str.trim().replaceAll("\\,", ".");
				if (str.isEmpty())
					value = null;
				else
					try {
						value = Double.parseDouble(str);
					} catch (NumberFormatException ex) {
						return;
					}
			} else
				// force cast
				value = ((Number) value).doubleValue();
		}
		super.setTag(name, value);
	}

	@Override
	public String toString() {
		StringBuilder txt = new StringBuilder();
		txt.append("Node: ");
		txt.append(getId());
		txt.append(" lat=");
		txt.append(getLat());
		txt.append(" lon=");
		txt.append(getLon());
		if (!getTags().isEmpty()) {
			txt.append("\n");
			txt.append(tagsToString());
		}
		return txt.toString();
	}

	@Override
	public void parseCoords(String elementText) {
		String elementSeparator = ",";
		parseCoordinateString(elementText, elementSeparator);
	}

	public void parseCoordinateString(String elementText,
			String elementSeparator) {
		String[] split = elementText.split(elementSeparator);

		if(3==split.length) {
			setTag("ele", split[2]);
		}
		Double easting = Double.parseDouble(split[0]);
		Double northing = Double.parseDouble(split[1]);
		GeodeticPoint wgs84 = toWGS84(easting, northing);
		lat = wgs84.getLatAngle();
		lon = wgs84.getLongAngle();
		logger.info(toString());
	}

	@Override
	protected void parseNetworkMember(String elementText) {
		throw new UnsupportedOperationException("Nodes should not have members");
	}

	@Override
	protected void addDirectedNode(String nodeId, String grade, String orientation) {
		throw new UnsupportedOperationException(
				"Nodes should not have directed nodes");
	}
	
	@Override
	protected void addDirectedLink(String nodeId, String orientation) {
		throw new UnsupportedOperationException(
				"Nodes should not have directed links");
	}
	
	private static GeodeticPoint toWGS84(double easting, double northing) {
		MapPoint osgb36Pt = new MapPoint(easting, northing);
		GeodeticPoint wgs84Pt = null;
		try {
			wgs84Pt = coordConvertor.transformHiRes(osgb36Pt);
		} catch (OutOfRangeException ore) {
			//REALLY? 
			//TODO should this be where the lowres route goes?
		}
		if (null==wgs84Pt) {
			try {
				wgs84Pt = coordConvertor.transformLoRes(osgb36Pt);
			} catch(OutOfRangeException ore) {
				
			}
		}
		return wgs84Pt;
	}

	public OSITNNode gradeClone(long nodeId) {
		logger.warn("CLONING:" + nodeId);
		OSITNNode clone = new OSITNNode(nodeId);
		Map<String, Object> tags = this.getTags();
		clone.setTags(tags);
		clone.lat = this.lat;
		clone.lon = this.lon;
		return clone;
	}

	@Override
	protected void parseCoords(int dimensions, String lineDefinition) {
		throw new UnsupportedOperationException();
	}

}
