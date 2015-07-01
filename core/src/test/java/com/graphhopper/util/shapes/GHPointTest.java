/*
 *  Licensed to Peter Karich under one or more contributor license
 *  agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  Peter Karich licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the
 *  License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.util.shapes;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Peter Karich
 */
public class GHPointTest
{
    private static final String EPSG_27700 = "EPSG:27700";
    private static final String WGS_84 = "WGS84";
    private static final String WGS_84_AS_EPSG = "EPSG:4326";
    private static final String BNG = "BNG";
    
    /* latitude of BNG easting = 0 in WGS84*/
	private static final double LAT_ANGLE = 49.76680727257757;
	/* longitude of BNG northing = 0 in WGS84*/
	private static final double LON_ANGLE = -7.557159804822196;

	@Test
    public void testIsValid()
    {
        GHPoint instance = new GHPoint();
        assertFalse(instance.isValid());
        instance.lat = 1;
        assertFalse(instance.isValid());
        instance.lon = 1;
        assertTrue(instance.isValid());
    }
    
    @Test
    public void testParse() {
    	GHPoint parsedPoint = GHPoint.parse("1,2");
    	assertEquals(1, parsedPoint.getLat(), 0);
    	assertEquals(2, parsedPoint.getLon(), 0);
    }
    
    @Test
    public void testParseWithInvalidSrs() {
    	try {
    		GHPoint.parse("1,2", "RANDOMCODE");
    		fail("Error should have been thrown");
    	}
    	catch(IllegalArgumentException iae) {
    		assertEquals("Srs RANDOMCODE is not a valid srs for input.", iae.getMessage());
    	}
    }
    
    @Test
    public void testParseWithInvalidPoint() {
    	try {
    		GHPoint.parse("100000000000000000,2", "BNG");
    		fail("Error should have been thrown");
    	}
    	catch(IllegalArgumentException iae) {
    		assertEquals("Point 100000000000000000,2 is not a valid point. Point must be a comma separated coordinate in BNG projection.", iae.getMessage());
    	}
    }
    
    @Test
    public void testParseWithSrsDefault() {
    	GHPoint parsedPoint = GHPoint.parse("1,2", WGS_84.toLowerCase());
    	assertEquals(1, parsedPoint.getLat(), 0);
    	assertEquals(2, parsedPoint.getLon(), 0);
    }
    
    @Test
    public void testParseWithSrsEpsg4326() {
    	GHPoint parsedPoint = GHPoint.parse("1,2", WGS_84_AS_EPSG.toLowerCase());
    	assertEquals(1, parsedPoint.getLat(), 0);
    	assertEquals(2, parsedPoint.getLon(), 0);
    }
    
    @Test
    public void testParseWithSrsEpsg27700() {
    	GHPoint parsedPoint = GHPoint.parse("0,0", EPSG_27700.toLowerCase());
    	assertEquals(LAT_ANGLE, parsedPoint.getLat(), 0);
    	assertEquals(LON_ANGLE, parsedPoint.getLon(), 0);
    }
    
    @Test
    public void testParseWithSrsBNG() {
    	GHPoint parsedPoint = GHPoint.parse("0,0", BNG.toLowerCase());
    	assertEquals(LAT_ANGLE, parsedPoint.getLat(), 0);
    	assertEquals(LON_ANGLE, parsedPoint.getLon(), 0);
    }
}
