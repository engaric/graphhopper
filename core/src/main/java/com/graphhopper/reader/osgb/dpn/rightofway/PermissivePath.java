package com.graphhopper.reader.osgb.dpn.rightofway;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Description: A route where the landowner has permitted travel on foot. This right may be withdrawn by the landowner.
 *
 * Confirmed Allowable users: Pedestrians
 *
 * Created by sadam on 13/02/15.
 */
public class PermissivePath extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    protected void applyAttributes(Way way) {
        way.setTag("foot", "permissive");
    }

}
