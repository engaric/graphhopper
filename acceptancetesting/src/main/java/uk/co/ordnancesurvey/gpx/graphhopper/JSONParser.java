package uk.co.ordnancesurvey.gpx.graphhopper;

import java.util.HashSet;

import org.alternativevision.gpx.beans.Waypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.beans.RouteWayPoint;
import uk.co.ordnancesurvey.gpx.extensions.ExtensionConstants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JSONParser {

	private JsonElement jElement;
	private JsonObject jObject;
	private JsonArray paths;
	private JsonArray instructions;
	//private JsonObject info;

	private static final Logger LOG = LoggerFactory.getLogger(JSONParser.class);

	String JSONString;
	HashSet<Waypoint> wayPoints = new HashSet<Waypoint>();

	/**
	 * Adds a WayPoint to the Instructions ArrayList(WayPoint List)
	 * 
	 * @param waypoint
	 * 
	 */
	public void addWayPoint(Waypoint w) {

		wayPoints.add(w);
	}

	/**
	 * @return List of WayPoints in a JSON Route String
	 */
	public HashSet<Waypoint> getWayPoints() {
		return wayPoints;
	}

	public void parse(String jsonString) {

		JsonParser parser = new JsonParser();

		try {
			jElement = parser.parse(jsonString);
			jObject = jElement.getAsJsonObject();
			paths = jObject.getAsJsonArray("paths");
			info = jObject.getAsJsonObject("info");

			instructions = paths.get(0).getAsJsonObject()
					.getAsJsonArray("instructions");
			extractWayPoints();

		}

		catch (Exception e) {
			LOG.info("Invalid JSON String :"+ e.getMessage());
		}

	}

	public JsonArray getPaths() {
		return paths;
	}

	public JsonArray getInstructions() {
		return instructions;
	}

	public HashSet<Waypoint> getJsonWayPoints() {
		return getWayPoints();
	}

	public void extractWayPoints() {
		for (int i = 0; i < instructions.size(); i++) {
			Waypoint w = new Waypoint();

			JsonObject instruction = instructions.get(i).getAsJsonObject();

			JsonPrimitive description = instruction.getAsJsonPrimitive("text");
			JsonPrimitive time = instruction.getAsJsonPrimitive("time");
			JsonPrimitive distance = instruction.getAsJsonPrimitive("distance");

			double distance_rounding = Double.parseDouble(distance.toString());

			distance_rounding = Math.round(distance_rounding * 10) / 10.0;

			JsonPrimitive azimuth = instruction.getAsJsonPrimitive("azimuth");
			JsonPrimitive annotation_text = instruction
					.getAsJsonPrimitive("annotation_text");
			JsonArray interval = instruction.getAsJsonArray("interval");
			int coordinateIndex = Integer.parseInt(interval.get(0).toString());
			JsonElement s = getJSONCoordinates(coordinateIndex);
			Double longitude = Double.parseDouble(s.getAsJsonArray().get(0)
					.toString());
			Double latitude = Double.parseDouble(s.getAsJsonArray().get(1)
					.toString());
			w.setLongitude(longitude);
			w.setLatitude(latitude);
			w.setDescription(description.toString());
			w.addExtensionData(ExtensionConstants.DISTANCE,
					String.valueOf(distance_rounding));
			w.addExtensionData(ExtensionConstants.TIME, time.toString());

			LOG.info("azimuth :" + azimuth);
			LOG.info("descritption: " + description);
			LOG.info("time :" + time);
			LOG.info("distance :" + distance);
			if (null != annotation_text) {
				w.addExtensionData("Annotation_text", annotation_text
						.getAsString().trim());
				LOG.info("annotation_text: "
						+ annotation_text.getAsString().trim());
			}
			LOG.info("Coordinates : " + w.getLatitude() + ","
					+ w.getLongitude());

			addWayPoint(w);
		}

	}

	public JsonElement getJSONCoordinates(int coordinateIndex) {

		JsonObject points = paths.get(0).getAsJsonObject()
				.getAsJsonObject("points");
		JsonArray coordinates = points.getAsJsonObject().getAsJsonArray(
				"coordinates");

		return coordinates.get(coordinateIndex);
	}

	public boolean isWayPointinPath(Waypoint w) {
		boolean iswaypointinPath = false;

		for (Waypoint wp : getWayPoints()) {

			RouteWayPoint k = new RouteWayPoint(wp);
			iswaypointinPath = k.equals(new RouteWayPoint(w));
			if (iswaypointinPath) {
				break;
			}
		}

		return iswaypointinPath;

	}

	public long getTotalRouteTime() {
		JsonPrimitive totalTime = paths.get(0).getAsJsonObject()
				.getAsJsonPrimitive("time");
		return Long.parseLong(totalTime.toString());
	}

	public String getErrorMessage() {
		
		JsonObject error= jObject.getAsJsonObject("error");
	//	JsonArray errors = info.getAsJsonArray("errors");
		//JsonPrimitive errorMessage = errors.get(0).getAsJsonObject()
		//		.getAsJsonPrimitive("message");
		JsonPrimitive errorMessage = error.getAsJsonObject()
				.getAsJsonPrimitive("message");
		return errorMessage.getAsString();

	}

	public int getStatusCode() {
	//	JsonArray errors = info.getAsJsonArray("errors");
		
		JsonObject error= jObject.getAsJsonObject("error");
	//	JsonArray errors = info.getAsJsonArray("errors");

		JsonPrimitive statusCode = error.getAsJsonPrimitive("statuscode");
		//JsonPrimitive errorMessage = errors.get(0).getAsJsonObject()
		//		.getAsJsonPrimitive("statuscode");
		return statusCode.getAsInt();

	}

	public String getNearestPoint() {

		JsonArray nearestPoint = jElement.getAsJsonObject().getAsJsonArray(
				"coordinates");

		return nearestPoint.get(1).getAsString() + ","
				+ nearestPoint.get(0).getAsString();

	}

	public String getNearestPointDistance() {

		JsonPrimitive distance = jElement.getAsJsonObject().getAsJsonPrimitive(
				"distance");
		return distance.toString();
	}

	public void parseCoordinatesFromJson() {

		JsonObject points = paths.get(0).getAsJsonObject()
				.getAsJsonObject("points");
		JsonArray coordinates = points.getAsJsonObject().getAsJsonArray(
				"coordinates");

		for (JsonElement jsonElement : coordinates) {
			Waypoint w = new Waypoint();
			Double longitude = Double.parseDouble(jsonElement.getAsJsonArray()
					.get(0).toString());
			Double latitude = Double.parseDouble(jsonElement.getAsJsonArray()
					.get(1).toString());
			w.setLongitude(longitude);
			w.setLatitude(latitude);
			addWayPoint(w);

		}

	}
}
