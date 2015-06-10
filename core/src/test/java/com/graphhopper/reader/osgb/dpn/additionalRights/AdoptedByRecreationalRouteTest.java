package com.graphhopper.reader.osgb.dpn.additionalRights;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.OsToOsmAttributeMappingVisitor;
import com.graphhopper.reader.osgb.dpn.additionalrights.AdoptedByRecreationalRoute;

public class AdoptedByRecreationalRouteTest {
    static OsToOsmAttributeMappingVisitor visitor;
    @Mock
    Way way;

    @BeforeClass
    public static void createVisitor() {
        visitor = new AdoptedByRecreationalRoute();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVisitWayAttribute() {
        visitor.visitWayAttribute("adoptedbyrecreationalroute", way);
        verify(way).setTag("network", "lwn");
        verify(way).setTag("foot", "yes");
        verifyNoMoreInteractions(way);
    }

}