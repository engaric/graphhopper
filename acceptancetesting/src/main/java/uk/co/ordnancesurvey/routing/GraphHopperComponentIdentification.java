package uk.co.ordnancesurvey.routing;

import static uk.co.ordnancesurvey.webtests.base.ComponentValueSource.INNER_HTML;
import uk.co.ordnancesurvey.webtests.base.ComponentByXPATH;
import uk.co.ordnancesurvey.webtests.base.ComponentClass;
import uk.co.ordnancesurvey.webtests.base.ComponentID;
import uk.co.ordnancesurvey.webtests.base.ComponentIdentification;
import uk.co.ordnancesurvey.webtests.base.ComponentIdentifier;
import uk.co.ordnancesurvey.webtests.base.ComponentTableById;
import uk.co.ordnancesurvey.webtests.base.GridFinder;

public class GraphHopperComponentIdentification implements
		ComponentIdentification {

	public static ComponentIdentifier waypoint = new ComponentID("0_searchBox");
	public static final ComponentIdentifier ADD_WAYPOINT= new ComponentByXPATH("//*[@class='pointAdd']");
	public static final  ComponentIdentifier ROUTE_TYPE_CAR = new ComponentID("car");
	public static final  ComponentIdentifier ROUTE_TYPE_BIKE = new ComponentID("bike");
	public static final  ComponentIdentifier ROUTE_TYPE_EMERGENCY=new ComponentID("emv");
	public static final  ComponentIdentifier ROUTE_TYPE_MOUNTAINBIKE = new ComponentByXPATH("//*[@id='mtb']");
	public static final  ComponentIdentifier ROUTE_TYPE_WALK = new ComponentID("foot");
	public static final  ComponentIdentifier ROUTE_SEARCH = new ComponentID("searchButton");
	public static final  ComponentIdentifier WAYPOINT_ONMAP = new ComponentByXPATH("//*[@class='leaflet-popup-content']", INNER_HTML);
	public static final  GridFinder INSTRUCTIONS=new ComponentTableById("instructions", INNER_HTML);
	public static final  ComponentIdentifier TOTAL_ROUTE_TIME =new ComponentByXPATH("//*[@id='info']/div[1]",INNER_HTML);
	public static final ComponentIdentifier  MAP=new ComponentByXPATH("//*[@id='map']");
	public static final ComponentIdentifier  ZOOM_OUT=new ComponentByXPATH("//*[@title='Zoom out']");
	public static final ComponentIdentifier  ZOOM_IN=new ComponentByXPATH("//*[@title='Zoom in']");
	public static final ComponentIdentifier DROPDOWN =new ComponentByXPATH("//span/div/span/div[*]/p/strong");
	public static final ComponentIdentifier SETTINGSBUTTON =new ComponentByXPATH("//*[@id='settings-btn']");
	public static final ComponentIdentifier AVOIDANCE_AROAD=new ComponentByXPATH("//*[@name='avoidances[]'][@value='aroad']");
	public static final ComponentIdentifier AVOIDANCE_BOULDERS=new ComponentByXPATH("//*[@name='avoidances[]'][@value='boulders']");
	public static final ComponentIdentifier AVOIDANCE__CLIFF=new ComponentByXPATH("//*[@name='avoidances[]'][@value='cliff']");
	public static final ComponentIdentifier AVOIDANCE__INLANDWATERr=new ComponentByXPATH("//*[@name='avoidances[]'][@value='inlandwater']");
	public static final ComponentIdentifier AVOIDANCE__MARSH=new ComponentByXPATH("//*[@name='avoidances[]'][@value='marsh']");
	public static final ComponentIdentifier AVOIDANCE__QUARRYORPIT=new ComponentByXPATH("//*[@name='avoidances[]'][@value='quarryorpit']");
	public static final ComponentIdentifier AVOIDANCE__SCREE=new ComponentByXPATH("//*[@name='avoidances[]'][@value='scree']");
	public static final ComponentIdentifier AVOIDANCE__ROCK=new ComponentByXPATH("//*[@name='avoidances[]'][@value='rock']");
	public static final ComponentIdentifier AVOIDANCE__MUD=new ComponentByXPATH("//*[@name='avoidances[]'][@value='mud']");
	public static final ComponentIdentifier AVOIDANCE__SAND=new ComponentByXPATH("//*[@name='avoidances[]'][@value='sand']");
	public static final ComponentIdentifier AVOIDANCE_SHINGLE=new ComponentByXPATH("//*[@name='avoidances[]'][@value='shingle']");
	public static final ComponentIdentifier FASTEST_RBUTTON=new ComponentByXPATH("//input[@value='fastest']");
	public static final ComponentIdentifier SHORTEST_RBUTTON=new ComponentByXPATH("//input[@value='shortest']");
	public static final ComponentIdentifier ERROR_MESSAGE= new ComponentByXPATH("//*[@class='error']");
	public static final ComponentIdentifier PRIVATE_ACCESS_ALLOWED = new ComponentByXPATH("//*[@name='access' and @value='allow']");
	public static final ComponentIdentifier PRIVATE_ACCESS_NOTALLOWED = new ComponentByXPATH("//*[@name='access' and @value='disallow']");
	public static final ComponentIdentifier  PROJECTION = new ComponentByXPATH("//*[@class='srs']");
	
}
