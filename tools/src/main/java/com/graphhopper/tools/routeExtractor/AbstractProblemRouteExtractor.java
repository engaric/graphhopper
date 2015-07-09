package com.graphhopper.tools.routeExtractor;

import gnu.trove.TLongCollection;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
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
import com.graphhopper.reader.osgb.itn.OsItnInputFile;
import com.graphhopper.util.Helper;

abstract public class AbstractProblemRouteExtractor {
	private final static Logger LOGGER = Logger.getLogger(AbstractProblemRouteExtractor.class.getName()); 
    protected String workingStore;
    protected TLongSet testNodeSet = new TLongHashSet(30);
    protected TLongCollection fullWayList = new TLongArrayList(100);
    protected TLongCollection fullNodeList = new TLongArrayList(200);
    protected final TLongCollection otherEndOfWayNodeList = new TLongArrayList(200);
    protected final TLongCollection roadFidList = new TLongHashSet(200);
    protected Set<String> notHighwaySet = new HashSet<String>();
    protected TLongCollection origFullNodeList;
    protected TLongCollection origFullWayList;
    protected TLongProcedure nodeOutput;
    protected TLongProcedure wayOutput;
    protected TLongArrayList relationList;
    protected TLongProcedure relOutput;
    protected PrintWriter outputWriter;
    
    protected abstract class WayNodeProcess implements TLongProcedure {
        protected final long end;
        protected final RoutingElement item;
        protected final long start;

        public WayNodeProcess(final long end, final RoutingElement item, final long start) {
            this.end = end;
            this.item = item;
            this.start = start;
        }
    }

    public abstract class ProcessVisitor<T> {
        abstract void processVisitor(T element) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerConfigurationException, TransformerException, XPathExpressionException, MismatchedDimensionException, FactoryException, TransformException;
    }

    protected abstract class ProcessFileVisitor<T> extends ProcessVisitor<File> {
        protected ProcessVisitor<T> innerProcess;

        void setInnerProcess(final ProcessVisitor<T> process) {
            innerProcess = process;
        }
    }
    
    public AbstractProblemRouteExtractor(String fileOrDirName) {
        workingStore = fileOrDirName;
    }
    abstract public void process(final String outputFileName) throws TransformerException, ParserConfigurationException, SAXException, XPathExpressionException, XMLStreamException, IOException, MismatchedDimensionException, FactoryException, TransformException;

    protected  ProcessFileVisitor<RoutingElement> fileProcessProcessor = new ProcessFileVisitor<RoutingElement>() {

        @Override
        void processVisitor(final File file) throws XMLStreamException, IOException, TransformerConfigurationException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException, MismatchedDimensionException, FactoryException, TransformException {
            OsItnInputFile in = null;
            try {
                in = new OsItnInputFile(file);
                in.setAbstractFactory(new OsItnUnfilteredRoutingElementFactory());
                
                in.setWorkerThreads(1).open();
                RoutingElement item;
                while ((item = in.getNext()) != null) {
                    innerProcess.processVisitor(item);
                }
            } finally {
                Helper.close(in);
            }
        }
    };

    protected final ProcessVisitor<RoutingElement> extractNodeIds = new ProcessVisitor<RoutingElement>() {

        @Override
        void processVisitor(final RoutingElement item) {
            if (item.isType(OSMElement.WAY)) {
                final OSITNWay way = (OSITNWay) item;
                if (item.hasTag("nothighway")) {
                    notHighwaySet.add(item.getTag("nothighway"));
                }
                if (fullWayList.contains(way.getId())) {
                    final TLongList nodes = way.getNodes();
                    final long startNode = nodes.get(0);
                    final long endNode = nodes.get(nodes.size() - 1);
                    fullNodeList.add(startNode);
                    fullNodeList.add(endNode);
                }
            }
        }
    };

    private final ProcessVisitor<RoutingElement> extractWayIdLinkedToNodes = new ProcessVisitor<RoutingElement>() {
        @Override
        void processVisitor(final RoutingElement item) {
            if (item.isType(OSMElement.WAY)) {
                final OSITNWay way = (OSITNWay) item;
                final TLongList nodes = way.getNodes();
                final long start = nodes.get(0);
                final long end = nodes.get(nodes.size() - 1);
                final TLongProcedure addWayIfNodeExists = new WayNodeProcess(end, item, start) {
                    @Override
                    public boolean execute(final long testNode) {
                        if ((testNode == start) || (testNode == end)) {
                            // ADD THE OTHER END IN TO OUR new collection
                            final long otherEnd = testNode == start ? end : start;
                            otherEndOfWayNodeList.add(otherEnd);

                            fullWayList.add(way.getId());

                            return false;
                        }
                        return true;
                    }
                };
                origFullNodeList.forEach(addWayIfNodeExists);
            }
        }
    };

    private final ProcessVisitor<RoutingElement> extractRelationsAtJunctionOfBothRoads = new ProcessVisitor<RoutingElement>() {
        @Override
        void processVisitor(final RoutingElement item) {
            if (item.isType(OSMElement.RELATION)) {
                final Relation rel = (Relation) item;
                //                    final OSITNRelation rel = (OSITNRelation) item;
                final ArrayList<? extends RelationMember> links = rel.getMembers();
                final long start = links.get(0).ref();
                final long end = links.get(links.size() - 1).ref();
                final TLongProcedure addRelIfWayExists = new WayNodeProcess(end, item, start) {
                    @Override
                    public boolean execute(final long testNode) {
                        if ((testNode == start) || (testNode == end)) {
                            // It will be either an OSITNRelation or an OsItnMetaData both of which are OSITNElement
                            relationList.add(((OSITNElement)item).getId());
                            return false;
                        }
                        return true;
                    }
                };
                fullWayList.forEach(addRelIfWayExists);
            }
        }
    };

