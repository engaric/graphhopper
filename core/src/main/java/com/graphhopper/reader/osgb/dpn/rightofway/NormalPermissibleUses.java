package com.graphhopper.reader.osgb.dpn.rightofway;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: No known restrictions on pedestrians, cyclists or horses being ridden. Restrictions may apply to vehicles.
 *
 * Confirmed Allowable users: Pedestrians, Horses, Cyclists – used for public roads without a restriction
 *
 * @author phopkins
 *
 */
public class NormalPermissibleUses  extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    public void applyAttributes(RoutingElement way) {
        way.setTag("foot", "yes");
        way.setTag("horse", "yes");
        way.setTag("bicycle", "yes");
    }

}
