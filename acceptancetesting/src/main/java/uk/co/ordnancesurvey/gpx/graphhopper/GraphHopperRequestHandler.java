package uk.co.ordnancesurvey.gpx.graphhopper;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphHopperRequestHandler {

	private static final Logger LOG = LoggerFactory
			.getLogger(GraphHopperRequestHandler.class);
	private GraphHopperJSONParser parser = new GraphHopperJSONParser();

	public void dispatchServiceRequest(String routeType, String avoidances, String routeOptions,
			String[] string) {

		String vehicle = "";
		String routeOption = "";

		if (routeOptions.split(",").length > 1) {
			vehicle = routeOptions.split(",")[0];
			routeOption = routeOptions.split(",")[1];
		} else {
			vehicle = routeOptions;
		}
		// Set up the URL
		String jsonResponse = "";
		String coordinateString = "";
		String graphHopperUrl;

		for (int i = 0; i < string.length; i++) {

			coordinateString = coordinateString + "&point=" + string[i];

		}

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			graphHopperUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee");
		} else {
			graphHopperUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl");
		}

		String apikey = IntegrationTestProperties.getTestProperty("apiKey");
		if (vehicle.equalsIgnoreCase("mountainbike")) {
			vehicle = "mtb";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(graphHopperUrl);
		sb.append("route?");
		if (routeType != null) {
			sb.append("type=");
			sb.append(routeType);
		}
		sb.append("&vehicle=");
		sb.append(vehicle);

		sb.append(coordinateString);
		sb.append("&apikey=");
		sb.append(apikey);
		sb.append("&points_encoded=false");

		if (!avoidances.equals("")) {
			sb.append("&avoidances=" + avoidances);

			if (routeOption.isEmpty()) {
				routeOption = "fastavoid";
			}
		}

		else {

			if (routeOption.isEmpty()) {
				routeOption = "fastest";
			}

		}

		sb.append("&weighting=");

		sb.append(routeOption);
		GraphHopperGPXParserRouteTest GPHService = new GraphHopperGPXParserRouteTest();
		try {
			CloseableHttpResponse httpResponse = GPHService
					.sendAndGetResponse(sb.toString());
			jsonResponse = IOUtils.toString(httpResponse.getEntity()
					.getContent(), "UTF-8");

		} catch (IOException e) {
			LOG.info("Exception raised whilst attempting to call graphhopper server "
					+ e.getMessage());
		}

		if (jsonResponse != null && jsonResponse.length() > 0) {
			parser.parseJSONFromString(jsonResponse);
		}

	}
}