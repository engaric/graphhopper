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
import com.graphhopper.reader.osgb.roadclassification.PrivateRoadRestrictedAccess;

public class PrivateRoadRestrictedAccessTest {
    static OsToOsmAttributeMappingVisitor visitor;
    @Mock
    Way way;

    @BeforeClass
    public static void createVisitor() {
        visitor = new PrivateRoadRestrictedAccess();
    }

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testVisitWayAttribute() {
        visitor.visitWayAttribute("Private Road - Restricted Access".toLowerCase().replace(" ", "").replace("-", ""), way);
        verify(way).setTag("access", "no");
        verifyNoMoreInteractions(way);
    }
}