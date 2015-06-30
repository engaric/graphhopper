package com.graphhopper.reader.osgb.dpn.rightofway;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: A route that is the responsibility of local highway authorities and maintained at public expense.
 * All ORPAs have rights for pedestrians. Beyond that, any particular ORPA may, or may not, have rights for cyclists and equestrians,
 * and may or may not have rights for motor vehicles. Other Routes with Public Access (ORPA) are sometimes known as unclassified
 * unsurfaced roads (or unclassified country roads).  Given we cannot tell if horse or bicycle is allowed we will have to say no. 
 *
 * Confirmed Allowable users: Pedestrians *
 *
 * * Other rights may exist; these will need to be determined from the local Highway Authority
 *
 * Created by sadam on 16/02/15.
 */
public class OtherRouteWithPublicAccess extends AbstractOsToOsmAttibuteMappingVisitor {
    @Override
    public void applyAttributes(RoutingElement way)
    {
        way.setTag("foot", "yes");
        way.setTag("bicycle", "no");
        way.setTag("horse", "no");
    }
}
