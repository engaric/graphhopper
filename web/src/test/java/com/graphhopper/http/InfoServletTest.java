package com.graphhopper.http;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.storage.GraphStorage;
import com.graphhopper.storage.StorableProperties;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.shapes.BBox;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class InfoServletTest
{

    private Injector injector;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private LocationIndex locationIndex;

    @Mock
    private GraphHopper graphHopper;

    @Mock
    private EncodingManager encodingManager;

    @Mock
    private FlagEncoder flagEncoder;

    @Mock
    private GraphStorage graph;


    @Mock
    private StorableProperties storableProperties;


    @Mock
    QueryResult queryResult;

    private Map<String, String[]> requestParameters;

    private InfoServlet infoServlet;

    private PrintWriter writer;

    private final String[] SINGLE_POINT = { "50.728198,-3.534516" };
    private final String[] SINGLE_VEHICLE = { "car" };
    private final String[] INVALID_TOO_MANY_POINTS = { "50.728198,-3.534516", "50.726807,-3.530156" };

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        injector = Guice.createInjector(new AbstractModule()
        {
            @Override
            protected void configure()
            {
                bind(GraphHopper.class).toInstance(graphHopper);
                bind(Boolean.class).annotatedWith(Names.named("internalErrorsAllowed")).toInstance(
                        false);
                bind(Boolean.class).annotatedWith(Names.named("jsonpAllowed")).toInstance(false);
                bind(String.class).annotatedWith(Names.named("defaultSrs")).toInstance("WGS84");
            }
        });

        infoServlet = injector.getInstance(InfoServlet.class);
        requestParameters = new HashMap<>();
        writer = new PrintWriter("httpServletResponseContents");
        when(httpServletResponse.getWriter()).thenReturn(writer);
        when(graphHopper.hasElevation()).thenReturn(false);
        when(graphHopper.getGraph()).thenReturn(graph);
        BBox bounds = new BBox(1,2,3,4,5,6);
        when(graph.getBounds()).thenReturn(bounds);


        when(graphHopper.getGraph().getEncodingManager()).thenReturn(encodingManager);
        when(graph.getProperties()).thenReturn(storableProperties);

        when(storableProperties.get("osmreader.import.date")).thenReturn("importDate");
        when(storableProperties.get("dpn.data_version")).thenReturn("dpnVersion");
        when(storableProperties.get("itn.data_version")).thenReturn("itnVersion");
        when(storableProperties.get("hn.data_version")).thenReturn("hnVersion");
    }

    @After
    public void tearDown() throws Exception
    {
        injector = null;
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponse()
            throws IOException, ServletException
    {

        infoServlet.doGet(httpServletRequest, httpServletResponse);

        writer.flush();
        System.err.println(FileUtils
                .readFileToString(new File("httpServletResponseContents"), "UTF-8")
                );
        assertEquals("{\"features\":{\"encodingManager\":{\"elevation\":false}}," +
                "\"build_date\":\"2016-03-04T11:26:32+0000\"," +
                "\"supported_vehicles\":[\"encodingManager\"]," +
                "\"version_type\":\"!! NON-PRODUCTION RELEASE !!\"," +
                "\"bbox\":[1,3,2,4]," +
                "\"hn_data_version\":\"hnVersion\"," +
                "\"dpn_data_version\":\"dpnVersion\"," +
                "\"version\":\"0.4.5.11\"," +
                "\"itn_data_version\":\"itnVersion\"," +
                "\"import_date\":\"importDate\"}", FileUtils
                .readFileToString(new File("httpServletResponseContents"), "UTF-8"));
    }
}
