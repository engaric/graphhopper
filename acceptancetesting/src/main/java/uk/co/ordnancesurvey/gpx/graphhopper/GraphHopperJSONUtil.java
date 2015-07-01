package uk.co.ordnancesurvey.gpx.graphhopper;

import java.util.HashSet;

import org.alternativevision.gpx.beans.Waypoint;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.beans.RouteWayPoint;
import uk.co.ordnancesurvey.gpx.extensions.ExtensionConstants;

public class GraphHopperJSONUtil {

	JSONParser parser = new JSONParser();

	private static final Logger LOG = LoggerFactory
			.getLogger(GraphHopperJSONUtil.class);

	public boolean isWayPointinpath(Waypoint w) {

		boolean iswaypointinPath = false;

		for (Waypoint wp : parser.getJsonWayPoints()) {

			RouteWayPoint k = new RouteWayPoint(wp);
			iswaypointinPath = k.equals(new RouteWayPoint(w));
			if (iswaypointinPath) {
				break;
			}

		}

		if (!iswaypointinPath) {
			LOG.info("Way point not found in the JSON Route");
		}

		return iswaypointinPath;

	}

	public boolean isWayPointinPath(Waypoint we, HashSet<Waypoint> wa) {
		boolean iswaypointinPath = false;

		for (Waypoint waypoint : wa) {

			if (new RouteWayPoint(we).equals(new RouteWayPoint(waypoint))) {
				iswaypointinPath = true;
				LOG.info("WayPoint " + we + " Found In a JSON Route");
			}
			if (iswaypointinPath) {
				break;
			}
		}

		if (!iswaypointinPath) {
			LOG.info("Way point not found in the JSON Route");
		}
		return iswaypointinPath;
	}

	public void parse(String jsonResponse) {

		parser.parse(jsonResponse);
	}

	public Waypoint buildWayPointForJson(String wayPoint_Coordinates,
			String wayPointDescription, String time, String distance,
			String avoidance) {
		Waypoint w = new Waypoint();
		String waypoint[] = wayPoint_Coordinates.split(",");
		w.setLatitude(new Double(waypoint[0]));
		w.setLongitude(new Double(waypoint[1]));
		w.setDescription(wayPointDescription);
		w.addExtensionData(ExtensionConstants.DISTANCE, distance);
		w.addExtensionData(ExtensionConstants.TIME, time);
		w.addExtensionData("Annotation_text", avoidance.trim());
		return w;

	}

	public String nearestPointJSONParser(String jsoString) {

		return parser.getNearestPoint();

	}

	public long getTotalRouteTime() {
		return parser.getTotalRouteTime();
	}

	public void verifyMessage(String responseMessage) {
		String actualErrorMessage = parser.getErrorMessage().trim();
		Assert.assertTrue(" Service : actual error message:"
				+ actualErrorMessage + " does not match with: "
				+ responseMessage,
				responseMessage.trim().equalsIgnoreCase(actualErrorMessage));

	}

	public void verifyStatusCode(int statusCode) {
		int actualStatusCode = parser.getStatusCode();
		Assert.assertTrue("Service : actual error message: " + actualStatusCode
				+ " does not match with: " + statusCode,
				(actualStatusCode == statusCode));

	}

	public HashSet<Waypoint> getJsonCoordinatesAsHashSet() {

		parser.parseCoordinatesFromJson();
		return parser.getJsonWayPoints();
	}

	public String getNearestPoint() {

		return parser.getNearestPoint();

	}

	public String getNearestPointDistance() {

		return parser.getNearestPointDistance();
	}
	
	public Boolean isRoute()
	{
		Boolean isRoute=false;
		if(null!=parser.getPaths())
		{
			isRoute= true;
		}
			return isRoute;
		}
	}