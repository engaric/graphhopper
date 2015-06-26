package com.graphhopper.http;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class LowerCaseParameterFilterTest
{

	private HttpServletRequest originalRequest;

	@Before
	public void setup()
	{
		originalRequest = Mockito.mock(HttpServletRequest.class);
	}

	@Test
	public void testLowerCaseRequest_singlePoint()
	{
		Map<String, String[]> origParameters = new HashMap<>();
		origParameters.put("POINT", new String[] { "123,456" });
		when(originalRequest.getParameterMap()).thenReturn(origParameters);

		LowerCaseParameterFilter.LowerCaseRequest request = new LowerCaseParameterFilter.LowerCaseRequest(
		        originalRequest);
		Map<String, String[]> resultParameters = request.getParameterMap();
		// Check the original value is no longer there
		assertNull(resultParameters.get("POINT"));
		// Check that the lower case value is as expected
		assertNotNull(resultParameters.get("point"));
		assertEquals(1, resultParameters.get("point").length);
		assertEquals("123,456", resultParameters.get("point")[0]);
	}

	@Test
	public void testLowerCaseRequest_multiPoint()
	{
		Map<String, String[]> origParameters = new HashMap<>();
		origParameters.put("POINT", new String[] { "123,456" });
		origParameters.put("pOiNt", new String[] { "789,321" });
		when(originalRequest.getParameterMap()).thenReturn(origParameters);

		LowerCaseParameterFilter.LowerCaseRequest request = new LowerCaseParameterFilter.LowerCaseRequest(
		        originalRequest);
		Map<String, String[]> resultParameters = request.getParameterMap();
		assertNull(resultParameters.get("POINT"));
		assertNull(resultParameters.get("pOiNt"));

		assertNotNull(resultParameters.get("point"));
		assertEquals(2, resultParameters.get("point").length);
		assertEquals("123,456", resultParameters.get("point")[0]);
		assertEquals("789,321", resultParameters.get("point")[1]);
	}

	@Test
	public void testLowerCaseRequest_getParameterValues()
	{
		Map<String, String[]> origParameters = new HashMap<>();
		origParameters.put("POINT", new String[] { "123,456" });
		origParameters.put("pOiNt", new String[] { "789,321" });
		when(originalRequest.getParameterMap()).thenReturn(origParameters);

		LowerCaseParameterFilter.LowerCaseRequest request = new LowerCaseParameterFilter.LowerCaseRequest(
				originalRequest);
		assertNull(request.getParameterValues("POINT"));
		assertNull(request.getParameterValues("pOiNt"));

		assertNotNull(request.getParameterValues("point"));
		String[] parameterValues = request.getParameterValues("point");

		assertEquals(2, parameterValues.length);
		assertEquals("123,456", parameterValues[0]);
		assertEquals("789,321", parameterValues[1]);
	}

	@Test
	public void testLowerCaseRequest_getParameterNames()
	{
		Map<String, String[]> origParameters = new HashMap<>();
		origParameters.put("POINT", new String[] { "123,456" });
		when(originalRequest.getParameterMap()).thenReturn(origParameters);

		LowerCaseParameterFilter.LowerCaseRequest request = new LowerCaseParameterFilter.LowerCaseRequest(
		        originalRequest);

		Enumeration<String> enumer = request.getParameterNames();

		assertTrue(enumer.hasMoreElements());
		assertEquals("point", enumer.nextElement());
		assertFalse(enumer.hasMoreElements());
	}

	@Test
	public void testLowerCaseRequest_concat()
	{
		String[] result = LowerCaseParameterFilter.LowerCaseRequest.concat(
		        new String[] { "123,456" }, new String[] { "789,321" });
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals("123,456", result[0]);
		assertEquals("789,321", result[1]);
	}

	/**
	 * When a HttpServletRequest is passed into LowerCaseParameterFilter.doFilter the
	 * FilterChain.doFilter method will get passed a LowerCaseParameterFilter.LowerCaseRequest
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testLowerCaseParameterFilter_doFilter_HttpServletRequest() throws IOException,
	        ServletException
	{
		LowerCaseParameterFilter filter = new LowerCaseParameterFilter();
		FilterChain filterChainMock = Mockito.mock(FilterChain.class);

		filter.doFilter(originalRequest, null, filterChainMock);

		verify(filterChainMock).doFilter(
				Matchers.isA(LowerCaseParameterFilter.LowerCaseRequest.class),
				(ServletResponse) Matchers.any());
	}

	/**
	 * When a not HttpServletRequest is passed into LowerCaseParameterFilter.doFilter the
	 * FilterChain.doFilter method will get passed the servletRequest passed to the
	 * LowerCaseParameterFilter.doFilter method
	 *
	 * @throws IOException
	 * @throws ServletException
	 */
	@Test
	public void testLowerCaseParameterFilter_doFilter_ServletRequest() throws IOException,
	        ServletException
	{
		LowerCaseParameterFilter filter = new LowerCaseParameterFilter();
		ServletRequest servletRequest = Mockito.mock(ServletRequest.class);
		FilterChain filterChainMock = Mockito.mock(FilterChain.class);

		filter.doFilter(servletRequest, null, filterChainMock);

		verify(filterChainMock).doFilter(Matchers.eq(servletRequest),
				(ServletResponse) Matchers.any());
	}

}
