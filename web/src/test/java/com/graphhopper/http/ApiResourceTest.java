package com.graphhopper.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static com.graphhopper.http.ApiResource.ROUTE;
import static com.graphhopper.http.ApiResource.NEAREST;
import static com.graphhopper.http.ApiResource.INFO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

//The values of parameters are immaterial, there are only two classes of equivalence: [nullPointer] and [nonNullString].
//One distinguishes between 'thenReturn(null)' and 'thenReturn(*nonNullString*)' only.

@RunWith(MockitoJUnitRunner.class)
public class ApiResourceTest
{

	@Mock
	HttpServletRequest request;


	@Test
	public void testPointIsMandatoryParamForRoute() throws NoSuchParameterException,
	        InvalidParameterException
	{
		when(request.getParameterMap()).thenReturn(Collections.<String, String[]> emptyMap());
		try
		{
			ROUTE.checkAllRequestParameters(request);
			fail("No exception thrown");
		} catch (MissingParameterException mpe)
		{
			assertEquals("No point parameter provided.", mpe.getMessage());
		}
	}

	@Test
	public void testVehicleIsMandatoryParamForRoute() throws NoSuchParameterException,
	        InvalidParameterException
	{
		Map<String, String[]> paramWithPoint = new HashMap<>();
		paramWithPoint.put("point", new String[] { "testval" });
		when(request.getParameterMap()).thenReturn(paramWithPoint);
		try
		{
			ROUTE.checkAllRequestParameters(request);
			fail("No exception thrown");
		} catch (MissingParameterException mpe)
		{
			assertEquals("No vehicle parameter provided.", mpe.getMessage());
		}
	}

	@Test
	public void testAllMandatoryAndAllValidParametersForRoute()
	{
		Map<String, String[]> allParameters = new HashMap<>();
		// mandatory
		allParameters.put("point", new String[] { "0" });
		allParameters.put("vehicle", new String[] { "car" });
		// valid
		allParameters.put("locale", new String[] { "x" });
		allParameters.put("instructions", new String[] { "x" });
		allParameters.put("weighting", new String[] { "x" });
		allParameters.put("algorithm", new String[] { "x" });
		allParameters.put("points_encoded", new String[] { "x" });
		allParameters.put("debug", new String[] { "x" });
		allParameters.put("pretty", new String[] { "x" });
		allParameters.put("calc_points", new String[] { "x" });
		allParameters.put("type", new String[] { "x" });
		allParameters.put("avoidances", new String[] { "x" });
		allParameters.put("private", new String[] { "x" });
		allParameters.put("srs", new String[] {"x"});
		allParameters.put("output_srs", new String[] {"x"});
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			ROUTE.checkAllRequestParameters(request);
		} catch (MissingParameterException | NoSuchParameterException | InvalidParameterException e)
		{
			fail(e.getMessage());
		}

	}

	@Test
	public void testAllMandatoryAllValidParametersAndIncorrectOnesForRoute()
	{
		Map<String, String[]> allParameters = new HashMap<>();
		// mandatory
		allParameters.put("point", new String[] { "0" });
		allParameters.put("vehicle", new String[] { "car" });
		// valid
		allParameters.put("locale", new String[] { "x" });
		allParameters.put("instructions", new String[] { "x" });
		allParameters.put("weighting", new String[] { "x" });
		allParameters.put("algorithm", new String[] { "x" });
		allParameters.put("points_encoded", new String[] { "x" });
		allParameters.put("debug", new String[] { "x" });
		allParameters.put("pretty", new String[] { "x" });
		allParameters.put("calc_points", new String[] { "x" });
		allParameters.put("type", new String[] { "x" });
		allParameters.put("avoidances", new String[] { "x" });
		allParameters.put("private", new String[] { "x" });
		allParameters.put("srs", new String[] { "x" });
		allParameters.put("output_srs", new String[] {"x"});
		// incorrect
		allParameters.put("bogus", new String[] { "x" });
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			ROUTE.checkAllRequestParameters(request);
		} catch (MissingParameterException | InvalidParameterException exception)
		{
			fail(exception.getMessage());
		} catch (NoSuchParameterException e)
		{
			assertEquals(
			        "Parameter bogus is not a valid parameter for resource route. Valid parameters for requested resource are point, vehicle, locale, instructions, weighting, algorithm, points_encoded, debug, pretty, calc_points, type, avoidances, private, srs.",
			        e.getMessage());
		}
	}
	
	@Test
	public void testPointMandatoryForNearest() throws NoSuchParameterException, InvalidParameterException
	{
		Map<String, String[]> allParameters = new HashMap<>();
		allParameters.put("srs", new String[] { "0" });
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			NEAREST.checkAllRequestParameters(request);
			fail("No exception thrown when manadatory parameter point not present");
		} catch (MissingParameterException mpe)
		{
			assertEquals("No point parameter provided.", mpe.getMessage());
		}
	}
	
	@Test
	public void testAllMandatoryAndAllValidParametersForNearest()
	{
		Map<String, String[]> allParameters = new HashMap<>();
		// mandatory
		allParameters.put("point", new String[] { "0" });
		// valid
		allParameters.put("srs", new String[] { "x" });
		allParameters.put("output_srs", new String[] {"x"});
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			NEAREST.checkAllRequestParameters(request);
		} catch (MissingParameterException | NoSuchParameterException | InvalidParameterException e)
		{
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testAllMandatoryAllValidParametersAndIncorrectOnesForNearest()
	{
		Map<String, String[]> allParameters = new HashMap<>();
		// mandatory
		allParameters.put("point", new String[] { "0" });
		// valid
		allParameters.put("srs", new String[] { "x" });
		allParameters.put("output_srs", new String[] {"x"});
		// incorrect
		allParameters.put("bogus", new String[] { "x" });
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			NEAREST.checkAllRequestParameters(request);
		} catch (MissingParameterException | InvalidParameterException exception)
		{
			fail(exception.getMessage());
		} catch (NoSuchParameterException e)
		{
			assertEquals(
			        "Parameter bogus is not a valid parameter for resource nearest. Valid parameters for requested resource are point, srs.",
			        e.getMessage());
		}
	}
	
	
	@Test
	public void testNoMandatoryParametersForInfo() throws NoSuchParameterException, InvalidParameterException
	{
		Map<String, String[]> allParameters = new HashMap<>();
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			INFO.checkAllRequestParameters(request);
		} catch (MissingParameterException mpe)
		{
			fail("No mandatory parameters");
		}
	}

}
