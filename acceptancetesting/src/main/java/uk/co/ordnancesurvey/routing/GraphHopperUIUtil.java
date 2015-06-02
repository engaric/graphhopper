package uk.co.ordnancesurvey.routing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ADD_WAYPOINT;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.INSTRUCTIONS;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_SEARCH;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_TYPE_BIKE;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_TYPE_CAR;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_TYPE_WALK;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.TOTAL_ROUTE_TIME;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_ARoad;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Boulders;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Cliff;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_InlandWater;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Marsh;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Mud;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_QuarryOrPit;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Rock;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Sand;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Scree;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.avoidance_Shingle;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.dropDown;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.fastest_RButton;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.settingsButton;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.shortest_RButton;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.waypoint;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.alternativevision.gpx.beans.Route;
import org.alternativevision.gpx.beans.Waypoint;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.extensions.ExtensionConstants;
import uk.co.ordnancesurvey.gpx.graphhopper.GraphHopperGPXParserRouteTest;
import uk.co.ordnancesurvey.gpx.graphhopper.GraphHopperJSONParser;
import uk.co.ordnancesurvey.gpx.graphhopper.IntegrationTestProperties;
import uk.co.ordnancesurvey.webtests.base.ComponentID;
import uk.co.ordnancesurvey.webtests.base.ImageComparison;
import uk.co.ordnancesurvey.webtests.multiplatform.MultiplatformTest;
import uk.co.ordnancesurvey.webtests.platforms.BrowserPlatformOptions;
import cucumber.api.DataTable;

public class GraphHopperUIUtil extends MultiplatformTest {

	private String baseUrl;
	private String routeStepNumber;
	String testOn = IntegrationTestProperties.getTestProperty("testON");
	GraphHopperGPXParserRouteTest GPHService = new GraphHopperGPXParserRouteTest();
	GraphHopperJSONParser GPHJsonService = new GraphHopperJSONParser();
	private Map<String, ArrayList<String>> requestParameters = new HashMap<String, ArrayList<String>>();

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

