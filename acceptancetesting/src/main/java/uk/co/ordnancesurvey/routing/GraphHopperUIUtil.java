package uk.co.ordnancesurvey.routing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.*;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.alternativevision.gpx.beans.Route;
import org.alternativevision.gpx.beans.Waypoint;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.extensions.ExtensionConstants;
import uk.co.ordnancesurvey.gpx.graphhopper.GraphHopperGPXParserRouteTest;
import uk.co.ordnancesurvey.gpx.graphhopper.GraphHopperJSONParser;
import uk.co.ordnancesurvey.webtests.base.ComponentByXPATH;
import uk.co.ordnancesurvey.webtests.base.ComponentID;
import uk.co.ordnancesurvey.webtests.base.ComponentIdentifier;
import uk.co.ordnancesurvey.webtests.base.ImageComparison;
import uk.co.ordnancesurvey.webtests.multiplatform.MultiplatformTest;
import uk.co.ordnancesurvey.webtests.platforms.BrowserPlatformOptions;
import uk.co.ordnancesurvey.gpx.graphhopper.IntegrationTestProperties;

public class GraphHopperUIUtil extends MultiplatformTest {

	private String baseUrl;
	private String routeStepNumber;
	String testOn = IntegrationTestProperties.getTestProperty("testON");
	GraphHopperGPXParserRouteTest GPHService = new GraphHopperGPXParserRouteTest();
	GraphHopperJSONParser GPHJsonService = new GraphHopperJSONParser();

	JavascriptExecutor js = (JavascriptExecutor) driver;
	WebElement we;
	private BufferedImage actualMap;

	private static final Logger LOG = LoggerFactory
			.getLogger(GraphHopperUIUtil.class);

