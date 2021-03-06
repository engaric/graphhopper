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
import com.graphhopper.reader.osgb.dpn.rightofway.BywayOpenToAllTraffic;

public class BywayOpenToAllTrafficTest {

    static OsToOsmAttributeMappingVisitor visitor;
    @Mock
    Way way;

    @BeforeClass
    public static void createVisitor() {
        visitor = new BywayOpenToAllTraffic();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVisitWayAttribute() throws Exception {
        visitor.visitWayAttribute("bywayopentoalltraffic", way);
        verify(way).setTag("designation", "byway_open_to_all_traffic");
        verify(way).setTag("foot", "yes");
        verify(way).setTag("horse", "yes");
        verify(way).setTag("bicycle", "yes");
        verify(way).setTag("motor_vehicle", "yes");
        verifyNoMoreInteractions(way);
    }
}