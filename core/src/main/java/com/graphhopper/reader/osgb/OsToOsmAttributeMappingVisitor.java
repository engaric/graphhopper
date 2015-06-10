package com.graphhopper.reader.osgb;

import com.graphhopper.reader.Way;

/**
 * Created by sadam on 13/02/15.
 */
public interface OsToOsmAttributeMappingVisitor {
    /**
     *
     * @param attributeValue
     * @param way
     * @return true if this visitor handled the attribute
     */
    boolean visitWayAttribute(String attributeValue, Way way);
}
