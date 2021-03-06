package com.graphhopper.routing.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.graphhopper.reader.Relation;
import com.graphhopper.reader.Way;
import com.graphhopper.util.Helper;

public class BusFlagEncoder extends AbstractFlagEncoder
{
	protected final Map<String, Integer> trackTypeSpeedMap = new HashMap<String, Integer>();
	protected final Set<String> badSurfaceSpeedMap = new HashSet<String>();
	/**
	 * A map which associates string to speed. Get some impression:
	 * http://www.itoworld.com/map/124#fullscreen
	 * http://wiki.openstreetmap.org/wiki/OSM_tags_for_routing/Maxspeed
	 */
	protected final Map<String, Integer> defaultSpeedMap = new HashMap<String, Integer>();

	/**
	 * Should be only instantied via EncodingManager
	 */
	public BusFlagEncoder()
	{
		this(5, 5, 0);
	}

	public BusFlagEncoder( String propertiesStr )
	{
		this((int) parseLong(propertiesStr, "speedBits", 5), parseDouble(propertiesStr,
		        "speedFactor", 5), parseBoolean(propertiesStr, "turnCosts", false) ? 3 : 0);
	}

	public BusFlagEncoder( int speedBits, double speedFactor, int maxTurnCosts )
	{
		super(speedBits, speedFactor, maxTurnCosts);
		restrictions.addAll(Arrays.asList("motorcar", "motor_vehicle", "vehicle", "access"));

		restrictedValues.add("private");
		restrictedValues.add("agricultural");
		restrictedValues.add("forestry");
		restrictedValues.add("no");
		restrictedValues.add("restricted");
		restrictedValues.add("delivery");

		intendedValues.add("yes");
		intendedValues.add("permissive");

		potentialBarriers.add("gate");
		potentialBarriers.add("lift_gate");
		potentialBarriers.add("kissing_gate");
		potentialBarriers.add("swing_gate");

		absoluteBarriers.add("bollard");
		absoluteBarriers.add("stile");
		absoluteBarriers.add("turnstile");
		absoluteBarriers.add("cycle_barrier");
		absoluteBarriers.add("motorcycle_barrier");
		absoluteBarriers.add("block");

		trackTypeSpeedMap.put("grade1", 20); // paved
		trackTypeSpeedMap.put("grade2", 15); // now unpaved - gravel mixed with ...
		trackTypeSpeedMap.put("grade3", 10); // ... hard and soft materials
		trackTypeSpeedMap.put("grade4", 5); // ... some hard or compressed materials
		trackTypeSpeedMap.put("grade5", 5); // ... no hard materials. soil/sand/grass

		badSurfaceSpeedMap.add("cobblestone");
		badSurfaceSpeedMap.add("grass_paver");
		badSurfaceSpeedMap.add("gravel");
		badSurfaceSpeedMap.add("sand");
		badSurfaceSpeedMap.add("paving_stones");
		badSurfaceSpeedMap.add("dirt");
		badSurfaceSpeedMap.add("ground");
		badSurfaceSpeedMap.add("grass");

		// autobahn
		defaultSpeedMap.put("motorway", 100);
		defaultSpeedMap.put("motorway_link", 70);
		defaultSpeedMap.put("motorroad", 90);
		// bundesstraße
		defaultSpeedMap.put("trunk", 70);
		defaultSpeedMap.put("trunk_link", 65);
		// linking bigger town
		defaultSpeedMap.put("primary", 65);
		defaultSpeedMap.put("primary_link", 60);
		// linking towns + villages
		defaultSpeedMap.put("secondary", 60);
		defaultSpeedMap.put("secondary_link", 50);
		// streets without middle line separation
		defaultSpeedMap.put("tertiary", 50);
		defaultSpeedMap.put("tertiary_link", 40);
		defaultSpeedMap.put("unclassified", 30);
		defaultSpeedMap.put("residential", 30);
		// spielstraße
		defaultSpeedMap.put("living_street", 5);
		defaultSpeedMap.put("service", 20);
		// unknown road
		defaultSpeedMap.put("road", 20);
		// forestry stuff
		defaultSpeedMap.put("track", 15);

		defaultSpeedMap.put("Motorway", 100);
		defaultSpeedMap.put("A Road", 55);
		defaultSpeedMap.put("B Road", 35);
		defaultSpeedMap.put("Minor Road", 35);
		defaultSpeedMap.put("Local Street", 35);
		defaultSpeedMap.put("Alley", 35);
		defaultSpeedMap.put("A Road-Single Carriageway", 55);
		defaultSpeedMap.put("B Road", 35);
		defaultSpeedMap.put("Minor Road", 35);
		defaultSpeedMap.put("Local Street", 35);
		defaultSpeedMap.put("Alley", 35);
		defaultSpeedMap.put("Motorway", 100);
		defaultSpeedMap.put("A Road", 55);
		defaultSpeedMap.put("B Road", 35);
		defaultSpeedMap.put("Minor Road", 35);
		defaultSpeedMap.put("Local Street", 35);
		defaultSpeedMap.put("Alley", 35);
		defaultSpeedMap.put("A Road-Dual Carriageway", 55);
		defaultSpeedMap.put("B Road", 35);
		defaultSpeedMap.put("Minor Road", 35);
		defaultSpeedMap.put("Local Street", 35);
		defaultSpeedMap.put("Alley", 35);
		defaultSpeedMap.put("Motorway-Slip Road", 100);
		defaultSpeedMap.put("A Road-Slip Road", 55);
		defaultSpeedMap.put("B Road", 35);
		defaultSpeedMap.put("Minor Road", 35);
		defaultSpeedMap.put("Local Street", 35);
		defaultSpeedMap.put("Alley", 35);
		defaultSpeedMap.put("Motorway-Roundabout", 100);
		defaultSpeedMap.put("A Road-Roundabout", 55);
		defaultSpeedMap.put("B Road-Roundabout", 35);
		defaultSpeedMap.put("Minor Road-Roundabout", 35);
		defaultSpeedMap.put("Local Street-Roundabout", 35);

		// Should we be including these in car flags?
		defaultSpeedMap.put("Pedestrianised Street", 0);
		defaultSpeedMap.put("Private Road - Restricted Access", 35);
		defaultSpeedMap.put("Private Road - Publicly Accessible", 35);
		defaultSpeedMap.put("Alley", 0);

		// osgb:type
		// vehicleQualifierTypeExclusions.add("Buses");
		vehicleQualifierTypeExclusions.add("Coaches");
		vehicleQualifierTypeExclusions.add("Mopeds");
		vehicleQualifierTypeExclusions.add("Motor Cycles");
		vehicleQualifierTypeExclusions.add("HGV's");
		vehicleQualifierTypeExclusions.add("LGV's");
		vehicleQualifierTypeExclusions.add("Towed Caravans");
		vehicleQualifierTypeExclusions.add("Cycles");
		vehicleQualifierTypeExclusions.add("Tracked Vehicles");
		// osgb:use
		vehicleQualifierTypeExclusions.add("Taxi");
		vehicleQualifierTypeExclusions.add("Taxis"); // Added from analysing the actual data
		vehicleQualifierTypeExclusions.add("School Bus");
		vehicleQualifierTypeExclusions.add("Patron");
		vehicleQualifierTypeExclusions.add("Access");
		vehicleQualifierTypeExclusions.add("Resident");
		vehicleQualifierTypeExclusions.add("Emergency Vehicle");
		// vehicleQualifierTypeExclusions.add("Public Transport");
		vehicleQualifierTypeExclusions.add("Authorised Vehicle");
		// vehicleQualifierTypeExclusions.add("Local Bus");
		// vehicleQualifierTypeExclusions.add("Local Buses"); // Added from analysing the actual
		// data
		vehicleQualifierTypeExclusions.add("Escorted Traffic");
		vehicleQualifierTypeExclusions.add("Loading And Unloading"); // Added from analysing the
																	 // actual data

		vehicleQualifierTypeInclusions.add("Motor Vehicles");
		vehicleQualifierTypeInclusions.add("All Vehicles");
		// temp inclusions for testing
		vehicleQualifierTypeInclusions.add("Buses");

		vehicleQualifierTypeInclusions.add("Local Bus");
		vehicleQualifierTypeInclusions.add("Local Buses");
		vehicleQualifierTypeInclusions.add("Public Transport");

	}

