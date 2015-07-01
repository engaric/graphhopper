package com.graphhopper.util.shapes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.junit.Test;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;

import uk.co.ordnancesurvey.api.srs.LatLong;
import uk.co.ordnancesurvey.api.srs.OpenCoordConverter;

import com.graphhopper.GHResponse;
import com.graphhopper.util.PointList;

public class GHResponseCoordinateTransformerTest
{

	private static final String EPSG_27700 = "EPSG:27700";
	private static final String BNG = "bng";
	private static final String WGS84 = "wgs84";
	private static final String EPSG_4326 = "EPSG:4326";
	
	@Test
	public void testInvalidTransform() 
	{
		try {
			new GHResponseCoordinateTransformer("RANDOMCODE");
			fail("Error should be thrown");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("Srs RANDOMCODE is not a valid srs for output.", iae.getMessage());
		}
	}

	@Test
	public void testTransformPointListEPSG27700() throws FactoryException, TransformException 
	{
		GHResponse response = buildResponseObject();
		GHResponse comparatorResponse = buildResponseObject();
		GHResponseCoordinateTransformer transformer = new GHResponseCoordinateTransformer(EPSG_27700);
		transformer.transformCoordinates(response);
		
		checkPointList(response, comparatorResponse);
	}
	
	@Test
	public void testTransformPointListBNG() throws FactoryException, TransformException
	{
		GHResponse response = buildResponseObject();
		GHResponse comparatorResponse = buildResponseObject();
		GHResponseCoordinateTransformer transformer = new GHResponseCoordinateTransformer(BNG);
		transformer.transformCoordinates(response);
		
		checkPointList(response, comparatorResponse);
	}
	
	@Test
	public void testTransformPointListWGS84() throws FactoryException, TransformException
	{
		GHResponse response = buildResponseObject();
		GHResponse comparatorResponse = buildResponseObject();
		GHResponseCoordinateTransformer transformer = new GHResponseCoordinateTransformer(WGS84);
		transformer.transformCoordinates(response);
		
		assertEquals(comparatorResponse.getPoints(), response.getPoints());
	}
	
	@Test
	public void testTransformPointListEPSG4326() throws FactoryException, TransformException
	{
		GHResponse response = buildResponseObject();
		GHResponse comparatorResponse = buildResponseObject();
		GHResponseCoordinateTransformer transformer = new GHResponseCoordinateTransformer(EPSG_4326);
		transformer.transformCoordinates(response);
		
		assertEquals(comparatorResponse.getPoints(), response.getPoints());
	}
	
	private void checkPointList( GHResponse response, GHResponse comparatorResponse )
            throws FactoryException, TransformException
    {
	    PointList transformedPoints = response.getPoints();
		PointList originPoints = comparatorResponse.getPoints();
		
		Iterator<GHPoint3D> transformedIter = transformedPoints.iterator();
		Iterator<GHPoint3D> originIter = originPoints.iterator();
		
		while(transformedIter.hasNext()  && originIter.hasNext()) {
			GHPoint3D transformedCompare = transformedIter.next();
			GHPoint3D originCompare = originIter.next();
			LatLong bng = OpenCoordConverter.toBNG(originCompare.getLat(), originCompare.getLon());
			assertEquals(bng.getLatAngle(), transformedCompare.getLat(), 0);
			assertEquals(bng.getLongAngle(), transformedCompare.getLon(), 0);
		}
		if(transformedIter.hasNext() || originIter.hasNext())
			fail("PointLists hava different length");
    }

	private GHResponse buildResponseObject()
    {
	    GHResponse response = new GHResponse();
		PointList points = new PointList();
		GHPoint pointOne = new GHPoint(0, 0);
		GHPoint pointTwo = new GHPoint(53.371275,-1.80187);
		points.add(pointOne);
		points.add(pointTwo);
		response.setPoints(points);
		return response;
    }

}
