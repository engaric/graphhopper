package com.graphhopper.http;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class GraphHopperServletTest
{

	ApiResource apiResource = ApiResource.ROUTE;

	@Test
	public void testErrorStatusCode() throws ServletException, IOException
	{
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

		when(httpServletRequest.getParameter("vehicle")).thenReturn("car");
		when(httpServletRequest.getParameter("point")).thenReturn(null);

		GraphHopperServlet servlet = new GraphHopperServlet();
		HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
		when(httpServletResponse.getWriter()).thenReturn(mock(PrintWriter.class));
		servlet.doGet(httpServletRequest, httpServletResponse);
		verify(httpServletResponse).setStatus(SC_BAD_REQUEST);

	}

}
