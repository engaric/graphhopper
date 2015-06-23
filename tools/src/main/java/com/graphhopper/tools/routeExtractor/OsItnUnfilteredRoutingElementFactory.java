package com.graphhopper.tools.routeExtractor;

import java.math.BigDecimal;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.reader.osgb.AbstractRoutingElementFactory;
import com.graphhopper.reader.osgb.itn.OSITNElement;
import com.graphhopper.reader.osgb.itn.OSITNNode;
import com.graphhopper.reader.osgb.itn.OSITNRelation;
import com.graphhopper.reader.osgb.itn.OSITNWay;
import com.graphhopper.reader.osgb.itn.OsItnMetaData;

/**
 * Used by the Problem Route extractor which does not require to filter the routing element parsing.
 * 
 * This unifies the case statements available in @see{OsItnPreProcessRoutingElementFactory} and @see{OsItnProcessStageOneRoutingElementFactory}
 *
 * @author mbrett
 *
 */
public class OsItnUnfilteredRoutingElementFactory extends AbstractRoutingElementFactory<OSITNElement>{


	    private static final Logger logger = LoggerFactory.getLogger(OsItnUnfilteredRoutingElementFactory.class);

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
	        case "RoadNode": {
	            return OSITNNode.create(id, parser);
	        }
	        case "RoadLink": {
	            return OSITNWay.create(id, parser);
	        }
	        case "RoadLinkInformation":
	        case "RoadRouteInformation": {
	            return OSITNRelation.create(id, parser);
	        }
	        case "Road": {
	            return OsItnMetaData.create(id, parser);
	        }
	        }
	        return null;
	    }
	    
	}
	