	/**
	 * Define the place of the speedBits in the edge flags for car.
	 */
	@Override
	public int defineWayBits( int index, int shift )
	{
		// first two bits are reserved for route handling in superclass
		shift = super.defineWayBits(index, shift);
		speedEncoder = new EncodedDoubleValue("Speed", shift, speedBits, speedFactor,
		        defaultSpeedMap.get("secondary"), defaultSpeedMap.get("motorway"));
		return shift + speedEncoder.getBits();
	}

	protected double getSpeed( Way way )
	{
		String highwayValue = way.getTag("highway");
		Integer speed = defaultSpeedMap.get(highwayValue);
		if (speed == null)
			throw new IllegalStateException(toString() + ", no speed found for:" + highwayValue);

		if (highwayValue.equals("track"))
		{
			String tt = way.getTag("tracktype");
			if (!Helper.isEmpty(tt))
			{
				Integer tInt = trackTypeSpeedMap.get(tt);
				if (tInt != null)
					speed = tInt;
			}
		}

		return speed;
	}

	@Override
	public long acceptWay( Way way )
	{
		String highwayValue = way.getTag("highway");
		if (highwayValue == null)
		{
			if (way.hasTag("route", ferries))
			{
				String motorcarTag = way.getTag("motorcar");
				if (motorcarTag == null)
					motorcarTag = way.getTag("motor_vehicle");

				if (motorcarTag == null && !way.hasTag("foot") && !way.hasTag("bicycle")
				        || "yes".equals(motorcarTag))
					return acceptBit | ferryBit;
			}
			return 0;
		}

		if ("track".equals(highwayValue))
		{
			String tt = way.getTag("tracktype");
			if (tt != null && !tt.equals("grade1"))
				return 0;
		}

		if (!defaultSpeedMap.containsKey(highwayValue))
			return 0;

		if (way.hasTag("impassable", "yes") || way.hasTag("status", "impassable"))
			return 0;

		// do not drive street cars into fords
		boolean carsAllowed = way.hasTag(restrictions, intendedValues);
		if (isBlockFords() && ("ford".equals(highwayValue) || way.hasTag("ford")) && !carsAllowed)
			return 0;

		// check access restrictions
		if (way.hasTag(restrictions, restrictedValues) && !carsAllowed)
			return 0;

		// do not drive cars over railways (sometimes incorrectly mapped!)
		if (way.hasTag("railway") && !way.hasTag("railway", acceptedRailways))
			return 0;

		return acceptBit;
	}

