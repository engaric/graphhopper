package com.graphhopper.reader.osgb.roadclassification;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.OsToOsmAttributeMappingVisitor;

public class SlipRoadTest
{
	static OsToOsmAttributeMappingVisitor visitor;
	@Mock
	Way way;

	@BeforeClass
	public static void createVisitor()
	{
		visitor = new SlipRoad();
	}

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testVisitWayAttributeWhenHighwayNotSet()
	{
		when(way.hasTag("highway")).thenReturn(false);
		visitor.visitWayAttribute("Slip Road".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("highway");
		verifyNoMoreInteractions(way);
	}
	
	@Test
	public void testVisitWayAttributeWhenHighwayMotorwaySet()
	{
		when(way.hasTag("highway")).thenReturn(true);
		when(way.getTag("highway")).thenReturn("motorway");
		visitor.visitWayAttribute("Slip Road".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("highway");
		verify(way).getTag("highway");
		verify(way).setTag("highway","motorway_link");
		verifyNoMoreInteractions(way);
	}
	
	@Test
	public void testVisitWayAttributeWhenHighwayLocalRoadSet()
	{
		when(way.hasTag("highway")).thenReturn(true);
		when(way.getTag("highway")).thenReturn("tertiary");
		visitor.visitWayAttribute("Slip Road".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("highway");
		verify(way).getTag("highway");
		verify(way).setTag("highway","tertiary_link");
		verifyNoMoreInteractions(way);
	}
}