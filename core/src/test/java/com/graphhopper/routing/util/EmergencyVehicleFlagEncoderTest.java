/*
 *  Licensed to GraphHopper and Peter Karich under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for 
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper licenses this file to you under the Apache License, 
 *  Version 2.0 (the "License"); you may not use this file except in 
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.graphhopper.reader.OSMNode;
import com.graphhopper.reader.OSMWay;

/**
 * @author Stuart Adam
 * @author Mat Brett
 */
public class EmergencyVehicleFlagEncoderTest
{
    private final EncodingManager em = new EncodingManager("EMV|speedBits=7|speedFactor=1|turnCosts=0,BIKE",8);
    private final EmergencyVehicleFlagEncoder encoder = (EmergencyVehicleFlagEncoder) em.getEncoder("EMV");
    private final int speedFactor = 1;
    
    @Test
    public void testAccess()
    {
        OSMWay way = new OSMWay(1);
        assertFalse(encoder.acceptWay(way) > 0);
        way.setTag("highway", "service");
        assertTrue(encoder.acceptWay(way) > 0);
        way.setTag("access", "no");
        assertTrue(encoder.acceptWay(way) > 0);
        
        way.setTag("access", "private");
        assertTrue(encoder.acceptWay(way) > 0);

        way.clearTags();
        way.setTag("highway", "track");
        assertTrue(encoder.acceptWay(way) > 0);

        way.setTag("motorcar", "no");
        assertFalse(encoder.acceptWay(way) > 0);

        // for now allow grade1+2+3 for every country, see #253
        way.clearTags();
        way.setTag("highway", "track");
        way.setTag("tracktype", "grade2");
        assertTrue(encoder.acceptWay(way) > 0);
        way.setTag("tracktype", "grade4");
        assertFalse(encoder.acceptWay(way) > 0);

        way.clearTags();
        way.setTag("highway", "service");
        way.setTag("access", "no");
        way.setTag("motorcar", "yes");
        assertTrue(encoder.acceptWay(way) > 0);

        way.clearTags();
        way.setTag("highway", "service");
        way.setTag("access", "delivery");
        assertTrue(encoder.acceptWay(way) > 0);

        way.clearTags();
        way.setTag("highway", "unclassified");
        way.setTag("ford", "yes");
        assertFalse(encoder.acceptWay(way) > 0);
        way.setTag("motorcar", "yes");
        assertTrue(encoder.acceptWay(way) > 0);

        way.clearTags();
        way.setTag("route", "ferry");
        assertTrue(encoder.acceptWay(way) > 0);
        assertTrue(encoder.isFerry(encoder.acceptWay(way)));
        way.setTag("motorcar", "no");
        assertFalse(encoder.acceptWay(way) > 0);

        way.clearTags();
        way.setTag("route", "ferry");
        way.setTag("foot", "yes");
        assertFalse(encoder.acceptWay(way) > 0);
        assertFalse(encoder.isFerry(encoder.acceptWay(way)));
    }
    
    @Test 
    public void testStartStopOnlySections() 
    {
    	 OSMWay way = new OSMWay(1);
         way.setTag("highway", "tertiary");
         int key = 100;
         
         assertEquals("tertiary should be accesible unless stated otherwise", 0, (encoder.getLong(encoder.handleWayTags(way, 0L, 0L), key )));
         
         way.setTag("access", "no");
         assertEquals("tertiary with no access should only be accessible as a start or end point", 1, (encoder.getLong(encoder.handleWayTags(way, 0L, 0L), key )));
         
         way.setTag("access", "private");
         assertEquals("tertiary with private access (publically accessible) should always be vaid for emergencies", 0, (encoder.getLong(encoder.handleWayTags(way, 0L, 0L), key )));
    }
    
    @Test
    public void testOneway()
    {
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "primary");
        long flags = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertTrue(encoder.isForward(flags));
        assertTrue(encoder.isBackward(flags));
        way.setTag("oneway", "yes");
        flags = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertTrue(encoder.isForward(flags));
        assertFalse(encoder.isBackward(flags));
        way.clearTags();

