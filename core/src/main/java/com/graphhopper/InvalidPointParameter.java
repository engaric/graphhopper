package com.graphhopper;

import com.graphhopper.util.shapes.GHPoint;

public class InvalidPointParameter extends IllegalArgumentException
{
	private String prefix = "Cannot find point ";
	private String separator = ": ";
	private int placeIndex;
	private GHPoint point=null;
	private String pointStr = null;

	public InvalidPointParameter( int placeIndex, GHPoint point )
	{
		this.placeIndex = placeIndex;
		this.point = point;
	}
	
	public InvalidPointParameter( int placeIndex, String pointStr )
	{
		this.placeIndex = placeIndex;
		this.pointStr  = pointStr;
	}

	@Override
	public String getMessage()
	{	
		return prefix + placeIndex + separator + (null!=point?point:pointStr);
	}
	
	public int getIndex() {
		return placeIndex;
	}
}
