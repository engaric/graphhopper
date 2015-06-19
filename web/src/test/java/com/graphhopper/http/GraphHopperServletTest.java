package com.graphhopper.http;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.AlgorithmOptions;
import com.graphhopper.routing.util.AbstractAvoidanceDecorator;
import com.graphhopper.routing.util.AbstractFlagEncoder;
import com.graphhopper.routing.util.EncoderDecorator;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.storage.GraphStorage;

public class GraphHopperServletTest
{

    private Injector injector;

    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    Map<String, String[]> allParameters;

    GraphHopperServlet graphHopperServlet;

    GHResponse value = new GHResponse();

    @Mock
    private GraphHopper graphHopper;

    @Mock
    private EncodingManager encodingManager;

    @Mock
    private AbstractFlagEncoder flagEncoder;

    @Mock
    private GraphStorage graphStorage;

    @Mock
    private List<EncoderDecorator> encoderDecorators;

    @Mock
    private Iterator<EncoderDecorator> iterator;

    @Mock
    private AbstractAvoidanceDecorator abstractAvoidanceDecorator;

    private final String[] POINTS = { "50.728198,-3.534516", "50.726807,-3.530156" };
    private final String UNPARSABLE_POINT = "50A.45";

    private final String[] LOCALES = { "bg", "ca", "cz", "de_DE", "el", "en_US", "es", "fa", "fil",
            "fi", "fr", "gl", "he", "hu_HU", "it", "ja", "ne", "nl", "pl_PL", "pt_BR", "pt_PT",
            "ro", "ru", "si", "sk", "sv_SE", "tr", "uk", "vi_VI", "zh_CN", "en_GB" };
    private final String WRONG_LOCALE = "kl_KL";

    private final String[] ALGORITHMS = { AlgorithmOptions.ASTAR, AlgorithmOptions.ASTAR_BI,
            AlgorithmOptions.DIJKSTRA, AlgorithmOptions.DIJKSTRA_BI,
            AlgorithmOptions.DIJKSTRA_ONE_TO_MANY };
    private final String WRONG_ALGORITHM = "dekker";

    private final String[] VEHICLES = { "car", "bike", "foot" };
    private final String WRONG_VEHICLE = "flying_carpet";

    private final String[] INSTRUCTIONS = { "false" }, POINTS_ENCODED = { "true", "false" },
            DEBUG = { "true", "false" }, PRETTY = { "true", "false" }, CALC_POINTS = { "true",
                    "false" };
    private final String WRONG_INSTRUCTION = "uncertain";
    private final String WRONG_POINTS_ENCODED = "uncertain";
    private final String WRONG_DEBUG = "uncertain";
    private final String WRONG_PRETTY = "uncertain";
    private final String WRONG_CALC_POINTS = "uncertain";

    private final String[] WEIGHTINGS = { "fastest", "shortest" };
    private final String WRONG_WEIGHTING = "slowest";

    private final String[] AVOIDANCES = { "aroad", "boulders", "cliff", "inlandwater", "marsh",
            "quarryorpit", "scree", "rock", "mud", "sand", "shingle", "spoil", "tidalwater" };
    private final String WRONG_AVOIDANCE = "zxcv";

