package com.graphhopper.reader.osgb.roadclassification;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.OsToOsmAttributeMappingVisitor;

public class DualCarriagewayTest
{
	static OsToOsmAttributeMappingVisitor visitor;
	@Mock
	Way way;

	@BeforeClass
	public static void createVisitor()
	{
		visitor = new DualCarriageway();
	}

	@Before
	public void init()
	{
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testVisitWayAttribute()
	{
		visitor.visitWayAttribute("Dual Carriageway".toLowerCase().replace(" ", "").replace("–", ""), way);
		verify(way).setTag("maxspeed:type", "GB:nsl_dual");
		verifyNoMoreInteractions(way);
	}
}