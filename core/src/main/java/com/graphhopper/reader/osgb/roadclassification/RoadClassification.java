package com.graphhopper.reader.osgb.roadclassification;

import com.graphhopper.reader.RoutingElement;
import com.graphhopper.reader.osgb.AbstractOsToOsmAttibuteMappingVisitor;


public enum RoadClassification 
{
	NULLCLASSIFICATION(null) {
		@Override
		public void applyWayAttribute( RoutingElement way )
		{
			//nullop
		}
	},
	ALLEY(new Alley()),
	AROAD(new ARoad()),
	BROAD(new BRoad()),
	DUALCARRIAGEWAY(new DualCarriageway()),
	LOCALSTREET(new LocalStreet()),
	MINORROAD(new MinorRoad()),
	MOTORWAY(new Motorway()),
	PEDESTRIANISEDSTREET(new PedestrianisedStreet()),
	PRIVATEROADPUBLICLYACCESSIBLE(new PrivateRoadPubliclyAccessible()),
	PRIVATEROADRESTRICTEDACCESS(new PrivateRoadRestrictedAccess()),
	SINGLECARRIAGEWAY(new SingleCarriageway()),
	URBAN(new Urban()),
	ROUNDABOUT(new Roundabout()),
	SLIPROAD(new SlipRoad());
	
	private final AbstractOsToOsmAttibuteMappingVisitor innerVisitor;
	

	RoadClassification(AbstractOsToOsmAttibuteMappingVisitor innerVisitor) {
		this.innerVisitor = innerVisitor;
	}

    public void applyWayAttribute(RoutingElement way )
    {
	    innerVisitor.applyAttributes(way);
    }
    
    /**
     * 
     * For usages with a 1:1 mapping between attribute and value then have a quick lookup rather than using the visitor pattern and iterating over all visitors
     */    
    public static RoadClassification lookup(String dataName) {
    	try {
    		RoadClassification valueOf = RoadClassification.valueOf(dataName.replace(" ", "").replace("-", "").toUpperCase());
    		return valueOf;
    	}
    	catch (IllegalArgumentException | NullPointerException e) {
    		return NULLCLASSIFICATION;
    	}
    }
	
}