	@Override
	public long handleWayTags( Way way, long allowed, long relationFlags )
	{
		if (!isAccept(allowed))
			return 0;

		long encoded;
		if (!isFerry(allowed))
		{
			// get assumed speed from highway type
			double speed = getSpeed(way);
			speed = applyMaxSpeed(way, speed, true);

			// limit speed to max 30 km/h if bad surface
			if (speed > 30 && way.hasTag("surface", badSurfaceSpeedMap))
				speed = 30;

			encoded = setSpeed(0, speed);

			boolean isRoundabout = way.hasTag("junction", "roundabout");
			if (isRoundabout)
				encoded = setBool(encoded, K_ROUNDABOUT, true);

			if (way.hasTag("oneway", oneways) || isRoundabout)
			{
				if (way.hasTag("oneway", "-1"))
					encoded |= backwardBit;
				else
					encoded |= forwardBit;
			} else
				encoded |= directionBitMask;

		} else
		{
			encoded = handleFerryTags(way, defaultSpeedMap.get("living_street"),
			        defaultSpeedMap.get("service"), defaultSpeedMap.get("residential"));
			encoded |= directionBitMask;
		}

		return encoded;
	}

	public String getWayInfo( Way way )
	{
		String str = "";
		String highwayValue = way.getTag("highway");
		// for now only motorway links
		if ("motorway_link".equals(highwayValue))
		{
			String destination = way.getTag("destination");
			if (!Helper.isEmpty(destination))
			{
				int counter = 0;
				for (String d : destination.split(";"))
				{
					if (d.trim().isEmpty())
						continue;

					if (counter > 0)
						str += ", ";

					str += d.trim();
					counter++;
				}
			}
		}
		if (str.isEmpty())
			return str;
		// I18N
		if (str.contains(","))
			return "destinations: " + str;
		else
			return "destination: " + str;
	}

	@Override
	public String toString()
	{
		return "bus";
	}

	@Override
	public long handleRelationTags( Relation relation, long oldRelationFlags )
	{
		return oldRelationFlags;
	}
}
