package com.graphhopper.reader.osgb;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.Way;
import com.graphhopper.routing.util.OsFlagUtils;

/**
 * Created by sadam on 13/02/15.
 */
public abstract class AbstractOsToOsmAttibuteMappingVisitor implements OsToOsmAttributeMappingVisitor {
    protected String visitorName = this.getClass().getSimpleName().toLowerCase();

    @Override
    public boolean visitWayAttribute(String attributeValue, Way way) {
        if (visitorName.equals(attributeValue)) {
            applyAttributes(way);
            return true;
        }
        return false;
    }

    public abstract void applyAttributes(RoutingElement way);

    protected void setOrAppendTag(RoutingElement routingElement, String key, String value) {
        OsFlagUtils.setOrAppendTag(routingElement, key, value);
    }
}
