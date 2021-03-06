package com.graphhopper.reader.osgb.itn;

import java.math.BigDecimal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.reader.osgb.AbstractRoutingElementFactory;

/**
 * Stage two only needs Ways
 *
 * @author phopkins
 *
 */
public class OsItnProcessStageTwoRoutingElementFactory extends AbstractRoutingElementFactory<OSITNElement>{

    private static final Logger logger = LoggerFactory.getLogger(OsItnProcessStageTwoRoutingElementFactory.class);

    @Override
    public OSITNElement create(String name, String idStr, XMLStreamReader parser) throws MismatchedDimensionException, XMLStreamException, FactoryException, TransformException {
        // Strip of the osgb prefix
        idStr = idStr.substring(4);
        logger.info(idStr + ":" + name + ":");

        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException nfe) {
            BigDecimal bd = new BigDecimal(idStr);
            id = bd.longValue();
        }
        logger.info(id + ":" + name + ":");
        switch (name) {
        case "RoadLink": {
            return OSITNWay.create(id, parser);
        }
        }
        return null;
    }
}
