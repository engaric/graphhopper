package com.graphhopper.reader.osgb;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.graphhopper.routing.util.DefaultEdgeFilter;
import com.graphhopper.routing.util.EmergencyVehicleFlagEncoder;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphHopperStorage;

public class EmergencyVehicleFlagEncoderTest extends AbstractOsItnReaderTest{

    private EmergencyVehicleFlagEncoder emvEncoder;
    
    @Before
    public void initEncoding() {
        if (turnCosts) {
            emvEncoder = new EmergencyVehicleFlagEncoder("speedBits=5|speedfactor=5|turncosts=3");
        } else {
        	emvEncoder = new EmergencyVehicleFlagEncoder("speedBits=5|speedfactor=5|turncosts=0");
        }

        carOutEdges = new DefaultEdgeFilter(emvEncoder, false, true);
        carInEdges = new DefaultEdgeFilter(emvEncoder, true, false);
        encodingManager = createEncodingManager();
    }

	protected EncodingManager createEncodingManager() {
        return new EncodingManager(emvEncoder);
    }
    
    @Test
    public void testReadSimplePrivateCrossRoads() throws IOException {
        final boolean turnRestrictionsImport = false;
        final boolean is3D = false;
        final GraphHopperStorage graph = configureStorage(turnRestrictionsImport, is3D);

        final File file = new File("./src/test/resources/com/graphhopper/reader/os-itn-simple-private-crossroad.xml");
        readGraphFile(graph, file);
        assertEquals(5, graph.getNodes());
        checkSimpleNodeNetwork(graph);
    }
}
