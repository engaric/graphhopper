package uk.co.ordnancesurvey.gpx.graphhopper;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.alternativevision.gpx.GPXParser;
import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Route;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.Waypoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.extensions.RoutePointAzimuthParser;
import uk.co.ordnancesurvey.gpx.extensions.RoutePointDirectionParser;
import uk.co.ordnancesurvey.gpx.extensions.RoutePointDistanceParser;
import uk.co.ordnancesurvey.gpx.extensions.RoutePointTimeParser;

public class GraphHopperGPXParser {

	private static final Logger LOG = LoggerFactory
			.getLogger(GraphHopperGPXParser.class);

	private GPXParser gpxParser;
	private GPX gpx;

	public GraphHopperGPXParser() {
		init();
	}

	public static GraphHopperGPXParser getParserForgpxString(String gpxString) {

		GraphHopperGPXParser instance = new GraphHopperGPXParser();
		instance.parseGPXFromString(gpxString);

		return instance;
	}

	private void init() {

		gpxParser = new GPXParser();
		RoutePointDistanceParser rPDEP = new RoutePointDistanceParser();
		RoutePointTimeParser rPTEP = new RoutePointTimeParser();
		RoutePointAzimuthParser rPAEP = new RoutePointAzimuthParser();
		RoutePointDirectionParser rPDIEP = new RoutePointDirectionParser();
		gpxParser.addExtensionParser(rPDEP);
		gpxParser.addExtensionParser(rPTEP);
		gpxParser.addExtensionParser(rPAEP);
		gpxParser.addExtensionParser(rPDIEP);

	}

	public static GraphHopperGPXParser getParserForgpxFileName(
			String gpxFileName) {
		GraphHopperGPXParser instance = new GraphHopperGPXParser();
		instance.parseGPXFromFile(gpxFileName);

		return instance;
	}

	public void parseGPXFromString(String gpxString) {
		if (gpxString != null && gpxString.length() > 0) {
			try {
				gpx = gpxParser.parseGPX(new ByteArrayInputStream(gpxString
						.getBytes()));
			} catch (Exception e) {
				LOG.info("Invalid File supplied for parsing " + e.getMessage());
			}
		}
	}

	private void parseGPXFromFile(String gpxFileName) {
		if (gpxFileName != null && gpxFileName.length() > 0) {
			try {
				gpx = gpxParser.parseGPX(new FileInputStream(gpxFileName));

			} catch (Exception e) {
				LOG.info("Invalid File supplied for parsing " + e.getMessage());
			}
		}
	}

	public HashSet<Route> getRoutes() {
		return gpx.getRoutes();
	}

	public HashSet<Track> getTracks() {
		return gpx.getTracks();
	}

	public long getTotalRouteTime() {

		long totalTimeInSceonds = 0;
		HashSet<Track> tracks = gpx.getTracks();
		Track track = (Track) tracks.toArray()[0];
		ArrayList<Waypoint> trackPoints = track.getTrackPoints();

		if (trackPoints.size() > 0) {
			Waypoint sttp = trackPoints.get(0);
			Waypoint endtp = trackPoints.get(trackPoints.size() - 1);
			LOG.info("Start Time is " + sttp.getTime().toString());
			LOG.info("End Time is " + endtp.getTime().toString());
			totalTimeInSceonds = endtp.getTime().getTime()
					- sttp.getTime().getTime();
		}

		return totalTimeInSceonds;
	}

	public String getErrorMessage() {

		return (String) gpx.getExtensionData("errorMessage");

	}

	public int getstatusCode() {

		return (Integer.parseInt((String) gpx.getExtensionData("statusCode")));

	}

}
