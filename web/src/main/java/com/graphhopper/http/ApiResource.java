package com.graphhopper.http;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public enum ApiResource
{
	ROUTE("route", new String[] { "point", "vehicle" }, new String[] { "point", "vehicle",
			"locale", "instructions", "weighting", "algorithm", "points_encoded", "debug",
			"pretty", "calc_points", "type", "avoidances", "private", "srs" }), NEAREST("nearest",
					new String[] { "point"}, new String[] { "point", "srs" }), INFO("info", new String[] {},
							new String[] {"srs"});

	public String[] getMandatoryValues()
	{
		return mandatoryValues;
	}

	public String[] getValidValues()
	{
		return validValues;
	}

	public String getResourceName()
	{
		return resourceName;
	}

	private String resourceName;
	private String[] mandatoryValues;
	private String[] validValues;

	private ApiResource( String resourceName, String[] mandatoryValues, String[] validValues )
	{
		this.resourceName = resourceName;
		this.mandatoryValues = mandatoryValues;
		this.validValues = validValues;
	}

	/**
	 * Scans through all the request parameters. Checks if the mandatory field exists for a
	 * resource. Checks if all the request parameters are valid for the resource. Throws appropriate
	 * exceptions if not
	 *
	 * @param HttpServletRequest
	 * @param APIResource
	 *
	 * @throws MissingParameterException
	 * @throws NoSuchParameterException
	 */
	public void checkAllRequestParameters( HttpServletRequest request )
			throws MissingParameterException, NoSuchParameterException, InvalidParameterException
	{
		checkMandatoryParameters(request);
		checkValidParameters(request);
	}

	/**
	 *  Check if the mandatory parameter exists in the request
	 * @param request
	 * @throws MissingParameterException
	 */
	private void checkMandatoryParameters(HttpServletRequest request)
			throws MissingParameterException {
		String value = this.getMandatoryValues()[0];
		boolean mandatoryValueExists = checkMandatoryValue(request, value);
		if (mandatoryValueExists && this.getMandatoryValues().length > 1)
		{
			value = this.getMandatoryValues()[1];
			mandatoryValueExists = checkMandatoryValue(request, value);
		}
		if (!mandatoryValueExists)
		{
			throw new MissingParameterException(value);
		}
	}

	/**
	 * Checks if the given mandatory value exists in the request parameters
	 *
	 * @param parameters Enumeration<String>
	 * @param mandatoryValue
	 * @return
	 */
	private boolean checkMandatoryValue( HttpServletRequest request, String mandatoryValue )
	{
		Set<String> keys = request.getParameterMap().keySet();
		for (String key : keys)
		{
			if (mandatoryValue.equals(key))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if all the valid parameters exist in the request for the resource
	 *
	 * @param request
	 * @throws NoSuchParameterException
	 */
	private void checkValidParameters( HttpServletRequest request ) throws NoSuchParameterException
	{
		// Now check for all the valid parameters for the resource
		List<String> list = Arrays.asList(this.getValidValues());
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet())
		{
			String name = entry.getKey().toLowerCase();
			if (!list.contains(name))
			{
				String msg = getInvalidPara​meterMessage(name, getResourceName(), getValidValues());
				throw new NoSuchParameterException(msg);
			}
		}
	}

	/**
	 * Builds the message when the parameter provided is invalid
	 *
	 * @param invalid
	 * @param parameter
	 * @param validValues
	 * @return message String
	 */
	private String getInvalidPara​meterMessage( String invalid, String parameter,
			String[] validValues )
	{
		StringBuilder sb = new StringBuilder();

		sb.append("Parameter ").append(invalid).append(" is not a valid parameter for resource ")
		.append(parameter).append(". Valid parameters for requested resource are ");

		List<String> validList = Arrays.asList(validValues);
		for (String valid : validList)
		{
			sb.append(valid).append(", ");
		}
		String message = sb.substring(0, sb.length() - 2) + ".";

		return message;
	}

}