        way.setTag("highway", "tertiary");
        flags = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertTrue(encoder.isForward(flags));
        assertTrue(encoder.isBackward(flags));
        way.clearTags();

        way.setTag("highway", "tertiary");
        way.setTag("vehicle:forward", "no");
        flags = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertFalse(encoder.isForward(flags));
        assertTrue(encoder.isBackward(flags));
        way.clearTags();

        way.setTag("highway", "tertiary");
        way.setTag("vehicle:backward", "no");
        flags = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertTrue(encoder.isForward(flags));
        assertFalse(encoder.isBackward(flags));
        way.clearTags();
    }

    @Test
    public void testMilitaryAccess()
    {
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "track");
        way.setTag("access", "military");
        assertTrue(encoder.acceptWay(way) > 0);
    }

    @Test
    public void testSetAccess()
    {
        assertTrue(encoder.isForward(encoder.setProperties(0, true, true)));
        assertTrue(encoder.isBackward(encoder.setProperties(0, true, true)));

        assertTrue(encoder.isForward(encoder.setProperties(0, true, false)));
        assertFalse(encoder.isBackward(encoder.setProperties(0, true, false)));

        assertFalse(encoder.isForward(encoder.setProperties(0, false, true)));
        assertTrue(encoder.isBackward(encoder.setProperties(0, false, true)));

        assertTrue(encoder.isForward(encoder.flagsDefault(true, true)));
        assertTrue(encoder.isBackward(encoder.flagsDefault(true, true)));

        assertTrue(encoder.isForward(encoder.flagsDefault(true, false)));
        assertFalse(encoder.isBackward(encoder.flagsDefault(true, false)));

        long flags = encoder.flagsDefault(true, true);
        // disable access
        assertFalse(encoder.isForward(encoder.setAccess(flags, false, false)));
        assertFalse(encoder.isBackward(encoder.setAccess(flags, false, false)));
    }

    @Test
    public void testMaxSpeed()
    {
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "trunk");
        way.setTag("maxspeed", "500");
        long allowed = encoder.acceptWay(way);
        long encoded = encoder.handleWayTags(way, allowed, 0);
        assertEquals(112, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "primary");
        way.setTag("maxspeed:backward", "10");
        way.setTag("maxspeed:forward", "20");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(9, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "primary");
        way.setTag("maxspeed:forward", "20");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(18, encoder.getSpeed(encoded), 1e-1);

        way = new OSMWay(1);
        way.setTag("highway", "primary");
        way.setTag("maxspeed:backward", "20");
        encoded = encoder.handleWayTags(way, encoder.acceptWay(way), 0);
        assertEquals(18, encoder.getSpeed(encoded), 1e-1);
    }

    @Test
    public void testSpeed()
    {
        // limit bigger than default road speed
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "trunk");
        way.setTag("maxspeed", "110");
        long allowed = encoder.acceptWay(way);
        long encoded = encoder.handleWayTags(way, allowed, 0);
        assertEquals(99, encoder.getSpeed(encoded), 1e-1);

        way.clearTags();
        way.setTag("highway", "residential");
        way.setTag("surface", "cobblestone");
        allowed = encoder.acceptWay(way);
        encoded = encoder.handleWayTags(way, allowed, 0);
        assertEquals(30, encoder.getSpeed(encoded), 1e-1);

        way.clearTags();
        way.setTag("highway", "track");
        allowed = encoder.acceptWay(way);
        encoded = encoder.handleWayTags(way, allowed, 0);
        assertEquals(15, encoder.getSpeed(encoded), 1e-1);

        way.clearTags();
        way.setTag("highway", "track");
        way.setTag("tracktype", "grade1");
        allowed = encoder.acceptWay(way);
        encoded = encoder.handleWayTags(way, allowed, 0);
        assertEquals(20, encoder.getSpeed(encoded), 1e-1);

        try
        {
            encoder.setSpeed(0, -1);
            assertTrue(false);
        } catch (IllegalArgumentException ex)
        {
        }
    }
    
    /**
     * Convenience method which builds an OSMWay, enriches it with tags, then returns it.
     * @return
     */
    private OSMWay wayMaker(Map<String, String> tags) {
    	 OSMWay way = new OSMWay(1);
    	 
    	 Iterator<Entry<String, String>> it = tags.entrySet().iterator();
    	    while (it.hasNext()) {
    	        Map.Entry<String, String> pair = (Map.Entry<String, String>)it.next();
    	        way.setTag((String) pair.getKey(),pair.getValue());
    	        it.remove(); 
    	    }
    	    
    	    return way;
    }
    
    /**
     * Extracted test body for testing that the maximum speeds for roads is considered to be its observed limit if that limit is less than the 
     * maximum speed given for the grade of road.
     * @param tags
     */
    private void testMaxRoadSpeed(int maxSpeedInKPH, Map<String, String> tags) {
    	OSMWay way = wayMaker(tags);
    	 long allowed = encoder.acceptWay(way);
         long encoded = encoder.handleWayTags(way, allowed, 0);
         assertEquals(factorSpeed(maxSpeedInKPH, speedFactor), encoder.getSpeed(encoded), 1e-1);
    }
    
	private int truncateMaxRoadSpeedToLegalMaximum()
    {
	    int maxLegalSpeed = CarFlagEncoder.OBSERVED_AVG_MOTORWAY_SPEED_MPH_IN_KPH;
	    int maxPossibleSpeed = encoder.maxPossibleSpeed;
		return maxPossibleSpeed > maxLegalSpeed ? maxLegalSpeed: maxPossibleSpeed;
    }
	
	private int factorSpeed(int speed, int factor) {
		return ((speed/factor)*factor);
	}
    
    @Test
    public void testMaxRoadSpeedMotorway() {
    	Map<String, String> tags = new HashMap<String, String>();
    	 tags.put("highway", "motorway");
         tags.put("maxspeed:type", "GB:motorway");
         testMaxRoadSpeed(truncateMaxRoadSpeedToLegalMaximum(), tags);
    }
    
    @Test
    public void testMaxRoadSpeedDualCarriageway() {
    	Map<String, String> tags = new HashMap<String, String>();
    	 tags.put("highway", "primary");
         tags.put("maxspeed:type", "GB:nsl_dual");
         testMaxRoadSpeed(CarFlagEncoder.OBSERVED_AVG_DUAL_CARRIAGEWAY_SPEED_MPH_IN_KPH, tags);
    }
    
    @Test
    public void testMaxRoadSpeedSingleCarriageway() {
    	Map<String, String> tags = new HashMap<String, String>();
    	 tags.put("highway", "secondary");
         tags.put("maxspeed:type", "GB:nsl_single");
         testMaxRoadSpeed(CarFlagEncoder.OBSERVED_AVG_SINGLE_CARRIAGEWAY_SPEED_MPH_IN_KPH, tags);
    }
    
    @Test
    public void testMaxRoadSpeedUrbanRoad() {
    	Map<String, String> tags = new HashMap<String, String>();
    	 tags.put("highway", "secondary");
         tags.put("maxspeed:type", "GB:urban");
         testMaxRoadSpeed(CarFlagEncoder.OBSERVED_AVG_URBAN_ROAD_SPEED_MPH_IN_KPH, tags);
    }

    @Test
    public void testSetSpeed()
    {
        assertEquals(10, encoder.getSpeed(encoder.setSpeed(0, 10)), 1e-1);
    }

    @Test
    public void testRoundabout()
    {
        long flags = encoder.setAccess(0, true, true);
        long resFlags = encoder.setBool(flags, FlagEncoder.K_ROUNDABOUT, true);
        assertTrue(encoder.isBool(resFlags, FlagEncoder.K_ROUNDABOUT));
        assertTrue(encoder.isForward(resFlags));
        assertTrue(encoder.isBackward(resFlags));

        resFlags = encoder.setBool(flags, FlagEncoder.K_ROUNDABOUT, false);
        assertFalse(encoder.isBool(resFlags, FlagEncoder.K_ROUNDABOUT));
        assertTrue(encoder.isForward(resFlags));
        assertTrue(encoder.isBackward(resFlags));

        OSMWay way = new OSMWay(1);
        way.setTag("highway", "motorway");
        flags = encoder.handleWayTags(way, encoder.acceptBit, 0);
        assertTrue(encoder.isForward(flags));
        assertTrue(encoder.isBackward(flags));
        assertFalse(encoder.isBool(flags, FlagEncoder.K_ROUNDABOUT));

        way.setTag("junction", "roundabout");
        flags = encoder.handleWayTags(way, encoder.acceptBit, 0);
        assertTrue(encoder.isForward(flags));
        assertFalse(encoder.isBackward(flags));
        assertTrue(encoder.isBool(flags, FlagEncoder.K_ROUNDABOUT));
    }

    @Test
    public void testSwapDir()
    {
        long swappedFlags = encoder.reverseFlags(encoder.flagsDefault(true, true));
        assertTrue(encoder.isForward(swappedFlags));
        assertTrue(encoder.isBackward(swappedFlags));

        swappedFlags = encoder.reverseFlags(encoder.flagsDefault(true, false));

        assertFalse(encoder.isForward(swappedFlags));
        assertTrue(encoder.isBackward(swappedFlags));

        assertEquals(0, encoder.reverseFlags(0));
    }

    @Test
    public void testBarrierAccess()
    {
        OSMNode node = new OSMNode(1, -1, -1);
        node.setTag("barrier", "lift_gate");
        node.setTag("access", "yes");
        // no barrier!
        assertTrue(encoder.handleNodeTags(node) == 0);

        node = new OSMNode(1, -1, -1);
        node.setTag("barrier", "lift_gate");
        node.setTag("bicycle", "yes");
        // barrier!
        assertTrue(encoder.handleNodeTags(node) > 0);

        node = new OSMNode(1, -1, -1);
        node.setTag("barrier", "lift_gate");
        node.setTag("access", "yes");
        node.setTag("bicycle", "yes");
        // should this be a barrier for motorcars too?
        // assertTrue(encoder.handleNodeTags(node) > 0);

        node = new OSMNode(1, -1, -1);
        node.setTag("barrier", "lift_gate");
        node.setTag("access", "no");
        node.setTag("motorcar", "yes");
        // no barrier!
        assertTrue(encoder.handleNodeTags(node) == 0);

        node = new OSMNode(1, -1, -1);
        node.setTag("barrier", "bollard");
        // barrier!
        assertTrue(encoder.handleNodeTags(node) > 0);

        // ignore other access tags for absolute barriers!
        node.setTag("motorcar", "yes");
        // still barrier!
        assertTrue(encoder.handleNodeTags(node) > 0);
    }

    @Test
    public void testTurnFlagEncoding_noCosts()
    {
        FlagEncoder tmpEnc = new CarFlagEncoder(8, 5, 0);
        EncodingManager em = new EncodingManager(tmpEnc);

        long flags_r0 = tmpEnc.getTurnFlags(true, 0);
        long flags_0 = tmpEnc.getTurnFlags(false, 0);

        long flags_r20 = tmpEnc.getTurnFlags(true, 0);
        long flags_20 = tmpEnc.getTurnFlags(false, 20);

        assertEquals(0, tmpEnc.getTurnCost(flags_r0), 1e-1);
        assertEquals(0, tmpEnc.getTurnCost(flags_0), 1e-1);

        assertEquals(0, tmpEnc.getTurnCost(flags_r20), 1e-1);
        assertEquals(0, tmpEnc.getTurnCost(flags_20), 1e-1);

        assertFalse(tmpEnc.isTurnRestricted(flags_r0));
        assertFalse(tmpEnc.isTurnRestricted(flags_0));

        assertFalse(tmpEnc.isTurnRestricted(flags_r20));
        assertFalse(tmpEnc.isTurnRestricted(flags_20));
    }

    @Test
    public void testTurnFlagEncoding_withCosts()
    {
        FlagEncoder tmpEncoder = new CarFlagEncoder(8, 5, 127);
        EncodingManager em = new EncodingManager(tmpEncoder);

        long flags_r0 = tmpEncoder.getTurnFlags(true, 0);
        long flags_0 = tmpEncoder.getTurnFlags(false, 0);
        assertTrue(Double.isInfinite(tmpEncoder.getTurnCost(flags_r0)));
        assertEquals(0, tmpEncoder.getTurnCost(flags_0), 1e-1);
        assertTrue(tmpEncoder.isTurnRestricted(flags_r0));
        assertFalse(tmpEncoder.isTurnRestricted(flags_0));

        long flags_r20 = tmpEncoder.getTurnFlags(true, 0);
        long flags_20 = tmpEncoder.getTurnFlags(false, 20);
        assertTrue(Double.isInfinite(tmpEncoder.getTurnCost(flags_r20)));
        assertEquals(20, tmpEncoder.getTurnCost(flags_20), 1e-1);
        assertTrue(tmpEncoder.isTurnRestricted(flags_r20));
        assertFalse(tmpEncoder.isTurnRestricted(flags_20));

        long flags_r220 = tmpEncoder.getTurnFlags(true, 0);
        try
        {
            tmpEncoder.getTurnFlags(false, 220);
            assertTrue(false);
        } catch (Exception ex)
        {
        }
        assertTrue(Double.isInfinite(tmpEncoder.getTurnCost(flags_r220)));
        assertTrue(tmpEncoder.isTurnRestricted(flags_r220));
    }

    @Test
    public void testMaxValue()
    {
        CarFlagEncoder instance = new CarFlagEncoder(8, 0.5, 0);
        EncodingManager em = new EncodingManager(instance);
        OSMWay way = new OSMWay(1);
        way.setTag("highway", "motorway_link");
        way.setTag("maxspeed", "60 mph");
        long flags = instance.handleWayTags(way, 1, 0);

        // double speed = AbstractFlagEncoder.parseSpeed("60 mph");
        // => 96.56 * 0.9 => 86.9
        assertEquals(86.9, instance.getSpeed(flags), 1e-1);
        flags = instance.reverseFlags(flags);
        assertEquals(86.9, instance.getSpeed(flags), 1e-1);
        
        // test that maxPossibleValue  is not exceeded
        way = new OSMWay(2);
        way.setTag("highway", "motorway_link");
        way.setTag("maxspeed", "70 mph");
        flags = instance.handleWayTags(way, 1, 0);
        assertEquals(100, instance.getSpeed(flags), 1e-1);
    }

    @Test
    public void testRegisterOnlyOnceAllowed()
    {
        CarFlagEncoder instance = new CarFlagEncoder(8, 0.5, 0);
        EncodingManager em = new EncodingManager(instance);
        try
        {
            em = new EncodingManager(instance);
            assertTrue(false);
        } catch (IllegalStateException ex)
        {
        }
    }

    @Test
    public void testSetToMaxSpeed()
    {
        OSMWay way = new OSMWay(12);
        way.setTag("maxspeed", "90");
        assertEquals(90, encoder.getMaxSpeed(way), 1e-2);
    }

    @Test
    public void testFordAccess()
    {
        OSMNode node = new OSMNode(0, 0.0, 0.0);
        node.setTag("ford", "yes");

        OSMWay way = new OSMWay(1);
        way.setTag("highway", "unclassified");
        way.setTag("ford", "yes");

        // Node and way are initially blocking
        assertTrue(encoder.isBlockFords());
        assertFalse(encoder.acceptWay(way) > 0);
        assertTrue(encoder.handleNodeTags(node) > 0);

        try
        {
            // Now they are passable
            encoder.setBlockFords(false);
            assertTrue(encoder.acceptWay(way) > 0);
            assertFalse(encoder.handleNodeTags(node) > 0);
        } finally
        {
            encoder.setBlockFords(true);
        }
    }

    @Test
    public void testCombination()
    {
        OSMWay way = new OSMWay(123);
        way.setTag("highway", "cycleway");
        way.setTag("sac_scale", "hiking");        

        long flags = em.acceptWay(way);
        long edgeFlags = em.handleWayTags(way, flags, 0);
        assertFalse(encoder.isBackward(edgeFlags));
        assertFalse(encoder.isForward(edgeFlags));
        assertTrue(em.getEncoder("bike").isBackward(edgeFlags));
        assertTrue(em.getEncoder("bike").isForward(edgeFlags));
    }
}