    private final String[] TYPES = { "json" };
    private final String WRONG_TYPE = "pdf";

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
            }
        });

        graphHopperServlet = injector.getInstance(GraphHopperServlet.class);
        when(httpServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));
        allParameters = new HashMap<>();
        when(graphHopper.getEncodingManager()).thenReturn(encodingManager);
        when(graphHopper.hasElevation()).thenReturn(false);

        when(encodingManager.supports(anyString())).thenReturn(true);
        when(encodingManager.getEncoder(anyString())).thenReturn(flagEncoder);
        when(flagEncoder.toString()).thenReturn("unknown");

        when(graphHopper.route(any(GHRequest.class))).thenReturn(value);

    }

    @After
    public void tearDown() throws Exception
    {
        injector = null;
    }

    private String buildErrorMessageString( String paramValue, String paramName,
            List<String> validValues )
    {
        StringBuilder errMesg = new StringBuilder(paramValue)
                .append(" is not a valid value for parameter ").append(paramName)
                .append(". Valid values are ");
        for (int i = 0; i < validValues.size(); i++)
        {
            String validStr = validValues.get(i);
            if (i == validValues.size() - 1)
            {
                errMesg.append(" or ");
            }
            errMesg.append(validStr);
            if (i < validValues.size() - 2)
            {
                errMesg.append(", ");
            }
        }
        return errMesg.toString();
    }

    private String buildErrorMessageString( String paramValue, String paramName,
            String... validValues )
    {
        return buildErrorMessageString(paramValue, paramName, Arrays.asList(validValues));
    }

    @Test
    public void statusCodeForCorrectParameters() throws ServletException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        graphHopperServlet.doGet(httpServletRequest, httpServletResponse);
        verify(httpServletResponse, never()).setStatus(SC_BAD_REQUEST);
    }

    @Test
    public void testGetGHResponseWithValidPoints() throws IOException, MissingParameterException,
            NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                .hasErrors());
    }

    @Test
    public void testGetGHResponseWithUnparsablePoint() throws IOException,
            MissingParameterException, NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", new String[] { UNPARSABLE_POINT });
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(
                "Point "
                        + UNPARSABLE_POINT
                        + " is not a valid point. Point must be a comma separated coordinate in WGS84 projection.",
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectLocales() throws IOException,
            MissingParameterException, NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("instructions", new String[] { "false" });
        for (String locale : LOCALES)
        {
            allParameters.put("locale", new String[] { locale });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithWrongLocale() throws IOException, MissingParameterException,
            NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { WRONG_LOCALE });
        allParameters.put("instructions", new String[] { "false" });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(buildErrorMessageString(WRONG_LOCALE, "locale", LOCALES), graphHopperServlet
                .getGHResponse(httpServletRequest, httpServletResponse).getErrors().get(0)
                .getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectAlgorithms() throws IOException,
            MissingParameterException, NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String algorithm : ALGORITHMS)
        {
            allParameters.put("algorithm", new String[] { algorithm });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithWrongAlgorithm() throws IOException,
            MissingParameterException, NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("algorithm", new String[] { WRONG_ALGORITHM });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(buildErrorMessageString(WRONG_ALGORITHM, "algorithm", ALGORITHMS),
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectTypes() throws IOException, MissingParameterException,
            NoSuchParameterException, InvalidParameterException, ServletException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String type : TYPES)
        {
            allParameters.put("type", new String[] { type });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());

        }
    }

    @Test
    public void testGetGHResponseWithWrongType() throws IOException, MissingParameterException,
            NoSuchParameterException, InvalidParameterException, ServletException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("type", new String[] { WRONG_TYPE });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(WRONG_TYPE
                + " is not a valid value for parameter type. Valid values are GPX or JSON.",
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectVehicles() throws IOException,
            MissingParameterException, NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                .hasErrors());
    }

    @Test
    public void testGetGHResponseWithWrongVehicle() throws IOException, MissingParameterException,
            NoSuchParameterException, InvalidParameterException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { WRONG_VEHICLE });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        when(graphHopper.getGraph()).thenReturn(graphStorage);
        when(graphStorage.getEncodingManager()).thenReturn(encodingManager);
        when(encodingManager.supports(anyString())).thenReturn(false);
        when(encodingManager.toString()).thenReturn("*supported vehicles*");

        GHResponse ghResponse = graphHopperServlet.getGHResponse(httpServletRequest,
                httpServletResponse);

        assertEquals(
                "Vehicle flying_carpet is not a valid vehicle. Valid vehicles are *supported vehicles*",
                ghResponse.getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectInstructions() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        for (String instruction : INSTRUCTIONS)
        {
            allParameters.put("instructions", new String[] { instruction });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            GHResponse ghResponse = graphHopperServlet.getGHResponse(httpServletRequest,
                    httpServletResponse);

            assertFalse(ghResponse.hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectInstruction() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("instructions", new String[] { WRONG_INSTRUCTION });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(
                WRONG_INSTRUCTION
                        + " is not a valid value for parameter instructions. Valid values are true or false",
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectPointsEncoded() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String point_encoded : POINTS_ENCODED)
        {
            allParameters.put("points_encoded", new String[] { point_encoded });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectPointsEncoded() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("points_encoded", new String[] { WRONG_POINTS_ENCODED });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(
                buildErrorMessageString(WRONG_POINTS_ENCODED, "points_encoded", POINTS_ENCODED),
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectDebug() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String debug : DEBUG)
        {
            allParameters.put("debug", new String[] { debug });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectDebug() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("debug", new String[] { WRONG_DEBUG });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(buildErrorMessageString(WRONG_DEBUG, "debug", DEBUG), graphHopperServlet
                .getGHResponse(httpServletRequest, httpServletResponse).getErrors().get(0)
                .getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectPretty() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String pretty : PRETTY)
        {
            allParameters.put("pretty", new String[] { pretty });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectPretty() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("pretty", new String[] { WRONG_PRETTY });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(buildErrorMessageString(WRONG_PRETTY, "pretty", PRETTY), graphHopperServlet
                .getGHResponse(httpServletRequest, httpServletResponse).getErrors().get(0)
                .getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectCalcPoints() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String calcPoint : CALC_POINTS)
        {
            allParameters.put("calc_points", new String[] { calcPoint });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectCalcPoints() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        allParameters.put("calc_points", new String[] { WRONG_CALC_POINTS });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        assertEquals(buildErrorMessageString(WRONG_CALC_POINTS, "calc_points", CALC_POINTS),
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

    @Test
    public void testGetGHResponseWithCorrectWeighting() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String weighting : WEIGHTINGS)
        {
            allParameters.put("weighting", new String[] { weighting });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectWeighting() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        allParameters.put("weighting", new String[] { WRONG_WEIGHTING });

        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        GHResponse ghResponse = graphHopperServlet.getGHResponse(httpServletRequest,
                httpServletResponse);

        assertEquals(1, ghResponse.getErrors().size());
        // TODO Generate appropriate error message in GraphhopperServletClass
        // assertEquals(buildErrorMessageString(WRONG_WEIGHTING, "calc_points", WEIGHTINGS),
        // ghResponse.getErrors().get(0).getMessage());

    }

    @Test
    public void testGetGHResponseWithCorrectAvoidances() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });
        allParameters.put("instructions", new String[] { "false" });

        for (String avoidances : AVOIDANCES)
        {
            allParameters.put("avoidances", new String[] { avoidances });
            when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

            when(flagEncoder.getEncoderDecorators()).thenReturn(encoderDecorators);
            when(encoderDecorators.iterator()).thenReturn(iterator);
            when(iterator.hasNext()).thenReturn(true, false);
            when(iterator.next()).thenReturn(abstractAvoidanceDecorator);
            when(abstractAvoidanceDecorator.getEdgeAttributesOfInterestNames()).thenReturn(
                    AVOIDANCES);

            assertFalse(graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                    .hasErrors());
        }
    }

    @Test
    public void testGetGHResponseWithIncorrectAvoidances() throws MissingParameterException,
            NoSuchParameterException, InvalidParameterException, IOException
    {
        allParameters.put("point", POINTS);
        allParameters.put("vehicle", new String[] { VEHICLES[0] });
        allParameters.put("locale", new String[] { LOCALES[0] });

        allParameters.put("avoidances", new String[] { WRONG_AVOIDANCE });
        when(httpServletRequest.getParameterMap()).thenReturn(allParameters);

        when(flagEncoder.getEncoderDecorators()).thenReturn(encoderDecorators);
        when(encoderDecorators.iterator()).thenReturn(iterator);
        when(iterator.hasNext()).thenReturn(true, false);
        when(iterator.next()).thenReturn(abstractAvoidanceDecorator);
        when(abstractAvoidanceDecorator.getEdgeAttributesOfInterestNames()).thenReturn(AVOIDANCES);

        assertEquals(buildErrorMessageString(WRONG_AVOIDANCE, "avoidances", AVOIDANCES),
                graphHopperServlet.getGHResponse(httpServletRequest, httpServletResponse)
                        .getErrors().get(0).getMessage());
    }

}
