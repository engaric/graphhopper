package com.graphhopper.util.shapes;

import java.security.InvalidParameterException;

import org.geotools.referencing.CRS;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import uk.co.ordnancesurvey.api.srs.LatLong;
import uk.co.ordnancesurvey.api.srs.OpenCoordConverter;

import com.graphhopper.GHResponse;
import com.graphhopper.util.PointList;

public class GHResponseCoordinateTransformer
{

	private static final String BNG = "BNG";
	private CoordinateReferenceSystem targetCRS = OpenCoordConverter.wgs84CoordRefSystem;

	public GHResponseCoordinateTransformer( String crsCode ) throws InvalidParameterException
	{
		if(!crsCode.equalsIgnoreCase("WGS84"))
	        try
            {
	            targetCRS = crsCode.equalsIgnoreCase(BNG) ? OpenCoordConverter.bngCoordRefSystem : CRS
	                .decode(crsCode);
            } catch (FactoryException e)
            {
	           throw new IllegalArgumentException("Srs " + crsCode + " is not a valid srs for output.");
            }
	}

	public void transformCoordinates( GHResponse response )
	{
		if(targetCRS.equals(OpenCoordConverter.wgs84CoordRefSystem))
			return;
		
		PointList points = response.getPoints();
		PointList transformedPoints = new PointList(points.getSize(), points.is3D());
		try
		{
			GHPoint transformedPoint;
			for (GHPoint ghPoint : points)
			{
				transformedPoint = doTransformPoint(ghPoint);

				transformedPoints.add(transformedPoint);
			}

			response.setPoints(transformedPoints);
		} catch (FactoryException | MismatchedDimensionException | TransformException e)
		{
			e.printStackTrace();
		}

	}
	
	public GHPoint transformPoint(GHPoint ghPoint) throws FactoryException, TransformException {
		if(targetCRS.equals(OpenCoordConverter.wgs84CoordRefSystem))
			return ghPoint;
		return doTransformPoint(ghPoint);
	}

	private GHPoint doTransformPoint( GHPoint ghPoint ) throws FactoryException, TransformException
    {
	    GHPoint transformedPoint;
	    LatLong transformedCoordinate = OpenCoordConverter
	            .transformFromSourceCRSToTargetCRS(OpenCoordConverter.wgs84CoordRefSystem,
	                    targetCRS, ghPoint.getLat(), ghPoint.getLon(), true);
	    if (ghPoint instanceof GHPoint3D) {
	    	GHPoint3D threedPoint = (GHPoint3D) ghPoint;
	    	transformedPoint = new GHPoint3D(transformedCoordinate.getLatAngle(),
	    	        transformedCoordinate.getLongAngle(), threedPoint.getElevation());
	    }
	    else
	    	transformedPoint = new GHPoint(transformedCoordinate.getLatAngle(),
	    	        transformedCoordinate.getLongAngle());
	    return transformedPoint;
    }
}
