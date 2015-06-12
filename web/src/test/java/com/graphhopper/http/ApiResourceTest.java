package com.graphhopper.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

	ApiResource apiResource = ApiResource.ROUTE;

	@Test
	public void testPointIsMandatoryParam() throws NoSuchParameterException,
	        InvalidParameterException
	{
		when(request.getParameterMap()).thenReturn(Collections.<String, String[]> emptyMap());
		try
		{
			apiResource.checkAllRequestParameters(request);
			fail("No exception thrown");
		} catch (MissingParameterException mpe)
		{
			assertEquals("No point parameter provided.", mpe.getMessage());
		}
	}

	@Test
	public void testVehicleIsMandatoryParam() throws NoSuchParameterException,
	        InvalidParameterException
	{
		Map<String, String[]> paramWithPoint = new HashMap<>();
		paramWithPoint.put("point", new String[] { "testval" });
		when(request.getParameterMap()).thenReturn(paramWithPoint);
		try
		{
			apiResource.checkAllRequestParameters(request);
			fail("No exception thrown");
		} catch (MissingParameterException mpe)
		{
			assertEquals("No vehicle parameter provided.", mpe.getMessage());
		}
	}

	@Test
	public void testAllMandatoryAndAllValidParameters()
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
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			apiResource.checkAllRequestParameters(request);
		} catch (MissingParameterException | NoSuchParameterException | InvalidParameterException e)
		{
			fail(e.getMessage());
		}

	}

	@Test
	public void testAllMandatoryAllValidParametersAndIncorrectOnes()
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
		// incorrect
		allParameters.put("bogus", new String[] { "x" });
		when(request.getParameterMap()).thenReturn(allParameters);
		try
		{
			apiResource.checkAllRequestParameters(request);
		} catch (MissingParameterException | InvalidParameterException exception)
		{
			fail(exception.getMessage());
		} catch (NoSuchParameterException e)
		{
			assertEquals("Parameter bogus is not a valid parameter for resource route. Valid parameters for requested resource are point, vehicle, locale, instructions, weighting, algorithm, points_encoded, debug, pretty, calc_points, type, avoidances.", e.getMessage());  
		}
	}

}
