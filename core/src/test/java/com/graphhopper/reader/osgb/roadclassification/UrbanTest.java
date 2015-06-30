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

public class UrbanTest
{
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
		when(way.hasTag("maxspeed:type", "GB:nsl_single")).thenReturn(true);
		visitor.visitWayAttribute("Urban".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("maxspeed:type", "GB:nsl_single");
		verify(way).setTag("maxspeed:type", "GB:urban");
		verifyNoMoreInteractions(way);
	}
	
	@Test
	public void testVisitWayAttributeWhenNationalSpeedLimitSet()
	{
		when(way.hasTag("maxspeed")).thenReturn(false);
		when(way.hasTag("maxspeed:type", "GB:nsl_single")).thenReturn(true);
		visitor.visitWayAttribute("Urban".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).hasTag("maxspeed:type", "GB:nsl_single");
		verify(way).setTag("maxspeed:type", "GB:urban");
		verifyNoMoreInteractions(way);
	}
}