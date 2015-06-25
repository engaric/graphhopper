package com.graphhopper.reader.osgb;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.graphhopper.reader.OSMWay;
import com.graphhopper.routing.util.CarFlagEncoder;
import com.graphhopper.routing.util.DefaultEdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.OsCarFlagEncoder;
import com.graphhopper.storage.GraphHopperStorage;

public class OSCarFlagEncoderTest extends AbstractOsItnReaderTest
{

	private OsCarFlagEncoder osCarEncoder;

	@Before
	public void initEncoding()
	{
		if (turnCosts)
		{
			osCarEncoder = new OsCarFlagEncoder("speedBits=5|speedfactor=5|turncosts=3");
		} else
		{
			osCarEncoder = new OsCarFlagEncoder("speedBits=5|speedfactor=5|turncosts=0");
		}

		carOutEdges = new DefaultEdgeFilter(osCarEncoder, false, true);
		carInEdges = new DefaultEdgeFilter(osCarEncoder, true, false);
		encodingManager = createEncodingManager();
	}

	protected EncodingManager createEncodingManager()
	{
		return new EncodingManager(osCarEncoder);
	}

	@Test
	public void testReadSimplePrivateCrossRoads() throws IOException
	{
		final boolean turnRestrictionsImport = false;
		final boolean is3D = false;
		final GraphHopperStorage graph = configureStorage(turnRestrictionsImport, is3D);

		final File file = new File(
		        "./src/test/resources/com/graphhopper/reader/os-itn-simple-private-crossroad.xml");
		readGraphFile(graph, file);
		assertEquals(5, graph.getNodes());
		checkSimpleNodeNetwork(graph);
		checkAccessNodeNetwork(graph, osCarEncoder, true);
	}

	@Test
	public void testReadSimplePrivateRestrictedCrossRoads() throws IOException
	{
		final boolean turnRestrictionsImport = false;
		final boolean is3D = false;
		final GraphHopperStorage graph = configureStorage(turnRestrictionsImport, is3D);

		final File file = new File(
		        "./src/test/resources/com/graphhopper/reader/os-itn-simple-private-restricted-crossroad.xml");
		readGraphFile(graph, file);
		assertEquals(5, graph.getNodes());
		checkSimpleNodeNetwork(graph);
		checkAccessNodeNetwork(graph, osCarEncoder, true);
	}
	
	@Test
	/**
	 * Tests that the max speed for an OS car is 70mph.
	 * 
	 * Note that the 70mph is factored to be divisible by five such that it fits the current factor 5 speed encoding.
	 * @throws IOException
	 */
	public void testMaxSpeedForCaris70mph() throws IOException {
		
		final int factoredMPHinKPH = (CarFlagEncoder.SEVENTY_MPH_IN_KPH/5)*5;
		
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "motorway");
        way.setTag("maxspeed", "120");
        long allowed = osCarEncoder.acceptWay(way);
        long encoded = osCarEncoder.handleWayTags(way, allowed, 0);
        assertEquals(factoredMPHinKPH, osCarEncoder.getSpeed(encoded), 1e-1);

	}
}
