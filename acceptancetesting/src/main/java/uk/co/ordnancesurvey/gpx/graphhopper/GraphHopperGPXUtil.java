package uk.co.ordnancesurvey.gpx.graphhopper;

import java.util.HashSet;

import org.alternativevision.gpx.beans.Route;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.Waypoint;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.beans.RouteWayPoint;

public class GraphHopperGPXUtil {

	private static final Logger LOG = LoggerFactory
			.getLogger(GraphHopperGPXUtil.class);

	private GraphHopperGPXParser parser = new GraphHopperGPXParser();

	public void parseGPXFromString(String gpxString) {

		parser.parseGPXFromString(gpxString);
	}

	public boolean isWayPointOnRoute(Waypoint aWayPoint, Route aRoute) {
		LOG.debug(aWayPoint.getExtensionData().toString());
		boolean isWayPointOnRoute = false;
		LOG.debug(aRoute.getRoutePoints().toString());
		for (Waypoint aWaypointInaRoute : aRoute.getRoutePoints()) {
			if (new RouteWayPoint(aWaypointInaRoute).equals(new RouteWayPoint(
					aWayPoint))) {
				isWayPointOnRoute = true;
				LOG.info("WayPoint " + aWayPoint + " Found In a Route" + aRoute);
				break;
			}
		}

		return isWayPointOnRoute;
	}

	public boolean isWayPointOnTrack(Waypoint aWayPoint, Track aTrack) {

		boolean isWayPointOnTrack = false;
		LOG.debug(aTrack.getTrackPoints().toString());
		for (Waypoint aWaypointInaTrack : aTrack.getTrackPoints()) {
			if (new RouteWayPoint(aWaypointInaTrack).equals(new RouteWayPoint(
					aWayPoint))) {

				isWayPointOnTrack = true;
				LOG.debug("WayPoint " + aWayPoint + " Found In a Track" + aTrack);
				break;
			}

		}

		if(!isWayPointOnTrack)
		{
			LOG.info("Track Point not found on the GPX track");
		}
		return isWayPointOnTrack;
	}

	public boolean routeContainsTurn(String turnDescription, Route aRoute) {
		LOG.debug(aRoute.toString());
		boolean routeContainsTurn = false;
		turnDescription = turnDescription.toUpperCase();

		for (Waypoint aWaypointInaRoute : aRoute.getRoutePoints()) {
			if (aWaypointInaRoute.getDescription() != null
					&& aWaypointInaRoute.getDescription().toUpperCase()
							.equals(turnDescription)) {
				routeContainsTurn = true;

				LOG.info("WayPoint " + aWaypointInaRoute
						+ " contains route instruction" + turnDescription);
				break;
			}
		}

		return routeContainsTurn;
	}

	public boolean isWayPointOnGPXRoutes(Waypoint wp) {

		boolean isWayPointOnRoute = false;

		for (Route route : getRoutes()) {
			isWayPointOnRoute = isWayPointOnRoute(wp, route);
			
			if (isWayPointOnRoute(wp, route)) {
				break;
			}

		}
		if(!isWayPointOnRoute){
			LOG.info("Way Point not found on the GPX route");
		}
		return isWayPointOnRoute;
	}

	public HashSet<Route> getRoutes() {
		return parser.getRoutes();
	}

	public HashSet<Track> getTracks() {
		return parser.getTracks();
	}

	public long getTotalRouteTime() {

		return parser.getTotalRouteTime();
	}

	public void verifyMessage(String responseMessage) {
		String actualErrorMessage = parser.getErrorMessage();
		Assert.assertTrue("Service :actual error message: " + actualErrorMessage
				+ "does not match with: " + responseMessage,
				responseMessage.equalsIgnoreCase(actualErrorMessage));

	}

	public void verifyStatusCode(int statusCode) {

		int actualstatusCode = parser.getstatusCode();
		Assert.assertTrue("Service : actual error message: " + actualstatusCode
				+ "does not match with: " + statusCode,
				(statusCode == actualstatusCode));

	}
}
