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
package com.graphhopper.http;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.DistanceCalc;
import com.graphhopper.util.Helper;
import com.graphhopper.util.shapes.GHPoint;
import com.graphhopper.util.shapes.GHPoint3D;

/**
 * @author svantulden
 */
public class NearestServlet extends GHBaseServlet
{
	@Inject
	private GraphHopper hopper;

	private final DistanceCalc calc = Helper.DIST_EARTH;

	@Override
	public void doGet( HttpServletRequest httpReq, HttpServletResponse httpRes )
	        throws ServletException, IOException
	{
		GHResponse ghRsp = null;
		Map<String, Object> map = new HashMap<>();
		try
		{
			ApiResource.NEAREST.checkAllRequestParameters(httpReq);

			List<GHPoint> infoPoints = getPoints(httpReq, "point");
			if (infoPoints.size() > 1)
			{
				throw new InvalidParameterException(
				        "Only one point should be specified and it must be a comma separated coordinate in WGS84 projection.");
			}

			boolean enabledElevation = getBooleanParam(httpReq, "elevation", false);

			GHPoint place = infoPoints.get(0);
			LocationIndex index = hopper.getLocationIndex();
			QueryResult qr = index.findClosest(place.lat, place.lon, EdgeFilter.ALL_EDGES);
			if (!qr.isValid())
			{
				map.put("error", "Nearest point cannot be found!");
			} else
			{
				GHPoint3D snappedPoint = qr.getSnappedPoint();
				map.put("type", "Point");

				JSONArray coord = new JSONArray();
				coord.put(snappedPoint.lon);
				coord.put(snappedPoint.lat);

				if (hopper.hasElevation() && enabledElevation)
					coord.put(snappedPoint.ele);

				map.put("coordinates", coord);

				// Distance from input to snapped point in meters
				map.put("distance",
				        calc.calcDist(place.lat, place.lon, snappedPoint.lat, snappedPoint.lon));
			}
		} catch (NoSuchParameterException | MissingParameterException | InvalidParameterException e)
		{
			ghRsp = new GHResponse().addError(e);
		}

		if (ghRsp != null && ghRsp.hasErrors())
		{
			processResponseErrors(ghRsp, map, internalErrorsAllowed);
			writeJsonError(httpRes, SC_BAD_REQUEST, new JSONObject(map));
		} else
		{
			writeJson(httpReq, httpRes, new JSONObject(map));
		}
	}
}
