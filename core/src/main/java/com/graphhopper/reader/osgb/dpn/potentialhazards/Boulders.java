package com.graphhopper.reader.osgb.dpn.potentialhazards;

import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;

/**
 * Created by sadam on 13/02/15.
 */
public class Boulders extends AbstractOsToOsmAttibuteMappingVisitor {

    @Override
    protected void applyAttributes(Way way) {
        setOrAppendTag(way, "natural", "boulders");
    }

}
