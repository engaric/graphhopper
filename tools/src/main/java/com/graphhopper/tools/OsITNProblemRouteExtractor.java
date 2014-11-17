package com.graphhopper.tools;

import gnu.trove.TLongCollection;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.procedure.TLongProcedure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.graphhopper.reader.OSMElement;
import com.graphhopper.reader.Relation;
import com.graphhopper.reader.RelationMember;
import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.Way;
import com.graphhopper.reader.osgb.OsItnInputFile;
import com.graphhopper.util.CmdArgs;
import com.graphhopper.util.Helper;

/**
 * This tool is designed to help extract the xml can contributes to a know route
 * with problems argument is the named road for which you wish to extract all
 * referenced nodes and ways. Initial implementation will just extract the
 * directly referenced nodes and ways. A later version should probably also
 * extract all first order connections.
 * 
 * @author stuartadam
 * 
 */
public class OsITNProblemRouteExtractor {
	OsItnInputFile file;
	private String workingStore;
	private TLongCollection fullWayList = new TLongArrayList(100);
	private TLongCollection fullNodeList = new TLongArrayList(200);
	private String workingRoadName;
	protected Set<String> notHighwaySet = new HashSet<String>();

	private abstract class WayNodeProcess implements TLongProcedure {
		protected final long end;
		protected final RoutingElement item;
		protected final long start;

		private WayNodeProcess(long end, RoutingElement item, long start) {
			this.end = end;
			this.item = item;
			this.start = start;
		}

	}

	private abstract class ProcessVisitor<T> {
		abstract void processVisitor(T element) throws XMLStreamException,
				IOException, ParserConfigurationException, SAXException,
				TransformerConfigurationException, TransformerException, XPathExpressionException;
	}

	private abstract class ProcessFileVisitor<T> extends ProcessVisitor<File> {
		protected ProcessVisitor<T> innerProcess;

		void setInnerProcess(ProcessVisitor<T> process) {
			innerProcess = process;
		}
	}

	private ProcessFileVisitor<RoutingElement> fileProcessProcessor = new ProcessFileVisitor<RoutingElement>() {

		@Override
		void processVisitor(File file) throws XMLStreamException, IOException,
				TransformerConfigurationException,
				ParserConfigurationException, SAXException,
				TransformerException, XPathExpressionException {
			OsItnInputFile in = null;
			try {
				in = new OsItnInputFile(file).setWorkerThreads(1).open();
				RoutingElement item;
				while ((item = in.getNext()) != null) {
					innerProcess.processVisitor(item);
				}
			} finally {
				Helper.close(in);
			}
		}
	};

	private ProcessVisitor<RoutingElement> extractWayIds = new ProcessVisitor<RoutingElement>() {
		@Override
		void processVisitor(RoutingElement item) {
			if (item.isType(OSMElement.WAY)) {
				final Way way = (Way) item;
				if (way.hasTag("name", workingRoadName)) {
					fullWayList.add(way.getId());
				}
			}
			if (item.isType(OSMElement.RELATION)) {
				final Relation relation = (Relation) item;
				// if (!relation.isMetaRelation()
				// && relation.hasTag(OSITNElement.TAG_KEY_TYPE, "route"))
				// prepareWaysWithRelationInfo(relation);

				if (relation.hasTag("name", workingRoadName)) {
					prepareNameRelation(relation, fullWayList);
				}
			}
		}
	};

	private ProcessVisitor<RoutingElement> extractNodeIds = new ProcessVisitor<RoutingElement>() {

		@Override
		void processVisitor(RoutingElement item) {
			if (item.isType(OSMElement.WAY)) {
				final Way way = (Way) item;
				if (item.hasTag("nothighway")) {
					notHighwaySet.add(item.getTag("nothighway"));
				}
				if (fullWayList.contains(way.getId())) {
					TLongList nodes = way.getNodes();
					long startNode = nodes.get(0);
					long endNode = nodes.get(nodes.size() - 1);
					fullNodeList.add(startNode);
					fullNodeList.add(endNode);
				}
			}
		}
	};

