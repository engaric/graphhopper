package com.graphhopper.reader.osgb.dpn.rightOfWay;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.OsToOsmAttributeMappingVisitor;
import com.graphhopper.reader.osgb.dpn.rightofway.BridleWay;

public class BridleWayTest {
    static OsToOsmAttributeMappingVisitor visitor;
    @Mock
    Way way;

    @BeforeClass
    public static void createVisitor() {
        visitor = new BridleWay();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVisitWayAttribute() {
        visitor.visitWayAttribute("bridleway", way);
        verify(way).setTag("designation", "public_bridleway");
        verify(way).setTag("foot", "yes");
        verify(way).setTag("horse", "yes");
        verify(way).setTag("bicycle", "yes");
        verifyNoMoreInteractions(way);
    }

}