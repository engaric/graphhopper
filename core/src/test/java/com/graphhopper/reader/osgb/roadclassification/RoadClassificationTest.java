package com.graphhopper.reader.osgb.roadclassification;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoadClassificationTest
{

	@Test
	public void testLookup()
	{
		assertEquals(RoadClassification.NULLCLASSIFICATION, RoadClassification.lookup("Unknown"));
		assertEquals(RoadClassification.ALLEY, RoadClassification.lookup("Alley"));
		assertEquals(RoadClassification.AROAD, RoadClassification.lookup("A Road"));
		assertEquals(RoadClassification.BROAD, RoadClassification.lookup("B Road"));
		assertEquals(RoadClassification.DUALCARRIAGEWAY, RoadClassification.lookup("Dual Carriageway"));
		assertEquals(RoadClassification.LOCALSTREET, RoadClassification.lookup("Local Street"));
		assertEquals(RoadClassification.MINORROAD, RoadClassification.lookup("Minor Road"));
		assertEquals(RoadClassification.MOTORWAY, RoadClassification.lookup("Motorway"));
		assertEquals(RoadClassification.PEDESTRIANISEDSTREET, RoadClassification.lookup("Pedestrianised Street"));
		assertEquals(RoadClassification.PRIVATEROADPUBLICLYACCESSIBLE, RoadClassification.lookup("Private Road – Publicly Accessible"));
		assertEquals(RoadClassification.PRIVATEROADRESTRICTEDACCESS, RoadClassification.lookup("Private Road – Restricted Access"));
		assertEquals(RoadClassification.SINGLECARRIAGEWAY, RoadClassification.lookup("Single Carriageway"));
		assertEquals(RoadClassification.URBAN, RoadClassification.lookup("Urban"));
		assertEquals(RoadClassification.ROUNDABOUT, RoadClassification.lookup("Roundabout"));
		assertEquals(RoadClassification.SLIPROAD, RoadClassification.lookup("Slip Road"));
	}

}