	private ProcessVisitor<RoutingElement> extractWayIdLinkedToNodes = new ProcessVisitor<RoutingElement>() {
		@Override
		void processVisitor(final RoutingElement item) {
			if (item.isType(OSMElement.WAY)) {
				final Way way = (Way) item;
				TLongList nodes = way.getNodes();
				final long start = nodes.get(0);
				final long end = nodes.get(nodes.size() - 1);
				TLongProcedure addWayIfNodeExists = new WayNodeProcess(end,
						item, start) {
					@Override
					public boolean execute(long testNode) {
						if (testNode == start || testNode == end) {
							fullWayList.add(item.getId());
							return false;
						}
						return true;
					}
				};
				origFullNodeList.forEach(addWayIfNodeExists);
			}
		}
	};

	private ProcessVisitor<RoutingElement> extractRelationsAtJunctionOfBothRoads = new ProcessVisitor<RoutingElement>() {
		@Override
		void processVisitor(final RoutingElement item) {
			if (item.isType(OSMElement.RELATION)) {
				final Relation rel = (Relation) item;
				ArrayList<? extends RelationMember> links = rel.getMembers();
				final long start = links.get(0).ref();
				final long end = links.get(links.size() - 1).ref();
				TLongProcedure addRelIfWayExists = new WayNodeProcess(end,
						item, start) {
					@Override
					public boolean execute(long testNode) {
						if (testNode == start || testNode == end) {
							relationList.add(rel.getId());
							return false;
						}
						return true;
					}
				};
				fullWayList.forEach(addRelIfWayExists);
			}
		}
	};

	private ProcessVisitor<File> extractProcessor = new ProcessVisitor<File>() {
		void processVisitor(File element) throws XMLStreamException,
				IOException, ParserConfigurationException, SAXException,
				TransformerException, XPathExpressionException {
			OsItnInputFile itn = new OsItnInputFile(element);
			InputStream bis = itn.getInputStream();
			TLongArrayList fidList = new TLongArrayList(relationList);
			fidList.addAll(fullWayList);
			fidList.addAll(origFullNodeList);
			outputListedFids(fidList, bis);
		};
		
		private void outputListedFids(TLongArrayList fidList, InputStream bis)
				throws XMLStreamException, NumberFormatException, IOException {
			boolean output = false;

			BufferedReader bir = new BufferedReader(new InputStreamReader(bis));
			while (bir.ready()) {
				String line = bir.readLine();
				if (output) {
					System.out.println(line);
					if (isEndBlock(line)) {
						output = false;
					}
				}
				if (!output && line.contains("fid=osgb")) {
					String idStr = line.substring(line.indexOf("fid=osgb") + 9,
							line.lastIndexOf('\''));
					System.err.println("ID:" + idStr);
					long checkFid = Long.parseLong(idStr);
					if (fidList.contains(checkFid)) {
						output = true;
						System.out.println(line);
					}
				} 
			}
		}

		private boolean isEndBlock(String curLine) {
			boolean endBlock = false;
			switch (curLine) {
			case "</osgb:RoadNode>":
			case "</osgb:RouteNode>":
			case "</osgb:RoadLink>":
			case "</osgb:RouteLink>":
			case "</osgb:RoadRouteInformation>":
			case "</osgb:Road>":
			case "</osgb:RoadLinkInformation>":
			case "</osgb:RoadNodeInformation>": {
				endBlock = true;
				break;
			}
			}
			return endBlock;
		}
	};

	private String workingLinkRoad;
	private TLongCollection origFullNodeList;
	private TLongCollection origFullWayList;
	private TLongProcedure nodeOutput;
	private TLongProcedure wayOutput;
	private TLongArrayList relationList;
	private TLongProcedure relOutput;

	public static void main(String[] strs) throws Exception {
		CmdArgs args = CmdArgs.read(strs);
		String fileOrDirName = args.get("osmreader.osm", null);
		String namedRoad = args.get("roadName", null);
		String namedLinkRoad = args.get("linkRoadName", null);
		OsITNProblemRouteExtractor extractor = new OsITNProblemRouteExtractor(
				fileOrDirName, namedRoad, namedLinkRoad);
		extractor.process();
	}

	public OsITNProblemRouteExtractor(String fileOrDirName, String namedRoad,
			String namedLinkRoad) {
		workingStore = fileOrDirName;
		workingRoadName = namedRoad;
		workingLinkRoad = namedLinkRoad;
	}

