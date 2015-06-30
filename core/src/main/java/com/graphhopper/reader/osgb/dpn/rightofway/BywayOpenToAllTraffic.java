package com.graphhopper.reader.osgb.dpn.rightofway;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: A highway open to all traffic
 *
 * Confirmed Allowable users: Pedestrians, Horses, Cyclists, Motorised Vehicles
 *
 * Created by sadam on 13/02/15.
 */
public class BywayOpenToAllTraffic extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way) {
        way.setTag("designation", "byway_open_to_all_traffic");
        way.setTag("foot", "yes");
        way.setTag("horse", "yes");
        way.setTag("bicycle", "yes");
        way.setTag("motor_vehicle", "yes");
    }

}
