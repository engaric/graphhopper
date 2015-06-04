package uk.co.ordnancesurvey.gpx.graphhopper;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.routing.GraphHopperUIUtil;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class NearestPointServiceUtil {

	private static final Logger LOG = LoggerFactory
			.getLogger(NearestPointServiceUtil.class);

	String jsonString;
	GraphHopperUIUtil GPHopperUIUtil = new GraphHopperUIUtil();

	public String getNearestPoint(String pointA) {

		String nearestpoint = "";
		StringBuffer sb = new StringBuffer();
		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee"));
		} else {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl"));
		}

		sb.append("nearest?point=");
		sb.append(pointA);

		GPHopperUIUtil.getRouteFromServiceWithParameters(sb);

		nearestpoint = GPHopperUIUtil.getNearestPoint();

		return nearestpoint;

	}

	public String getNearestPointDistance() {
String distance= GPHopperUIUtil.getNearestPointDistance();
		return distance.toString();
	}

}