    protected final ProcessVisitor<File> extractProcessor = new ProcessVisitor<File>() {
        @Override
        void processVisitor(final File element) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException {
            final OsItnInputFile itn = new OsItnInputFile(element);
            try {
                 outputListedFids(populateFidList(), itn.getInputStream());
            }
            finally {
            	itn.close();
            }
        };

        private void outputListedFids(final TLongArrayList fidList, final InputStream bis) throws XMLStreamException, NumberFormatException, IOException {
            boolean output = false;

            final BufferedReader bir = new BufferedReader(new InputStreamReader(bis));
            String lastLine = "";
            while (bir.ready()) {
                final String line = bir.readLine();

                if (output) {
                    outputWriter.println(line);
                    if (isEndBlock(line)) {
                        output = false;
                        outputWriter.flush();
                    }
                }
                if (!output && line.contains("fid='osgb")) {
                    final String idStr = line.substring(line.indexOf("fid='osgb") + 9, line.lastIndexOf('\''));
                    final long checkFid = Long.parseLong(idStr);
                    if (fidList.contains(checkFid)) {
                        output = true;
                        outputWriter.println(lastLine);
                        outputWriter.println(line);
                    }
                }
                lastLine = line;
            }
        }
        
        private TLongArrayList populateFidList() {
        	final TLongArrayList fidList = new TLongArrayList();
            
        	if(null != relationList) {
        		fidList.addAll(relationList);
            }
           
            if(null != fullWayList && !fullWayList.isEmpty()) {
            	LOGGER.info("Output " + fullWayList.size() + " ways ");
            	fidList.addAll(fullWayList);
            }
            
            if(null != origFullNodeList && !origFullNodeList.isEmpty()) {
            	LOGGER.info("Output " + origFullNodeList.size() + " nodes ");
            	fidList.addAll(origFullNodeList);
            }
            
            if(null != otherEndOfWayNodeList && !otherEndOfWayNodeList.isEmpty()) {
            	LOGGER.info("Output " + otherEndOfWayNodeList.size() + " otherEndOfWayNodeList nodes ");
            	fidList.addAll(otherEndOfWayNodeList);
            }
            
            if(null != roadFidList && !roadFidList.isEmpty()) {
	          LOGGER.info("Output " + roadFidList.size() + " roads ");
	          fidList.addAll(roadFidList);
            }

            LOGGER.info("fidList size: " + fidList.size());
            return fidList;
        }

        private boolean isEndBlock(final String curLine) {
            boolean endBlock = false;
            switch (curLine.trim()) {
            case "</osgb:networkMember>":
            case "</osgb:roadInformationMember>":
            case "</osgb:roadMember>": {
                endBlock = true;
                break;
            }
            }
            return endBlock;
        }
    };

    protected void prepareOutputMethods() {
        nodeOutput = new TLongProcedure() {
            @Override
            public boolean execute(final long arg0) {
                LOGGER.info("node:" + arg0);
                return true;
            }
        };

        wayOutput = new TLongProcedure() {
            @Override
            public boolean execute(final long arg0) {
                LOGGER.info("way:" + arg0);
                return true;
            }
        };

        relOutput = new TLongProcedure() {
            @Override
            public boolean execute(final long arg0) {
                LOGGER.info("rel:" + arg0);
                return true;
            }
        };
    }

    protected void findRelationsAtJunctionOfBothRoads(final File itnFile) {
        LOGGER.info("findRelationsAtJunctionOfBothRoads");
        relationList = new TLongArrayList(30);
        fileProcessProcessor.setInnerProcess(extractRelationsAtJunctionOfBothRoads);
        process(itnFile, fileProcessProcessor);
    }

    protected void findWaysLinkedAtJunctionOfBothRoads(final File itnFile) {
        LOGGER.info("findWaysLinkedAtJunctionOfBothRoads");
        fullWayList = new TLongArrayList(30);
        fullNodeList = origFullNodeList;
        fileProcessProcessor.setInnerProcess(extractWayIdLinkedToNodes);
        process(itnFile, fileProcessProcessor);
    }

    protected void findNodesOnBothWays(final File itnFile) {
        LOGGER.info("STAGE FOUR - findNodesOnBothWays");
        fileProcessProcessor.setInnerProcess(extractNodeIds);
        process(itnFile, fileProcessProcessor);
        origFullNodeList.retainAll(fullNodeList);
    }

    protected void process(final File itnFile, final ProcessVisitor<File> processVisitor) {
        try {
            processDirOrFile(itnFile, processVisitor);
        } catch (final Exception ex) {
            throw new RuntimeException("Problem while parsing file", ex);
        }
    }

    protected void processDirOrFile(final File osmFile, final ProcessVisitor<File> processVisitor) throws XMLStreamException, IOException, TransformerConfigurationException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException, MismatchedDimensionException, FactoryException, TransformException {
        if (osmFile.isDirectory()) {
            final String absolutePath = osmFile.getAbsolutePath();
            final String[] list = osmFile.list();
            for (final String file : list) {
                final File nextFile = new File(absolutePath + File.separator + file);
                processDirOrFile(nextFile, processVisitor);
            }
        } else {
            processSingleFile(osmFile, processVisitor);
        }
    }

    private void processSingleFile(final File osmFile, final ProcessVisitor<File> processVisitor) throws XMLStreamException, IOException, TransformerConfigurationException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException, MismatchedDimensionException, FactoryException, TransformException {
        processVisitor.processVisitor(osmFile);
    }

    protected void prepareNameRelation(final Relation relation, final TLongCollection wayList) {
        final ArrayList<? extends RelationMember> members = relation.getMembers();
        for (final RelationMember relationMember : members) {
            wayList.add(relationMember.ref());
        }
    }
}