	public GraphHopperUIUtil(String url) {

		super(BrowserPlatformOptions.getEnabledOptionsArrayList().get(0)[0]);
		try {
			baseUrl = url;
			init();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void init() throws InterruptedException {
		// baseUrl = IntegrationTestProperties
		// .getTestProperty("graphHopperWebUrl");
		// if (null == baseUrl) {
		// baseUrl = "http://os-graphhopper.elasticbeanstalk.com/";
		// }

		if (!testOn.equalsIgnoreCase("SERVICE")) {
			initialiseWebDriver();
		}

	}

	@Override
	public void logout() {
		if (!testOn.equalsIgnoreCase("SERVICE"))
			driver.close();
	}

	/**
	 * <p>
	 * getRouteFromUI is to get a route from web interface using the provided
	 * start, end and intermediate waypoints.
	 * <p>
	 * all avoidances will be considered while generating a route.
	 * 
	 * @param routeOptions
	 *            can be car/bike/foot
	 * @param avoidances
	 *            can be aroad,cliff.. etc and it can be "" if no avoidance is
	 *            need to be set
	 * @param points
	 *            start and end points along with any intermediate points
	 * @throws InterruptedException
	 */
	public void getRouteFromUI(String routeOptions, String avoidances,
			String... points) throws InterruptedException {
		String vehicle = "";
		String routeOption = "";
		if (routeOptions.split(",").length > 1) {
			vehicle = routeOptions.split(",")[0];
			routeOption = routeOptions.split(",")[1];
		} else {
			vehicle = routeOptions;
		}

		switch (vehicle) {
		case "car":
			clickElement(ROUTE_TYPE_CAR);

			break;
		case "bike":
			clickElement(ROUTE_TYPE_BIKE);
			break;
		case "foot":
			clickElement(ROUTE_TYPE_WALK);
			break;
		default:
			clickElement(ROUTE_TYPE_CAR);
			break;

		}
		clickElement(settingsButton);

		if (!avoidances.equals("")) {
			for (int i = 0; i < avoidances.split(",").length; i++) {
				String avoidance = avoidances.split(",")[i];
				switch (avoidance.toLowerCase().trim()) {

				case "aroad":
					clickElement(avoidance_ARoad);
					break;

				case "boulders":
					clickElement(avoidance_Boulders);
					break;
				case "cliff":
					clickElement(avoidance_Cliff);
					break;
				case "inlandwater":
					clickElement(avoidance_InlandWater);
					break;
				case "marsh":
					clickElement(avoidance_Marsh);
					break;
				case "quarryorpit":
					clickElement(avoidance_QuarryOrPit);
					break;
				case "scree":
					clickElement(avoidance_Scree);
					break;
				case "rock":
					clickElement(avoidance_Rock);
					break;
				case "mud":
					clickElement(avoidance_Mud);
					break;

				case "sand":
					clickElement(avoidance_Sand);
					break;

				case "shingle":
					clickElement(avoidance_Shingle);
					break;

				default:
					break;
				}
			}
			/*
			 * switch (routeOption) { case "shortavoid":
			 * clickElement(shortest_RButton); break; case "fastavoid":
			 * clickElement(fastest_RButton); break; case "fastest":
			 * 
			 * clickElement(fastest_RButton); break; case "shortest":
			 * clickElement(shortest_RButton); break;
			 * 
			 * default: break; }
			 */if (routeOption.equalsIgnoreCase("shortavoid")) {
				clickElement(shortest_RButton);
			}

			else {
				clickElement(fastest_RButton);
			}

		}

		else {
			if (routeOption.equalsIgnoreCase("shortest")) {
				clickElement(shortest_RButton);
			}

			else {
				clickElement(fastest_RButton);
			}
		}

		for (int i = 0; i < points.length - 2; i++)

		{
			clickElement(ADD_WAYPOINT);
		}

		for (int i = 0; i < points.length; i++) {
			String point = points[i];

			int length = point.split(",").length;

			if (length == 2) {

				waypoint = new ComponentID(i + "_searchBox");
				typeIntoField(waypoint, point);

			}

			else {
				waypoint = new ComponentID(i + "_searchBox");
				typeIntoField(waypoint, point);
				clickElement(dropDown);
			}
		}

		clickElement(ROUTE_SEARCH);

		waitFor(INSTRUCTIONS);

	}

	@Override
	public void login() {
		driver.navigate().to(baseUrl);
	}

	public boolean verifyInstructionThroughUI(String routeStepNumber,
			String stepInstruction, String avoidance) {
		this.routeStepNumber = routeStepNumber;
		List<WebElement> WAY_POINTS = driver.findElements(By
				.xpath("//*[@id='instructions']/tbody/tr[*]/td[2]"));
		try {
			WAY_POINTS.get(Integer.parseInt(routeStepNumber) - 1).click();
		} catch (Exception e) {
			LOG.info(e.getMessage());
			return false;
		}
		if (null != avoidance) {
			if (!avoidance.isEmpty()) {
				avoidance = ",  " + avoidance;
			}
		} else {
			avoidance = "";
		}

		return getTableRowStatus(INSTRUCTIONS,
				Integer.parseInt(this.routeStepNumber), stepInstruction
						+ avoidance);

	}

	public void getRouteFromServiceWithAvoidance(String routeOptions,
			String avoidances, String... points) {

		if (IntegrationTestProperties.getTestProperty("routeType")
				.equalsIgnoreCase("gpx")) {
			GPHService.parseRoute("gpx", avoidances, routeOptions, points);
		}

		else {

			GPHJsonService.parse("json", avoidances, routeOptions, points);
		}

	}

	public void getRouteFromService(String routeType, String... points) {

		if (IntegrationTestProperties.getTestProperty("routeType")
				.equals("gpx")) {
			GPHService.parseRoute("gpx", "", routeType, points);
		}

		else {

			GPHJsonService.parse("json", "", routeType, points);
		}

	}

	public void verifyInstructionThroughService(String stepInstruction) {
		HashSet<Route> routeInstruction = GPHService.getRoutes();

		Assert.assertTrue(
				"The Route instruction is not found in the gpx response",
				GPHService.routeContainsTurn(stepInstruction.toUpperCase(),
						routeInstruction.iterator().next()));

	}

	private Waypoint buildWayPoint(String waypointco) throws ParseException {

		Waypoint wp = new Waypoint();
		String waypoint[] = waypointco.split(",");
		wp.setLatitude(new Double(waypoint[0]));
		wp.setLongitude(new Double(waypoint[1]));

		return wp;
	}

	public boolean isWayPointonRouteMap(String wayPointIndex,
			String wayPoint_Coordinates, String wayPointDescription,
			String azimuth, String direction, String time, String distance,
			String avoidance) {
		boolean isWayPointonRouteMap = false;
		Waypoint wp;
		boolean isWayPointonRouteMapUI = false;
		boolean isWayPointonRouteMapService = false;

		switch (testOn.toUpperCase()) {
		case "WEB":

			isWayPointonRouteMap = verifyInstructionThroughUI(wayPointIndex,
					wayPointDescription, avoidance);
			break;
		case "SERVICE":
			if (IntegrationTestProperties.getTestProperty("routeType").equals(
					"gpx")) {
				wp = buildWayPoint(wayPoint_Coordinates, wayPointDescription,
						azimuth, direction, time, distance);
				isWayPointonRouteMap = GPHService.isWayPointOnGPXRoutes(wp);

			} else {
				wp = GPHJsonService.buildWayPointForJson(wayPoint_Coordinates,
						wayPointDescription, time, distance, avoidance);
				isWayPointonRouteMap = GPHJsonService.isWayPointinPath(wp);

			}

			break;

		default:

			isWayPointonRouteMapUI = verifyInstructionThroughUI(wayPointIndex,
					wayPointDescription, avoidance);

			if (IntegrationTestProperties.getTestProperty("routeType").equals(
					"gpx")) {
				wp = buildWayPoint(wayPoint_Coordinates, wayPointDescription,
						azimuth, direction, time, distance);

				isWayPointonRouteMapService = GPHService
						.isWayPointOnGPXRoutes(wp);

			} else {

				wp = GPHJsonService.buildWayPointForJson(wayPoint_Coordinates,
						wayPointDescription, time, distance, avoidance);
				isWayPointonRouteMapService = GPHJsonService
						.isWayPointinPath(wp);
			}
			isWayPointonRouteMap=(isWayPointonRouteMapUI) && (isWayPointonRouteMapService);

			break;
		}

		return isWayPointonRouteMap;

	}

	private Waypoint buildWayPoint(String wayPoint_Coordinates,
			String wayPointDescription, String azimuth, String direction,
			String time, String distance) {

		Waypoint wp = new Waypoint();
		String waypoint[] = wayPoint_Coordinates.split(",");
		wp.setLatitude(new Double(waypoint[0]));
		wp.setLongitude(new Double(waypoint[1]));

		wp.setDescription(wayPointDescription);
		wp.addExtensionData(ExtensionConstants.AZIMUTH, azimuth);
		wp.addExtensionData(ExtensionConstants.DIRECTION, direction);
		wp.addExtensionData(ExtensionConstants.TIME, time);
		wp.addExtensionData(ExtensionConstants.DISTANCE, distance);
		LOG.info(wp.toString());
		LOG.info(wp.getExtensionData().toString());
		return wp;
	}

	public boolean isWayPointNotonRouteMap(
			List<Map<String, String>> waypointList) {
		
		boolean isWayPointNotonsRouteMap = true;
		boolean isWayPointNotonRouteMap=true;
		for (int i = 0; i < waypointList.size(); i++) {

			if (waypointList.get(i).size() > 2) {
				String wayPointIndex = (String) waypointList.get(i).get(
						"wayPointIndex");
				String waypointco = (String) waypointList.get(i).get(
						"waypointco");
				String waypointdesc = (String) waypointList.get(i).get(
						"waypointdesc");
				String azimuth = (String) waypointList.get(i).get("azimuth");
				String direction = (String) waypointList.get(i)
						.get("direction");
				String time = (String) waypointList.get(i).get("time");
				String distance = (String) waypointList.get(i).get("distance");
				String avoidance = (String) waypointList.get(i)
						.get("avoidance");

				isWayPointNotonRouteMap = isWayPointonRouteMap(wayPointIndex,
						waypointco, waypointdesc, azimuth, direction, time,
						distance, avoidance);
				
				isWayPointNotonsRouteMap=(isWayPointNotonsRouteMap&&isWayPointNotonRouteMap);
					
			}

			else

			{

				String wayPointIndex = (String) waypointList.get(i).get(
						"wayPointIndex");
				String waypointdesc = (String) waypointList.get(i).get(
						"waypointdesc");
				isWayPointNotonRouteMap = verifyInstructionThroughUI(
						wayPointIndex, waypointdesc, "");
				
				isWayPointNotonsRouteMap=(isWayPointNotonsRouteMap||isWayPointNotonRouteMap);
				

			}

		}
		return isWayPointNotonsRouteMap;


	}

	public boolean isWayPointonRouteMap(List<Map<String, String>> waypointList) {
		boolean isWayPointsonRouteMap = true;
		boolean isWayPointonRouteMap = false;
		for (int i = 0; i < waypointList.size(); i++) {

			if (waypointList.get(i).size() > 2) {
				String wayPointIndex = (String) waypointList.get(i).get(
						"wayPointIndex");
				String waypointco = (String) waypointList.get(i).get(
						"waypointco");
				String waypointdesc = (String) waypointList.get(i).get(
						"waypointdesc");
				String azimuth = (String) waypointList.get(i).get("azimuth");
				String direction = (String) waypointList.get(i)
						.get("direction");
				String time = (String) waypointList.get(i).get("time");
				String distance = (String) waypointList.get(i).get("distance");
				String avoidance = (String) waypointList.get(i)
						.get("avoidance");

				isWayPointonRouteMap = isWayPointonRouteMap(wayPointIndex,
						waypointco, waypointdesc, azimuth, direction, time,
						distance, avoidance);
				
				isWayPointsonRouteMap=isWayPointsonRouteMap&&isWayPointonRouteMap;

					
			}

			else

			{

				String wayPointIndex = (String) waypointList.get(i).get(
						"wayPointIndex");
				String waypointdesc = (String) waypointList.get(i).get(
						"waypointdesc");
				isWayPointonRouteMap = verifyInstructionThroughUI(
						wayPointIndex, waypointdesc, "");
				
				isWayPointsonRouteMap=isWayPointsonRouteMap&&isWayPointonRouteMap;
				

			}

		}
		return isWayPointsonRouteMap;

	}
	
	

	

	public void verifyTotalRouteTime(String totalRouteTime)
			throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("H'h'mm'min'");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date eTime, aTime;

		String actualTime = "0h00min";
		String expectedTime = totalRouteTime.trim().replaceAll(" ", "");
		eTime = formatter.parse(expectedTime);
		aTime = formatter.parse(actualTime);

		switch (testOn.toUpperCase()) {
		case "WEB":

			actualTime = getValue(TOTAL_ROUTE_TIME).split("take ")[1].trim()
					.replaceAll(" ", "");
			if (!actualTime.contains("h")) {
				actualTime = "00h" + actualTime;
			}
			aTime = formatter.parse(actualTime);

			LOG.info("The total route time expected is " + eTime.getTime()
					+ " Milliseconds and actual is " + aTime.getTime()
					+ " Milliseconds");
			assertTrue("The total route time expected " + eTime.getTime()
					+ " is not matchin with actual " + aTime.getTime(),
					aTime.getTime() <= eTime.getTime());

			break;

		case "SERVICE":

			if (IntegrationTestProperties.getTestProperty("routeType")
					.equalsIgnoreCase("GPX")) {
				aTime.setTime(GPHService.getTotalRouteTime());
			} else {
				aTime.setTime(GPHJsonService.getTotalRouteTime());
			}
			LOG.info("The total route time expected is " + eTime.getTime()
					+ " and actual is " + aTime.getTime());
			assertTrue("The total route time expected " + eTime.getTime()
					+ " is not matchin with actual " + aTime.getTime(),
					aTime.getTime() <= eTime.getTime());
			break;

		default:

			if (IntegrationTestProperties.getTestProperty("routeType")
					.equalsIgnoreCase("GPX")) {
				aTime.setTime(GPHService.getTotalRouteTime());
			} else {
				aTime.setTime(GPHJsonService.getTotalRouteTime());
			}
			actualTime = getValue(TOTAL_ROUTE_TIME).split("take ")[1].trim()
					.replaceAll(" ", "");
			if (!actualTime.contains("h")) {
				actualTime = "00h" + actualTime;
			}
			if (!actualTime.contains("min")) {
				actualTime = actualTime + "00min";
			}
			aTime = formatter.parse(actualTime);

			LOG.info("The total route time expected is " + eTime.getTime()
					+ " Milliseconds and actual is " + aTime.getTime()
					+ " Milliseconds");
			assertTrue("The total route time expected " + eTime.getTime()
					+ " is not matchin with actual " + aTime.getTime(),
					aTime.getTime() <= eTime.getTime());
		}

	}

	public void isTrackPointonRouteMap(List<Map<String, String>> trackPointsList)
			throws ParseException {

		for (int i = 0; i < trackPointsList.size(); i++) {

			String waypointco = (String) trackPointsList.get(i).get(
					"trackPointco");
			// String time = (String) trackPointsList.get(i).get("time");

			Waypoint trackPoint = buildWayPoint(waypointco);
			if (IntegrationTestProperties.getTestProperty("routeType").equals(
					"gpx")) {
				assertTrue(GPHService.isWayPointOnTrack(trackPoint, GPHService
						.getTracks().iterator().next()));
			}

			else {
				assertTrue(GPHJsonService.isWayPointinPath(trackPoint,
						GPHJsonService.getJsonCoordinatesAsHashSet()));

			}

		}

	}

	public void isTrackPointNotonRouteMap(
			List<Map<String, String>> trackPointsList) throws ParseException {

		for (int i = 0; i < trackPointsList.size(); i++) {

			String waypointco = (String) trackPointsList.get(i).get(
					"trackPointco");
			// String time = (String) trackPointsList.get(i).get("time");

			Waypoint trackPoint = buildWayPoint(waypointco);
			if (IntegrationTestProperties.getTestProperty("routeType").equals(
					"gpx")) {
				assertTrue(!GPHService.isWayPointOnTrack(trackPoint, GPHService
						.getTracks().iterator().next()));
			}

			else {
				assertTrue(!GPHJsonService.isWayPointinPath(trackPoint,
						GPHJsonService.getJsonCoordinatesAsHashSet()));

			}

		}

	}

	public BufferedImage takescreen(String testID) throws IOException {

		File file = new File(testID + "_screenshot.png");

		File screenshot = takeScreenShot();
		actualMap = resize(ImageIO.read(screenshot), 1000, 800);
		// actualMap = ImageIO.read(screenshot);

		ImageIO.write(actualMap, "png", file);
		return actualMap;

	}

	public byte[] takescreenAsBiteArray() throws IOException {

		byte[] screenshot = takeScreenShotAsBiteArray();

		return screenshot;

	}

	public void compareMapImage(String expectedMap, String testID)
			throws IOException {
		takescreen(testID);

		File file = new File(expectedMap);
		BufferedImage expactedImage = resize(ImageIO.read(file), 1000, 800);
		// BufferedImage expactedImage = ImageIO.read(file);
		System.out.println(" width" + expactedImage.getWidth());
		System.out.println(" Height" + expactedImage.getHeight());
		System.out.println(" width" + actualMap.getWidth());
		System.out.println(" Height" + actualMap.getHeight());

		ImageComparison img = new ImageComparison(expactedImage, actualMap);

		img.compare();
		if (!img.match()) {
			String failPath = expectedMap + ".fail-" + testID + ".png";
			String comparePath = expectedMap + ".actual-" + testID + ".png";
			ImageIO.write(img.getChangeIndicator(), "png", new File(failPath));
			ImageIO.write(actualMap, "png", new File(comparePath));
			ImageIO.write(expactedImage, "png", new File("new.png"));
			fail("Image comparison failed see " + failPath + " for details");
		}

	}

	public BufferedImage resize(BufferedImage img, int newW, int newH) {
		// Getting the width and height of the given image.
		int w = img.getWidth();
		int h = img.getHeight();
		// Creating a new image object with the new width and height and with
		// the old image type
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		// Creating a graphics image for the new Image.
		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;

	}

	public void verifyWayPointsThroughService() {

	}

	public String nearestPointService(String pointA) {

		return GPHJsonService.getNearestPoint(pointA);

	}

	public String nearestPointDistance() {

		return GPHJsonService.getNearestPointDistance();

	}

}
