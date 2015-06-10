package com.graphhopper.reader.osgb.roadclassification;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.OsToOsmAttributeMappingVisitor;
import com.graphhopper.routing.util.CarFlagEncoder;

public class UrbanTest
{
	private static final String _30_MPH = Integer.toString(CarFlagEncoder.THIRTY_MPH_IN_KPH);
	static OsToOsmAttributeMappingVisitor visitor;
	@Mock
	Way way;

	@BeforeClass
	public static void createVisitor()
	{
		visitor = new Urban();
	}

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testVisitWayAttribute()
	{
		visitor.visitWayAttribute("Urban".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("maxspeed");
		verify(way).setTag("maxspeed", _30_MPH);
		verifyNoMoreInteractions(way);
	}
	
	@Test
	public void testVisitWayAttributeWhenNationalSpeedLimitSet()
	{
		when(way.hasTag("maxspeed")).thenReturn(false);
		when(way.hasTag("maxspeed:type")).thenReturn(true);
		visitor.visitWayAttribute("Urban".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("maxspeed");
		verify(way).setTag("maxspeed", _30_MPH);
		verifyNoMoreInteractions(way);
	}
}