package uk.co.ordnancesurvey.gpx.graphhopper;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class NearestPointServiceUtil {

	private static final Logger LOG = LoggerFactory
			.getLogger(NearestPointServiceUtil.class);
	
	 String jsonString;

	public  String getNearestPoint(String pointA) {

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
		GraphHopperGPXParserRouteTest GPHService = new GraphHopperGPXParserRouteTest();

		try {
			CloseableHttpResponse httpResponse = GPHService
					.sendAndGetResponse(sb.toString());

			jsonString= IOUtils.toString(httpResponse.getEntity()
					.getContent(), "UTF-8");

			GraphHopperJSONParser jsonParser = new GraphHopperJSONParser();
			nearestpoint = jsonParser.nearestPointJSONParser(jsonString);

		} catch (IOException e) {
			LOG.info("Exception raised whilst attempting to call graphhopper server "
					+ e.getMessage());
		}
		return nearestpoint;

	}
	
	
	public String getNearestPointDistance() {
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(jsonString);
		JsonPrimitive distance = je.getAsJsonObject().getAsJsonPrimitive(
				"distance");
		return distance.toString();
	}

}
