package com.graphhopper.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet Filter to convert all request parameters into lower case
 *
 * @author phopkins
 *
 */
public class LowerCaseParameterFilter implements Filter
{

	protected static Logger LOG = LoggerFactory.getLogger(LowerCaseParameterFilter.class);

	protected static class LowerCaseRequest extends HttpServletRequestWrapper
	{

		private Map<String, String[]> lowerCaseParams = new HashMap<>();

		public LowerCaseRequest( final HttpServletRequest request )
		{
			super(request);
			Map<String, String[]> orignalParams = request.getParameterMap();
			for (String name : orignalParams.keySet())
			{
				String lower = name.toLowerCase();
				if (!lowerCaseParams.containsKey(lower))
				{
					lowerCaseParams.put(lower, new String[0]);
				}
				lowerCaseParams.put(lower,
						concat(lowerCaseParams.get(lower), orignalParams.get(name)));
			}
		}

		@Override
		public Map<String, String[]> getParameterMap()
		{

			return Collections.unmodifiableMap(lowerCaseParams);
		}

		@Override
		public String getParameter( final String name )
		{
			String[] values = getParameterValues(name);
			if (values != null)
			{
				return values[0];
			} else
			{
				return null;
			}
		}

		@Override
		public Enumeration<String> getParameterNames()
		{
			return Collections.enumeration(lowerCaseParams.keySet());
		}

		@Override
		public String[] getParameterValues( final String name )
		{
			return lowerCaseParams.get(name);
		}

		public static <T> T[] concat( T[] first, T[] second )
		{
			T[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}
	}

	@Override
	public void doFilter( final ServletRequest request, final ServletResponse response,
			final FilterChain chain ) throws IOException, ServletException
	{
		if (request instanceof HttpServletRequest)
		{
			chain.doFilter(new LowerCaseRequest((HttpServletRequest) request), response);
		} else
		{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{

	}

	@Override
	public void destroy()
	{

	}
}