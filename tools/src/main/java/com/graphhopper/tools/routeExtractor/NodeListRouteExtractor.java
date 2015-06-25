package com.graphhopper.tools.routeExtractor;

import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.procedure.TLongProcedure;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import org.xml.sax.SAXException;

import com.graphhopper.reader.OSMElement;
import com.graphhopper.reader.Relation;
import com.graphhopper.reader.RelationMember;
import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.itn.OSITNElement;
import com.graphhopper.reader.osgb.itn.OSITNWay;

public class NodeListRouteExtractor extends AbstractProblemRouteExtractor {
	
	private final static Logger LOGGER = Logger.getLogger(NodeListRouteExtractor.class.getName());

    private String nodeListString;

    protected abstract class NodeIdListProcedure implements TLongProcedure {
        protected final TLongList nodeIds;
        protected final RoutingElement item;

        public NodeIdListProcedure(final TLongList nodeIds, final RoutingElement item) {
            this.nodeIds = nodeIds;
            this.item = item;
        }
    }

    protected abstract class RoutingElementProcedure implements TLongProcedure {
        protected final RoutingElement item;

        public RoutingElementProcedure(final RoutingElement item) {
            this.item = item;
        }
    }

    private final ProcessVisitor<RoutingElement> extractWayIds = new ProcessVisitor<RoutingElement>() {
        @Override
        void processVisitor(final RoutingElement someitem) {

            final TLongProcedure addWayIfNodeExists = new RoutingElementProcedure(someitem) {
                @Override
                public boolean execute(final long testNode) {
                    if (item.isType(OSMElement.WAY)) {
                        final OSITNWay way = (OSITNWay) item;
                        if (way.getNodes().contains(testNode)) {
                            LOGGER.info("\tWay found joining one of our nodes. Id: " + way.getId());
                            // Add the way to our list of ways
                            fullWayList.add(way.getId());

                            return false;
                        }
                    }
                    return true;
                }
            };
            origFullNodeList.forEach(addWayIfNodeExists);

        }
    };
    private final ProcessVisitor<RoutingElement> extractRoadLinksProcessVisitor = new ProcessVisitor<RoutingElement>() {
        @Override
        void processVisitor(final RoutingElement someitem) {

            final TLongProcedure addWayIfNodeExists = new RoutingElementProcedure(someitem) {
                @Override
                public boolean execute(final long testWayId) {
                    if (item.isType(OSMElement.RELATION)) {
                        final Relation relation = (Relation) item;

                        ArrayList<? extends RelationMember> members = relation.getMembers();
                        for (RelationMember relationMember : members) {
                            if (relationMember.ref() == testWayId) {
                                // It will be either an OSITNRelation or an OsItnMetaData both of which are OSITNElement
                                roadFidList.add(((OSITNElement)item).getId());
                                return false;
                            }
                        }
                    }
                    return true;
                }
            };
            fullWayList.forEach(addWayIfNodeExists);

        }
    };

    public NodeListRouteExtractor(String fileOrDirName, String nodeListString) {
        super(fileOrDirName);
        this.nodeListString = nodeListString;
    }

    @Override
    public void process(String outputFileName) throws TransformerException, ParserConfigurationException, SAXException, XPathExpressionException, XMLStreamException, IOException, MismatchedDimensionException, FactoryException, TransformException {
        prepareOutputMethods();

        final File itnFile = new File(workingStore);
        addRoadNodes();
        findWaysLinkedAtJunctionOfBothRoads(itnFile);
        findRelationsAtJunctionOfBothRoads(itnFile);
        findLinkedWayIDs(itnFile);
        findRoadLinksForWays(itnFile);
        findNodesOnBothWays(itnFile);
        origFullNodeList.forEach(nodeOutput);
        fullWayList.forEach(wayOutput);
        relationList.forEach(relOutput);

        outputWriter = new PrintWriter(outputFileName);

        outputWriter.println("<?xml version='1.0' encoding='UTF-8'?>" + "<osgb:FeatureCollection " + "xmlns:osgb='http://www.ordnancesurvey.co.uk/xml/namespaces/osgb' " + "xmlns:gml='http://www.opengis.net/gml' " + "xmlns:xlink='http://www.w3.org/1999/xlink' " + "xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' " + "xsi:schemaLocation='http://www.ordnancesurvey.co.uk/xml/namespaces/osgb http://www.ordnancesurvey.co.uk/xml/schema/v7/OSDNFFeatures.xsd' " + "fid='GDS-58096-1'>"
                + "<gml:description>Ordnance Survey, (c) Crown Copyright. All rights reserved, 2009-07-30</gml:description>" + "<gml:boundedBy><gml:null>unknown</gml:null></gml:boundedBy>" + "<osgb:queryTime>2009-07-30T02:01:07</osgb:queryTime>" + "<osgb:queryExtent>" + "<osgb:Rectangle srsName='osgb:BNG'>" + "<gml:coordinates>291000.000,92000.000 293000.000,94000.000</gml:coordinates>" + "</osgb:Rectangle>" + "</osgb:queryExtent>");
        outputWriter.flush();
        processDirOrFile(itnFile, extractProcessor);
        outputWriter.println("<osgb:boundedBy>" + "<gml:Box srsName='osgb:BNG'>" + "<gml:coordinates>290822.000,91912.000 293199.000,94222.000</gml:coordinates>" + "</gml:Box>" + "</osgb:boundedBy>" + "</osgb:FeatureCollection>");
        outputWriter.flush();
        outputWriter.close();
    }

    private void addRoadNodes() {
        LOGGER.info("Add road nodes");
        String[] nodeStrings = nodeListString.split(",");
        origFullNodeList = new TLongArrayList(nodeStrings.length);
        for (int i = 0; i < nodeStrings.length; i++) {
            origFullNodeList.add(Long.parseLong(nodeStrings[i]));
        }
    }

    private void findWaysOnRoad(final File itnFile) {
        LOGGER.info("STAGE ONE - findWaysOnRoad");
        fileProcessProcessor.setInnerProcess(extractWayIds);
        process(itnFile, fileProcessProcessor);
    }
    private void findRoadLinksForWays(final File itnFile) {
        LOGGER.info("STAGE TWOish - findRoadLinksForWays");
        fileProcessProcessor.setInnerProcess(extractRoadLinksProcessVisitor);
        process(itnFile, fileProcessProcessor);
    }

    private void findLinkedWayIDs(final File itnFile) {
        LOGGER.info("STAGE THREE - findLinkedWayIDs");
        origFullNodeList = fullNodeList;
        origFullWayList = fullWayList;
        fullNodeList = new TLongArrayList(200);
        fullWayList = new TLongArrayList(100);
        findWaysOnRoad(itnFile);
    }
}
