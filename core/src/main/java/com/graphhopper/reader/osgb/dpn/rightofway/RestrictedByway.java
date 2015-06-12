package com.graphhopper.reader.osgb.dpn.rightofway;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: A route open to all traffic except mechanically propelled vehicles. Formerly known as Road Used as Public Path (RUPP).
 *
 * Confirmed Allowable users: Pedestrians, Horses, Cyclists
 *
 * Created by sadam on 13/02/15.
 */
public class RestrictedByway extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way)
    {
        way.setTag("designation", "restricted_byway");
        way.setTag("highway", "track");
        way.setTag("motor_vehicle", "no");
        way.setTag("foot", "yes");
        way.setTag("horse", "yes");
        way.setTag("bicycle", "yes");    }

}
