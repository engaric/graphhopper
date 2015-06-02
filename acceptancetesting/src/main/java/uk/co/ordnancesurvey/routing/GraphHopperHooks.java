package uk.co.ordnancesurvey.routing;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;


import uk.co.ordnancesurvey.gpx.graphhopper.IntegrationTestProperties;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GraphHopperHooks {
	GraphHopperUIUtil graphUiUtil;
	String testON="";

	String instruction;
	String nearestPoint = "";
	String Distance = "";
	String pointA;

	DataTable routePointsTable;
	private String routeResponsecode;
	private String routeResponseMessage;
	// private Map<String,String> requestParameters= new HashMap<>();

	
	@Before
	public void init()
	{
		graphUiUtil = (IntegrationTestProperties
				.getTestPropertyBool("viaApigee") == true) ?  new GraphHopperUIUtil(
				IntegrationTestProperties
						.getTestProperty("graphHopperWebUrlViaApigee"))
				: new GraphHopperUIUtil(
						IntegrationTestProperties
								.getTestProperty("graphHopperWebUrl"));
	}

	
	@Before({"@WebOnly"})
	public void overrideTestONProperty()
	{

		testON=IntegrationTestProperties.getTestProperty("testON");
		IntegrationTestProperties.setTestProperty("testON", "Web");
	}

	
	@After({"@WebOnly"})
	public void rollBackTestONProperty()
	{
		IntegrationTestProperties.setTestProperty("testON", testON);
		
		
	}

	
	
	@Given("^My routing point for nearestPoint API as \"([^\"]*)\"$")
	public void I_have_route_point_for_Nearest_Point_API(
			String pointA) {
		this.pointA=pointA;
	}
	@When("^I request a nearest point from from Nearest Point API$")
	public void I_request_a_nearest_point_from_from_Nearest_Point_API(
			) {
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
				
				e.printStackTrace();
			}
		}
		
		graphUiUtil.logout();


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
		
		graphUiUtil.addParameter(paramName, paramValue);
	}


	@Given("^I have route ([^\"]*) as$")
	public void setRoutingpoints(String paramName,DataTable dt) {
		//graphUiUtil=new GraphHopperUIUtil(IntegrationTestProperties.getTestProperty("graphHopperWebUrl"));
		routePointsTable=dt;
		graphUiUtil.addRoutePointsToParameters(paramName, dt);

	}


	

	@Then("^I should be able to verify the statuscode as \"([^\"]*)\"$")
	public void I_should_be_able_to_verify_the_responseCode_as(
			int  statusCode) {
		
		graphUiUtil.verifyStatusCode(statusCode);


	}

	@Then("^I should be able to verify the response message as \"([^\"]*)\"$")
	public void I_should_be_able_to_verify_the_response_message_as(
			String responseMessage) {
		
		graphUiUtil.verifyErrorMessage(responseMessage);
	
	}

	
	
	@When("^I request for a route$")
	public void I_request_for_route() {
		

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI();
			break;
		case "SERVICE":
			
			
			
			graphUiUtil.getRouteFromServiceWithParameters();

			break;
		default:

			List<List<String>> data = routePointsTable.raw();

			String[] points = new String[data.get(1).size()];
			points = data.get(1).toArray(points);


			if (points[0].split(",").length==2) {
				graphUiUtil.getRouteFromServiceWithParameters();
				graphUiUtil.getRouteFromUI();
			} else {
				
				graphUiUtil.getRouteFromUI();
			}

			break;


		}
		

		
	}
	
	
}