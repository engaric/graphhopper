/*
 *  Licensed to GraphHopper and Peter Karich under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.util.shapes;

import java.util.HashMap;
import java.util.Map;

import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import uk.co.ordnancesurvey.api.srs.LatLong;
import uk.co.ordnancesurvey.api.srs.OpenCoordConverter;

import com.graphhopper.util.NumHelper;

/**
 * @author Peter Karich
 */
public class GHPoint
{
    private static final String BNG = "BNG";
    private static final String WGS_84 = "WGS84";
    private static Map<String, String> srsInputMap;
    public double lat = Double.NaN;
    public double lon = Double.NaN;

    static
    {
        srsInputMap = new HashMap<String, String>();
        srsInputMap.put(WGS_84, "EPSG:4326");
        srsInputMap.put(BNG, "EPSG:27700");
    }

    public GHPoint() {
    }

    public GHPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLon()
    {
        return lon;
    }

    public double getLat()
    {
        return lat;
    }

    public boolean isValid()
    {
        return !Double.isNaN(lat) && !Double.isNaN(lon);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 83
                * hash
                + (int) (Double.doubleToLongBits(this.lat) ^ (Double
                        .doubleToLongBits(this.lat) >>> 32));
        hash = 83
                * hash
                + (int) (Double.doubleToLongBits(this.lon) ^ (Double
                        .doubleToLongBits(this.lon) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        @SuppressWarnings("unchecked")
        final GHPoint other = (GHPoint) obj;
        return NumHelper.equalsEps(lat, other.lat)
                && NumHelper.equalsEps(lon, other.lon);
    }

    @Override
    public String toString()
    {
        return lat + "," + lon;
    }

    /**
     * Attention: geoJson is LON,LAT
     */
    public Double[] toGeoJson()
    {
        return new Double[] { lon, lat };
    }

    public static GHPoint parse(String str)
    {
        // if the point is in the format of lat,lon we don't need to call
        // geocoding service
        String[] fromStrs = str.split(",");
        if (fromStrs.length == 2)
        {
            try
            {
                double fromLat = Double.parseDouble(fromStrs[0]);
                double fromLon = Double.parseDouble(fromStrs[1]);
                return new GHPoint(fromLat, fromLon);
            } catch (NumberFormatException | NullPointerException ex)
            {
                // Fall through to return a null below
            }
        }
        return null;
    }

    /**
     * 
     * @param string
     * @param srs
     *            default is WGS_84 to match the non
     * @return
     */
    public static GHPoint parse(String str, String srs)
    {
        String[] fromStrs = str.split(",");
        if (fromStrs.length == 2)
        {
            try
            {
                double sourceXCoordinate = Double.parseDouble(fromStrs[0]);
                double sourceYCoordinate = Double.parseDouble(fromStrs[1]);
                CoordinateReferenceSystem outputCRS = OpenCoordConverter.wgs84CoordRefSystem;
                String casedSrs = srs.toUpperCase();
                String mappedInputSRS = srsInputMap.containsKey(casedSrs) ? srsInputMap
                        .get(casedSrs) : srs;
                CoordinateReferenceSystem inputCRS = mappedInputSRS
                        .equalsIgnoreCase(OpenCoordConverter.BNG_CRS_CODE) ? OpenCoordConverter.bngCoordRefSystem
                        : CRS.decode(mappedInputSRS);
                LatLong transformFromSourceCRSToTargetCRS = OpenCoordConverter
                        .transformFromSourceCRSToTargetCRS(inputCRS, outputCRS,
                                sourceXCoordinate, sourceYCoordinate, true);
                return new GHPoint(
                        transformFromSourceCRSToTargetCRS.getLatAngle(),
                        transformFromSourceCRSToTargetCRS.getLongAngle());
            } catch (TransformException | IllegalArgumentException e)
            {
                // Fall through to return a null below
            } catch (FactoryException e)
            {
                throw new IllegalArgumentException("Srs " + srs
                        + " is not a valid srs for input.");
            }
        }
        return null;
    }
}
