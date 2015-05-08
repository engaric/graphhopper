package uk.co.ordnancesurvey.routing;

import gherkin.formatter.model.Feature;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import uk.co.ordnancesurvey.gpx.graphhopper.IntegrationTestProperties;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class GraphHopperHooks {
	GraphHopperUIUtil graphUiUtil;

	String instruction;

	@Given("^I request a route between \"([^\"]*)\" and \"([^\"]*)\" as a \"([^\"]*)\" from RoutingAPI$")
	public void getRoute(String pointA, String pointB, String routeType)
			throws InterruptedException {
		String graphHopperWebUrl;

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee");
		} else {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl");
		}

		graphUiUtil = new GraphHopperUIUtil(graphHopperWebUrl);

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeType, "",pointA, pointB);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromService(routeType, pointA, pointB);
			break;
		default:

			if (pointA.split(",").length == 2) {
				graphUiUtil.getRouteFromService(routeType, pointA, pointB);
				graphUiUtil.getRouteFromUI(routeType,"", pointA, pointB);
			} else {
				graphUiUtil.getRouteFromUI(routeType, "",pointA, pointB);
			}

			break;

		}

	}

	@Given("^I request a route between \"([^\"]*)\" and \"([^\"]*)\" as a \"([^\"]*)\" from RoutingAPI and avoid \"([^\"]*)\"$")
	public void getRouteWithAvoidance(String pointA, String pointB,
			String routeType, String avoidance) throws InterruptedException {
		String graphHopperWebUrl;
		avoidance= avoidance.toLowerCase().trim();
		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee");
		} else {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl");
		}

		graphUiUtil = new GraphHopperUIUtil(graphHopperWebUrl);

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeType,avoidance, pointA, pointB);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromServiceWithAvoidance(routeType, avoidance, pointA,
					pointB);
			break;
		default:

			if (pointA.split(",").length == 2) {
				graphUiUtil.getRouteFromServiceWithAvoidance(routeType, avoidance,pointA, pointB);
				graphUiUtil.getRouteFromUI(routeType,avoidance, pointA, pointB);
			} else {
				graphUiUtil.getRouteFromUI(routeType,avoidance, pointA, pointB);
			}

			break;

		}

	}

	@Given("^I request a route between \"([^\"]*)\" and \"([^\"]*)\" as a \"([^\"]*)\" from RoutingAPI via \"([^\"]*)\"$")
	public void getRoute(String pointA, String pointB, String routeType,
			String pointC) throws InterruptedException {
		String graphHopperWebUrl;

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee");
		} else {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl");
		}

		graphUiUtil = new GraphHopperUIUtil(graphHopperWebUrl);

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeType,"",pointA, pointB, pointC);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromService(routeType, pointA, pointB, pointC);
			break;
		default:

			if (pointA.split(",").length == 2) {
				graphUiUtil.getRouteFromService(routeType, pointA, pointB,
						pointC);
				graphUiUtil.getRouteFromUI(routeType, pointA, pointB, pointC);
			} else {
				graphUiUtil.getRouteFromUI(routeType,"", pointA, pointB, pointC);
			}

			break;

		}

	}
	
	
	@Given("^I request a route between \"([^\"]*)\" and \"([^\"]*)\" as a \"([^\"]*)\" from RoutingAPI and avoid \"([^\"]*)\" via \"([^\"]*)\"$")
	public void getRouteWithAvoidances(String pointA, String pointB, String routeType,String avoidance,
			String pointC) throws InterruptedException {
		String graphHopperWebUrl;

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee");
		} else {
			graphHopperWebUrl = IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl");
		}

		graphUiUtil = new GraphHopperUIUtil(graphHopperWebUrl);

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeType,"",pointA, pointB, pointC);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromServiceWithAvoidance(routeType, avoidance,pointA, pointB, pointC);
			break;
		default:

			if (pointA.split(",").length == 2) {
				graphUiUtil.getRouteFromServiceWithAvoidance(routeType, avoidance,pointA, pointB, pointC);
				graphUiUtil.getRouteFromUI(routeType, pointA, pointB, pointC);
			} else {
				graphUiUtil.getRouteFromUI(routeType,"", pointA, pointB, pointC);
			}

			break;

		}

	}

	@Given("^I request a route between \"([^\"]*)\" and \"([^\"]*)\" as a \"([^\"]*)\" from RoutingAPI via \"([^\"]*)\" and \"([^\"]*)\"$")
	public void getRoute(String pointA, String pointB, String routeType,
			String pointC, String pointD) throws InterruptedException {

		graphUiUtil = new GraphHopperUIUtil(
				IntegrationTestProperties.getTestProperty("graphHopperWebUrl"));

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeType, "",pointA, pointB, pointC,
					pointD);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromService(routeType, pointA, pointB, pointC,
					pointD);
			break;
		default:

			if (pointA.split(",").length == 2) {
				graphUiUtil.getRouteFromService(routeType, pointA, pointB,
						pointC, pointD);
				graphUiUtil.getRouteFromUI(routeType,"", pointA, pointB, pointC,
						pointD);
			} else {
				graphUiUtil.getRouteFromUI(routeType, "",pointA, pointB, pointC,
						pointD);
			}

			break;

		}

	}
	
	
	@Given("^I request a route between \"([^\"]*)\" and \"([^\"]*)\" as a \"([^\"]*)\" from RoutingAPI and avoid \"([^\"]*)\" via \"([^\"]*)\" and \"([^\"]*)\"$")
	public void getRouteWithAvoidances(String pointA, String pointB, String routeType,String avoidance,
			String pointC, String pointD) throws InterruptedException {

		graphUiUtil = new GraphHopperUIUtil(
				IntegrationTestProperties.getTestProperty("graphHopperWebUrl"));

		String testON = IntegrationTestProperties.getTestProperty("testON");

		switch (testON.toUpperCase()) {
		case "WEB":

			graphUiUtil.getRouteFromUI(routeType, "",pointA, pointB, pointC,
					pointD);
			break;
		case "SERVICE":
			graphUiUtil.getRouteFromServiceWithAvoidance(routeType,avoidance, pointA, pointB, pointC,
					pointD);
			break;
		default:

			if (pointA.split(",").length == 2) {
				graphUiUtil.getRouteFromServiceWithAvoidance(routeType,avoidance, pointA, pointB, pointC,
						pointD);
				graphUiUtil.getRouteFromUI(routeType,"", pointA, pointB, pointC,
						pointD);
			} else {
				graphUiUtil.getRouteFromUI(routeType, "",pointA, pointB, pointC,
						pointD);
			}

			break;

		}

	}

	@Then("^I should be able to verify the \"([^\"]*)\" waypoint \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" on the route map$")
	public void I_should_be_able_to_verify_the_waypoint_on_the_route_map(
			String wayPointIndex, String wayPoint_Coordinates,
			String wayPointDescription, String azimuth, String direction,
			String time, String distance) {

		graphUiUtil.isWayPointonRouteMap(wayPointIndex, wayPoint_Coordinates,
				wayPointDescription, azimuth, direction, time, distance,"");

	}
	
	
	

	@Then("^I should be able to verify the waypoints on the route map:")
	public void I_should_be_able_to_verify_the_waypoints_on_the_route_map(
			List<Map<String, String>> wayPointList) {
		Assert.assertTrue("Waypoint not found on the route where it was expected",graphUiUtil.isWayPointonRouteMap(wayPointList));

	}

	@Then("^I should be able to verify the waypoints not on the route map:")
	public void I_should_be_able_to_verify_the_not_waypoints_on_the_route_map(
			List<Map<String, String>> wayPointList) {

		Assert.assertFalse("Waypoint found on the route where it was not expected",graphUiUtil.isWayPointonRouteMap(wayPointList));
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

	@After({ "@Routing" })
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

}