		if (!testOn.equalsIgnoreCase("SERVICE")) {
			initialiseWebDriver();
		}

	}

	@Override
	public void logout() {

		if (!testOn.equalsIgnoreCase("SERVICE"))
		{
			driver.close();
			driver.quit();
		}
		
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

			if (routeOption.equalsIgnoreCase("shortavoid")) {
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
			
			
	
			if (requestParameters.get("type").get(0).equals(
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

			if (requestParameters.get("type").get(0).equals(
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
			isWayPointonRouteMap = (isWayPointonRouteMapUI)
					&& (isWayPointonRouteMapService);

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
		boolean isWayPointNotonRouteMap = true;
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

				isWayPointNotonsRouteMap = (isWayPointNotonsRouteMap && isWayPointNotonRouteMap);

			}

			else

			{

				String wayPointIndex = (String) waypointList.get(i).get(
						"wayPointIndex");
				String waypointdesc = (String) waypointList.get(i).get(
						"waypointdesc");
				isWayPointNotonRouteMap = verifyInstructionThroughUI(
						wayPointIndex, waypointdesc, "");

				isWayPointNotonsRouteMap = (isWayPointNotonsRouteMap || isWayPointNotonRouteMap);

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

				isWayPointsonRouteMap = isWayPointsonRouteMap
						&& isWayPointonRouteMap;

			}

			else

			{

				String wayPointIndex = (String) waypointList.get(i).get(
						"wayPointIndex");
				String waypointdesc = (String) waypointList.get(i).get(
						"waypointdesc");
				isWayPointonRouteMap = verifyInstructionThroughUI(
						wayPointIndex, waypointdesc, "");

				isWayPointsonRouteMap = isWayPointsonRouteMap
						&& isWayPointonRouteMap;

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
			if (requestParameters.get("type").get(0).equals(
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

	protected void addParameter(String key, String value) {
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

	


	protected void addRoutePointsToParameters(String paramName, DataTable dt) {
		List<List<String>> data = dt.raw();

		String[] points = new String[data.get(1).size()];
		points = data.get(1).toArray(points);

		for (int i = 0; i < points.length; i++) {
			addParameter(paramName, points[i]);
		}

	}

	public void getRouteFromServiceWithParameters(StringBuffer sb) {

		if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx"))
				 {
			GPHService.parseRoute(sb);
		}

		else {

			GPHJsonService.parse(sb);
		}

	}

	protected void getRouteFromServiceWithParameters() {

		StringBuffer sb = new StringBuffer();

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee"));

		} else {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl"));
		}
		sb.append("route?");
		
		if (!requestParameters.containsKey("type") )
		{			
			ArrayList<String> responseType = new ArrayList<String>();
			responseType.add(IntegrationTestProperties
					.getTestProperty("routeType"));
			requestParameters.put("type",responseType);
		
		}
		else
			if (requestParameters.get("type").get(0).isEmpty()){
		
			requestParameters.remove("type");
			ArrayList<String> responseType = new ArrayList<String>();
			responseType.add(IntegrationTestProperties
					.getTestProperty("routeType"));
			requestParameters.put("type", responseType);
			}
		ArrayList<String> pointscoding = new ArrayList<String>();
		pointscoding.add("false");
		requestParameters.put("points_encoded", pointscoding);
		for (Entry<String, ArrayList<String>> entry : requestParameters
				.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> value = entry.getValue();
			for (String string : value) {
				sb.append("&" + key + "=" + string);
			}

		}

		getRouteFromServiceWithParameters(sb);
	}

	/*
	 * try {
	 * 
	 * CloseableHttpClient httpClient = HttpClientUtils.createClient(); HttpGet
	 * httpget = new HttpGet(sb.toString());
	 * 
	 * CloseableHttpResponse clientResponse = httpClient .execute(httpget);
	 * 
	 * getRouteFromServiceWithParameters(sb);
	 * 
	 * String routeResponse = IOUtils.toString(clientResponse.getEntity()
	 * .getContent(), "UTF-8");
	 * 
	 * String routeResponsecode = String.valueOf(clientResponse
	 * .getStatusLine().getStatusCode()); String routeResponseMessage =
	 * clientResponse.getStatusLine() .getReasonPhrase();
	 * 
	 * 
	 * } catch (IOException e) {
	 * 
	 * System.out.println(e.getMessage()); }
	 */

	public void getRouteFromUI() {

		String weighting = requestParameters.get("weighting").get(0);

		String avoidances = requestParameters.get("avoidances").get(0);
		String vehicle = requestParameters.get("vehicle").get(0);

		ArrayList<String> points = requestParameters.get("point");
		
		if (!requestParameters.containsKey("type") )
		{			
			ArrayList<String> responseType = new ArrayList<String>();
			responseType.add(IntegrationTestProperties
					.getTestProperty("routeType"));
			requestParameters.put("type",responseType);
		
		}
		else
			if (requestParameters.get("type").get(0).isEmpty()){
		
			requestParameters.remove("type");
			ArrayList<String> responseType = new ArrayList<String>();
			responseType.add(IntegrationTestProperties
					.getTestProperty("routeType"));
			requestParameters.put("type", responseType);
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

			if (weighting.equalsIgnoreCase("shortavoid")) {
				clickElement(shortest_RButton);
			}

			else {
				clickElement(fastest_RButton);
			}

		}

		else {
			if (weighting.equalsIgnoreCase("shortest")) {
				clickElement(shortest_RButton);
			}

			else {
				clickElement(fastest_RButton);
			}
		}

		for (int i = 0; i < points.size() - 2; i++)

		{
			clickElement(ADD_WAYPOINT);
		}

		for (int i = 0; i < points.size(); i++) {
			String point = points.get(i);

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

	public void verifyErrorMessage(String responseMessage) {
		if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
			GPHService.verifyMessage(responseMessage);
		} else {
			GPHJsonService.verifyMessage(responseMessage);
		}
	}

	public void verifyStatusCode(int statusCode) {
		if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
			GPHService.verifyStatusCode(statusCode);
		} else {
			GPHJsonService.verifyStatusCode(statusCode);
		}
	}

}
