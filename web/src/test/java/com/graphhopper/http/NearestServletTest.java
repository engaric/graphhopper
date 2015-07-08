package com.graphhopper.http;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.QueryResult;

public class NearestServletTest
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
    QueryResult queryResult;

    private Map<String, String[]> requestParameters;

    private NearestServlet nearestServlet;

    private PrintWriter writer;

    private final String[] SINGLE_POINT = { "50.728198,-3.534516" };
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

        nearestServlet = injector.getInstance(NearestServlet.class);
        requestParameters = new HashMap<>();
        writer = new PrintWriter("httpServletResponseContents");
        when(httpServletResponse.getWriter()).thenReturn(writer);
        when(graphHopper.hasElevation()).thenReturn(false);
    }

    @After
    public void tearDown() throws Exception
    {
        injector = null;
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponseWithMoreThanOnePoint()
            throws IOException, ServletException
    {

        requestParameters.put("point", INVALID_TOO_MANY_POINTS);
        when(httpServletRequest.getParameterMap()).thenReturn(requestParameters);

        nearestServlet.doGet(httpServletRequest, httpServletResponse);

        writer.flush();
        System.err.println(FileUtils
                .readFileToString(new File("httpServletResponseContents"), "UTF-8")
                );
        assertTrue(FileUtils
                .readFileToString(new File("httpServletResponseContents"), "UTF-8")
                .contains(
                        "Only one point should be specified and it must be a comma separated coordinate in WGS84 projection."));
    }

    @Test
    public void testDoGetHttpServletRequestHttpServletResponseWithOnePoint()
            throws ServletException, IOException
    {

        requestParameters.put("point", SINGLE_POINT);
        when(httpServletRequest.getParameterMap()).thenReturn(requestParameters);
        when(graphHopper.getLocationIndex()).thenReturn(locationIndex);
        when(locationIndex.findClosest(anyDouble(), anyDouble(), any(EdgeFilter.class)))
                .thenReturn(queryResult);
        when(queryResult.isValid()).thenReturn(false);

        nearestServlet.doGet(httpServletRequest, httpServletResponse);

        writer.flush();
        assertTrue(FileUtils.readFileToString(new File("httpServletResponseContents"), "UTF-8")
                .contains("Nearest point cannot be found!"));
    }

}