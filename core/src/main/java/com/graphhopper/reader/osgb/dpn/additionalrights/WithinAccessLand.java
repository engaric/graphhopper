package com.graphhopper.reader.osgb.dpn.additionalrights;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 *
 * Description: A link within Access Land
 *
 * Confirmed Allowable users: Pedestrians Note for Private Roads where the only
 * right to use is because the road is in Access Land there may not be a right
 * to use the road itself.
 *
 * @author phopkins
 *
 */
public class WithinAccessLand extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way) {
        way.setTag("foot", "yes");
    }

}
