package com.graphhopper.routing.util;

import com.graphhopper.reader.RoutingElement;

/**
 * Utility class to contain more complex flag and tag operations
 *
 * @author phopkins
 *
 */
public class OsFlagUtils {
    public static boolean hasTag(RoutingElement routingElement, String key, String value) {
        String wayTag = routingElement.getTag(key);
        if (null != wayTag) {
            String[] values = wayTag.split(",");
            for (String tvalue : values) {
                if (tvalue.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void setOrAppendTag(RoutingElement routingElement, String key, String value) {
        String currentValue = routingElement.getTag(key);
        if (currentValue != null) {
        	routingElement.setTag(key, currentValue + "," + value);
        } else {
            // This is the first time we are adding it so just add it
        	routingElement.setTag(key, value);
        }
    }

}
