package uk.co.ordnancesurvey.routing;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;

import uk.co.ordnancesurvey.gpx.graphhopper.HttpClientUtils;
import uk.co.ordnancesurvey.gpx.graphhopper.IntegrationTestProperties;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GraphHopperHooks {
	GraphHopperUIUtil graphUiUtil;

	String instruction;
	String nearestPoint = "";
	String Distance = "";

	private String routeResponse;
	private String routeResponsecode;
	private String routeResponseMessage;
	// private Map<String,String> requestParameters= new HashMap<>();
	private Map<String, ArrayList<String>> requestParameters = new HashMap<String, ArrayList<String>>();

	@Given("^I request a nearest point from  \"([^\"]*)\" from Nearest Point API$")
	public void I_request_a_nearest_point_from_from_Nearest_Point_API(
			String pointA) {

		graphUiUtil = (IntegrationTestProperties
				.getTestPropertyBool("viaApigee") == true) ? new GraphHopperUIUtil(
				IntegrationTestProperties
						.getTestProperty("graphHopperWebUrlViaApigee"))
				: new GraphHopperUIUtil(
						IntegrationTestProperties
								.getTestProperty("graphHopperWebUrl"));

		nearestPoint = graphUiUtil.nearestPointService(pointA);
		Distance = graphUiUtil.nearestPointDistance();

	}

	@Then("^I should be able to verify the nearest point to be \"([^\"]*)\" at a distance of \"([^\"]*)\"$")
	public void I_should_be_able_to_verify_the_nearest_point_to_be(
			String pointB, String distance) {

		Assert.assertTrue("******Expected nearest Point " + pointB
				+ " is not matching with " + nearestPoint + "********",
				pointB.equals(nearestPoint));
		Assert.assertTrue("******Expected nearest Point distance " + distance
				+ " is not matcching with " + Distance,
				Distance.equals(distance));

	}

	@Then("^I should be able to verify the \"([^\"]*)\" waypoint \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" on the route map$")
	public void I_should_be_able_to_verify_the_waypoint_on_the_route_map(
			String wayPointIndex, String wayPoint_Coordinates,
			String wayPointDescription, String azimuth, String direction,
			String time, String distance) {

		graphUiUtil.isWayPointonRouteMap(wayPointIndex, wayPoint_Coordinates,
				wayPointDescription, azimuth, direction, time, distance, "");

	}

	@Then("^I should be able to verify the waypoints on the route map:")
	public void I_should_be_able_to_verify_the_waypoints_on_the_route_map(
			List<Map<String, String>> wayPointList) {
		Assert.assertTrue(
				"Waypoint not found on the route where it was expected",
				graphUiUtil.isWayPointonRouteMap(wayPointList));

	}

	@Then("^I should be able to verify the waypoints not on the route map:")
	public void I_should_be_able_to_verify_the_not_waypoints_on_the_route_map(
			List<Map<String, String>> wayPointList) {

		Assert.assertFalse(
				"Waypoint found on the route where it was not expected",
				graphUiUtil.isWayPointNotonRouteMap(wayPointList));
		// graphUiUtil.isWayPointNotonRouteMap(wayPointList);

	}

	@Then("^The total route time should be not more than \"([^\"]*)\"$")
	public void The_total_route_time_should_be_not_more_than(
			String totalRouteTime) throws ParseException {
		graphUiUtil.verifyTotalRouteTime(totalRouteTime);

	}

	@Then("^I should be able to verify the trackPoints on the route map:")
	public void I_should_be_able_to_verify_the_trackpoints_on_the_route_map(
			List<Map<String, String>> trackPointsList) throws ParseException {

		graphUiUtil.isTrackPointonRouteMap(trackPointsList);

	}

	@Then("^I should be able to verify the trackPoints not on the route map:")
	public void I_should_be_able_to_verify_the_trackpoints_not_on_the_route_map(
			List<Map<String, String>> trackPointsList) throws ParseException {

		graphUiUtil.isTrackPointNotonRouteMap(trackPointsList);

	}

	@After
	public void closeBrowser(Scenario sc) {

		if (sc.isFailed()) {

			try {
				byte[] screeenshot = graphUiUtil.takescreenAsBiteArray();
				sc.embed(screeenshot, "image/png");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		graphUiUtil.logout();
		System.out.println("closed");

	}

	@Given("^I request a route between points with \"([^\"]*)\" from RoutingAPI and avoid \"([^\"]*)\"$")
	public void getRouteWithAvoidancesintermediatepoints(String routeOptions,
			String avoidances, DataTable dt) throws InterruptedException {

		List<List<String>> data = dt.raw();

		String[] points = new String[data.get(1).size()];
		points = data.get(1).toArray(points);

		graphUiUtil = new GraphHopperUIUtil(
				IntegrationTestProperties.getTestProperty("graphHopperWebUrl"));

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeOptions, avoidances, points);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromServiceWithAvoidance(routeOptions,
					avoidances, points);
			break;
		default:

			if (points[0].split(",").length == 2) {
				graphUiUtil.getRouteFromServiceWithAvoidance(routeOptions,
						avoidances, points);
				graphUiUtil.getRouteFromUI(routeOptions, avoidances, points);
			} else {
				graphUiUtil.getRouteFromUI(routeOptions, avoidances, points);
			}

			break;

		}
	}

	@Given("^I have ([^\"]*) as \"([^\"]*)\"$")
	public void setParameters_for_RoutingRequest(String paramName,
			String paramValue) {

		addParameter(paramName, paramValue);
	}

	private void addParameter(String key, String value) {
		ArrayList<String> tempList = null;
		if (requestParameters.containsKey(key)) {
			tempList = requestParameters.get(key);
			if (tempList == null)
				tempList = new ArrayList<String>();
			tempList.add(value);
		} else {
			tempList = new ArrayList<String>();
			tempList.add(value);
		}
		requestParameters.put(key, tempList);
	}

	@Given("^I have route points as$")
	public void setRoutingpoints(DataTable dt) {
		List<List<String>> data = dt.raw();

		String[] points = new String[data.get(1).size()];
		points = data.get(1).toArray(points);

		for (int i = 0; i < points.length; i++) {
			addParameter("point", points[i]);
		}

	}

	@Then("^I should be able to verify the responseCode as \"([^\"]*)\"$")
	public void I_should_be_able_to_verify_the_responseCode_as(
			String responseCode) {
		Assert.assertTrue(routeResponsecode
				+ "response code did not match with " + responseCode,
				routeResponsecode.equals(responseCode));

	}

	@Then("^I should be able to verify the response message as \"([^\"]*)\"$")
	public void I_should_be_able_to_verify_the_response_message_as(
			String responseMessage) {
		Assert.assertTrue(routeResponseMessage
				+ "response code did not match with " + responseMessage,
				routeResponseMessage.equals(responseMessage));
	}

	@When("^I request for a route$")
	public void I_request_for_route() {

		graphUiUtil = new GraphHopperUIUtil(
				IntegrationTestProperties.getTestProperty("graphHopperWebUrl"));

		StringBuffer sb = new StringBuffer();

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee"));

		} else {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl"));
		}
		sb.append("route?");
		for (Entry<String, ArrayList<String>> entry : requestParameters
				.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			for (String string : value) {
				sb.append("&" + key + "=" + string);
			}

		}

		if (IntegrationTestProperties.getTestProperty("testON")
				.equalsIgnoreCase("Service")) {

			try {

				CloseableHttpClient httpClient = HttpClientUtils.createClient();
				HttpGet httpget = new HttpGet(sb.toString());

				CloseableHttpResponse clientResponse = httpClient
						.execute(httpget);

				routeResponse = IOUtils.toString(clientResponse.getEntity()
						.getContent(), "UTF-8");

				routeResponsecode = String.valueOf(clientResponse
						.getStatusLine().getStatusCode());
				routeResponseMessage = clientResponse.getStatusLine()
						.getReasonPhrase();

			} catch (IOException e) {

				System.out.println(e.getMessage());
			}

		}

	}

}