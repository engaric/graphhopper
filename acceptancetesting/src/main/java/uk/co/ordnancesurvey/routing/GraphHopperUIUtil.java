package uk.co.ordnancesurvey.routing;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ADD_WAYPOINT;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.INSTRUCTIONS;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_SEARCH;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_TYPE_BIKE;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_TYPE_CAR;
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.ROUTE_TYPE_MOUNTAINBIKE;
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
import static uk.co.ordnancesurvey.routing.GraphHopperComponentIdentification.error_Message;
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
import org.apache.commons.io.IOUtils;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.ordnancesurvey.gpx.extensions.ExtensionConstants;
import uk.co.ordnancesurvey.gpx.graphhopper.GraphHopperGPXUtil;
import uk.co.ordnancesurvey.gpx.graphhopper.GraphHopperJSONUtil;
import uk.co.ordnancesurvey.gpx.graphhopper.HttpClientUtils;
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

	GraphHopperGPXUtil GPHServiceUtil = new GraphHopperGPXUtil();

	private final Map<String, String> customHeaders = new HashMap<>();

	private Map<String, ArrayList<String>> requestParameters = new HashMap<String, ArrayList<String>>();
	GraphHopperJSONUtil GPHJSONUtil = new GraphHopperJSONUtil();

	JavascriptExecutor js = (JavascriptExecutor) driver;
	WebElement we;
	private BufferedImage actualMap;
	private String httpMethod = "";
	private int actualResponseCode;
	private String actualResponseMsg;
	StringBuffer sb = new StringBuffer();

	private static final Logger LOG = LoggerFactory
			.getLogger(GraphHopperUIUtil.class);

	public GraphHopperUIUtil(String url) {

		super(BrowserPlatformOptions.getEnabledOptionsArrayList().get(0)[0]);
		try {
			baseUrl = url;
			init();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public GraphHopperUIUtil() {

		super(BrowserPlatformOptions.getEnabledOptionsArrayList().get(0)[0]);

	}

	private void init() throws InterruptedException {

		if (!testOn.equalsIgnoreCase("SERVICE")) {
			initialiseWebDriver();
		}

	}

	@Override
	public void logout() {

		if (!testOn.equalsIgnoreCase("SERVICE")) {
			LOG.info("Closing Web browser!!!");
			shutDownWebDrivers();
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

	public void verifyInstructionThroughService(String stepInstruction) {
		HashSet<Route> routeInstruction = GPHServiceUtil.getRoutes();

		Assert.assertTrue(
				"The Route instruction is not found in the gpx response",
				GPHServiceUtil.routeContainsTurn(stepInstruction.toUpperCase(),
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

			if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
				wp = buildWayPoint(wayPoint_Coordinates, wayPointDescription,
						azimuth, direction, time, distance);
				isWayPointonRouteMap = GPHServiceUtil.isWayPointOnGPXRoutes(wp);

			} else {

				wp = GPHJSONUtil.buildWayPointForJson(wayPoint_Coordinates,
						wayPointDescription, time, distance, avoidance);

				isWayPointonRouteMap = GPHJSONUtil.isWayPointinpath(wp);

			}

			break;

		default:

			isWayPointonRouteMapUI = verifyInstructionThroughUI(wayPointIndex,
					wayPointDescription, avoidance);

			if (requestParameters.get("type").get(0).equals("gpx")) {
				wp = buildWayPoint(wayPoint_Coordinates, wayPointDescription,
						azimuth, direction, time, distance);

				isWayPointonRouteMapService = GPHServiceUtil
						.isWayPointOnGPXRoutes(wp);

			} else {

				wp = GPHJSONUtil.buildWayPointForJson(wayPoint_Coordinates,
						wayPointDescription, time, distance, avoidance);
				isWayPointonRouteMapService = GPHJSONUtil.isWayPointinpath(wp);

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

			if (requestParameters.get("type").get(0).equalsIgnoreCase("GPX")) {
				aTime.setTime(GPHServiceUtil.getTotalRouteTime());
			} else {
				// aTime.setTime(GPHJsonService.getTotalRouteTime());
				aTime.setTime(GPHJSONUtil.getTotalRouteTime());
			}
			LOG.info("The total route time expected is " + eTime.getTime()
					+ " and actual is " + aTime.getTime());
			assertTrue("The total route time expected " + eTime.getTime()
					+ " is not matchin with actual " + aTime.getTime(),
					aTime.getTime() <= eTime.getTime());
			break;

		default:

			if (requestParameters.get("type").get(0).equalsIgnoreCase("GPX")) {
				aTime.setTime(GPHServiceUtil.getTotalRouteTime());
			} else {

				aTime.setTime(GPHJSONUtil.getTotalRouteTime());
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

			Waypoint trackPoint = buildWayPoint(waypointco);
			if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
				assertTrue(GPHServiceUtil.isWayPointOnTrack(trackPoint,
						GPHServiceUtil.getTracks().iterator().next()));
			}

			else {
				assertTrue(GPHJSONUtil.isWayPointinPath(trackPoint,
						GPHJSONUtil.getJsonCoordinatesAsHashSet()));

			}

		}

	}

	public void isTrackPointNotonRouteMap(
			List<Map<String, String>> trackPointsList) throws ParseException {

		for (int i = 0; i < trackPointsList.size(); i++) {

			String waypointco = (String) trackPointsList.get(i).get(
					"trackPointco");

			Waypoint trackPoint = buildWayPoint(waypointco);
			if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
				assertTrue(!GPHServiceUtil.isWayPointOnTrack(trackPoint,
						GPHServiceUtil.getTracks().iterator().next()));
			}

			else {
				assertTrue(!GPHJSONUtil.isWayPointinPath(trackPoint,
						GPHJSONUtil.getJsonCoordinatesAsHashSet()));

			}

		}

	}

	public BufferedImage takescreen(String testID) throws IOException {

		File file = new File(testID + "_screenshot.png");

		File screenshot = takeScreenShot();
		actualMap = resize(ImageIO.read(screenshot), 1000, 800);

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

	protected void addParameter(String key, String value) {
		ArrayList<String> tempList = null;
		if (value.equals("mountainbike")) {
			value = "mtb";
		}

		value = value.toLowerCase().replaceAll(" ", "");

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

		sendAndGetResponse(sb);

	}

	void sendAndGetResponse(StringBuffer sb) {
		String serviceResponse = "";
		try {
			CloseableHttpResponse httpResponse = dispatchServiceRequest(sb
					.toString());
			serviceResponse = IOUtils.toString(httpResponse.getEntity()
					.getContent(), "UTF-8");

			final StatusLine statusLine = httpResponse.getStatusLine();

			actualResponseCode = statusLine.getStatusCode();
			actualResponseMsg = statusLine.getReasonPhrase();

		} catch (IOException e) {
			LOG.info("Exception raised whilst attempting to call graphhopper server "
					+ e.getMessage());
		}

		if (serviceResponse != null && serviceResponse.length() > 0) {

			if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
				GPHServiceUtil.parseGPXFromString(serviceResponse);
			} else {
				GPHJSONUtil.parse(serviceResponse);

			}
		}

	}

	public CloseableHttpResponse dispatchServiceRequest(String requestUrl)
			throws IOException {
		String serviceUrl = requestUrl;
		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			serviceUrl += "&apikey="
					+ IntegrationTestProperties.getTestProperty("apiKey");
			LOG.debug("APPLYING KEY:");
		}

		return doSendAndGetResponse(serviceUrl);
	}

	private void addCustomHeaders(HttpUriRequest httpRequest) {
		for (Entry<String, String> header : customHeaders.entrySet()) {
			httpRequest.addHeader(header.getKey(), header.getValue());
		}
	}

	CloseableHttpResponse doSendAndGetResponse(String serviceUrl)
			throws IOException, ClientProtocolException {
		CloseableHttpClient httpClient = HttpClientUtils.createClient();

		HttpUriRequest httpRequest = null;

		switch (httpMethod) {
		case "PUT":
			httpRequest = new HttpPut(serviceUrl);

			break;
		case "GET":

			httpRequest = new HttpGet(serviceUrl);

			break;

		case "DEL":

			httpRequest = new HttpDelete(serviceUrl);

			break;

		case "POST":

			httpRequest = new HttpPost(serviceUrl);

			break;

		case "OPTIONS":

			httpRequest = new HttpOptions(serviceUrl);

			break;
		default:
			httpRequest = new HttpGet(serviceUrl);
			break;
		}

		// HttpGet httpget = new HttpGet(serviceUrl);
		addCustomHeaders(httpRequest);

		return httpClient.execute(httpRequest);
	}

	protected void getRouteFromServiceWithParameters() {

		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee"));

		} else {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl"));
		}
		sb.append("route?");

		if (!requestParameters.containsKey("type")) {
			ArrayList<String> responseType = new ArrayList<String>();
			responseType.add(IntegrationTestProperties
					.getTestProperty("routeType"));
			requestParameters.put("type", responseType);

		} else if (requestParameters.get("type").get(0).isEmpty()) {

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

	public void getRouteFromUI() {
		try {
			String weighting = requestParameters.get("weighting").get(0);

			String avoidances = requestParameters.get("avoidances").get(0);
			String vehicle = requestParameters.get("vehicle").get(0);
			if (vehicle.equalsIgnoreCase("mountainbike")) {
				vehicle = "mtb";
			}

			ArrayList<String> points = requestParameters.get("point");

			if (!requestParameters.containsKey("type")) {
				ArrayList<String> responseType = new ArrayList<String>();
				responseType.add(IntegrationTestProperties
						.getTestProperty("routeType"));
				requestParameters.put("type", responseType);

			} else if (requestParameters.get("type").get(0).isEmpty()) {

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
			case "mtb":
				clickElement(ROUTE_TYPE_MOUNTAINBIKE);
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
		} catch (Exception e) {
			LOG.info(e.getMessage());
		}

	}

	public void verifyErrorMessage(String responseMessage) {

		switch (testOn) {
		case "Web":

			navigateTo(sb.toString().replaceAll("/route?", "/"));
			waitFor(error_Message);
			verifyUIErrorMessage(responseMessage);

			break;
		case "Service":
			if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
				GPHServiceUtil.verifyMessage(responseMessage);
			} else {
				GPHJSONUtil.verifyMessage(responseMessage);
			}

			break;

		default:
			navigateTo(sb.toString().replaceAll("/route?", "/"));
			waitFor(error_Message);
			verifyUIErrorMessage(responseMessage);

			if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
				GPHServiceUtil.verifyMessage(responseMessage);
			} else {
				GPHJSONUtil.verifyMessage(responseMessage);
			}
			break;
		}
	}

	private void verifyUIErrorMessage(String responseMessage) {

		Assert.assertTrue("Web Interface: Actual Error Message" + getTextValue(error_Message) + " is not matching with :"+ responseMessage,getTextValue(error_Message).equalsIgnoreCase(responseMessage));
		

	}

	public void verifyStatusCode(int statusCode) {
		if (requestParameters.get("type").get(0).equalsIgnoreCase("gpx")) {
			GPHServiceUtil.verifyStatusCode(statusCode);
		} else {
			GPHJSONUtil.verifyStatusCode(statusCode);
		}
	}

	public Map<String, ArrayList<String>> getrequestParameters() {
		return requestParameters;
	}

	public void getNearestPoint(String paramName, String pointA) {

		StringBuffer sb = new StringBuffer();
		if (IntegrationTestProperties.getTestPropertyBool("viaApigee")) {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrlViaApigee"));
		} else {
			sb.append(IntegrationTestProperties
					.getTestProperty("graphHopperWebUrl"));
		}

		sb.append("nearest?");
		sb.append(paramName + "=");
		sb.append(pointA);

		getRouteFromServiceWithParameters(sb);

	}

	public String getNearestPoint() {

		return GPHJSONUtil.getNearestPoint();

	}

	public String getNearestPointDistance() {

		return GPHJSONUtil.getNearestPointDistance();
	}

	public void setHTTPMethod(String httpMethod) {

		this.httpMethod = httpMethod;

	}

	public void verifyHttpStatusCode(int statusCode) {
		Assert.assertTrue("Actual http Status Code" + actualResponseCode
				+ "i s not matching with " + statusCode,
				statusCode == actualResponseCode);
	}

	public void verifyHttpErrorMessage(String responseMessage) {
		Assert.assertTrue("Actual http Error Message " + actualResponseMsg
				+ " is not matching with " + responseMessage,
				responseMessage.equalsIgnoreCase(actualResponseMsg));

	}

}
