package com.graphhopper.reader.osgb.dpn.rightofway;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: A route where there is a right to travel on foot.
 *
 * Confirmed Allowable users: Pedestrians
 *
 * Created by sadam on 13/02/15.
 */
public class Footpath extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way)
    {
        way.setTag("designation", "public_footpath");
        way.setTag("highway", "footway");
        way.setTag("foot", "yes");
    }

}