	private void process() throws TransformerException, ParserConfigurationException, SAXException, XPathExpressionException, XMLStreamException, IOException {
		prepareOutputMethods();

		File itnFile = new File(workingStore);
		findWaysOnRoad(itnFile);
		findNodesOfRoad(itnFile);

		if (null != workingLinkRoad) {
			findLinkedWayIDs(itnFile);
			findNodesOnBothWays(itnFile);
			origFullNodeList.forEach(nodeOutput);
			findWaysLinkedAtJunctionOfBothRoads(itnFile);
			fullWayList.forEach(wayOutput);
			findRelationsAtJunctionOfBothRoads(itnFile);
			relationList.forEach(relOutput);
		} else {
			fullNodeList.forEach(nodeOutput);
			fullWayList.forEach(wayOutput);
		}
		
		processDirOrFile(itnFile, extractProcessor);
	}

	private void prepareOutputMethods() {
		nodeOutput = new TLongProcedure() {
			@Override
			public boolean execute(long arg0) {
				System.err.println("node:" + arg0);
				return true;
			}
		};

		wayOutput = new TLongProcedure() {
			@Override
			public boolean execute(long arg0) {
				System.err.println("way:" + arg0);
				return true;
			}
		};

		relOutput = new TLongProcedure() {
			@Override
			public boolean execute(long arg0) {
				System.err.println("rel:" + arg0);
				return true;
			}
		};
	}

	private void findNodesOfRoad(File itnFile) {
		System.err.println("STAGE TWO");
		fileProcessProcessor.setInnerProcess(extractNodeIds);
		process(itnFile, fileProcessProcessor);
	}

	private void findWaysOnRoad(File itnFile) {
		System.err.println("STAGE ONE");
		fileProcessProcessor.setInnerProcess(extractWayIds);
		process(itnFile, fileProcessProcessor);
	}

	private void findRelationsAtJunctionOfBothRoads(File itnFile) {
		relationList = new TLongArrayList(30);
		fileProcessProcessor
				.setInnerProcess(extractRelationsAtJunctionOfBothRoads);
		process(itnFile, fileProcessProcessor);
	}

	private void findWaysLinkedAtJunctionOfBothRoads(File itnFile) {
		fullWayList = new TLongArrayList(30);
		fullNodeList = origFullNodeList;
		fileProcessProcessor.setInnerProcess(extractWayIdLinkedToNodes);
		process(itnFile, fileProcessProcessor);
	}

	private void findNodesOnBothWays(File itnFile) {
		System.err.println("STAGE FOUR");
		fileProcessProcessor.setInnerProcess(extractNodeIds);
		process(itnFile, fileProcessProcessor);
		origFullNodeList.retainAll(fullNodeList);
	}

	private void findLinkedWayIDs(File itnFile) {
		origFullNodeList = fullNodeList;
		origFullWayList = fullWayList;
		fullNodeList = new TLongArrayList(200);
		fullWayList = new TLongArrayList(100);
		workingRoadName = workingLinkRoad;
		System.err.println("STAGE THREE");
		findWaysOnRoad(itnFile);
	}

	void process(File itnFile, ProcessVisitor<File> processVisitor) {
		try {
			processDirOrFile(itnFile, processVisitor);
		} catch (Exception ex) {
			throw new RuntimeException("Problem while parsing file", ex);
		}
	}

	private void processDirOrFile(File osmFile,
			ProcessVisitor<File> processVisitor) throws XMLStreamException,
			IOException, TransformerConfigurationException,
			ParserConfigurationException, SAXException, TransformerException, XPathExpressionException {
		if (osmFile.isDirectory()) {
			String absolutePath = osmFile.getAbsolutePath();
			String[] list = osmFile.list();
			for (String file : list) {
				File nextFile = new File(absolutePath + File.separator + file);
				processDirOrFile(nextFile, processVisitor);
			}
		} else {
			processSingleFile(osmFile, processVisitor);
		}
	}

	private void processSingleFile(File osmFile,
			ProcessVisitor<File> processVisitor) throws XMLStreamException,
			IOException, TransformerConfigurationException,
			ParserConfigurationException, SAXException, TransformerException, XPathExpressionException {
		processVisitor.processVisitor(osmFile);
	}

	private void prepareNameRelation(Relation relation, TLongCollection wayList) {
		ArrayList<? extends RelationMember> members = relation.getMembers();
		for (RelationMember relationMember : members) {
			wayList.add(relationMember.ref());
		}
	}

	private void prepareWaysWithRelationInfo(Relation relation) {
		// TODO Auto-generated method stub

	}
}
