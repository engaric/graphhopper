package uk.co.ordnancesurvey.api.srs;

import org.geotools.geometry.DirectPosition2D;

public class OSLatLong implements LatLong {
	double lat;
	double lon;
	
	public OSLatLong(GeodeticPoint pos) {
		lat = pos.getLatAngle();
		lon = pos.getLongAngle();
	}

	@Override
	public double getLatAngle() {
		return lat;
	}

	@Override
	public double getLongAngle() {
		return lon;
	}

}
