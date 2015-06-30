package com.graphhopper.routing.util;

import com.graphhopper.util.DistanceCalcEarth;
import com.graphhopper.util.Helper;

public class SpeedParserUtil
{
	public static double parseSpeed(String str) {
		if (Helper.isEmpty(str))
			return -1;

		try {
			int val;
			// see https://en.wikipedia.org/wiki/Knot_%28unit%29#Definitions
			int mpInteger = str.indexOf("mp");
			if (mpInteger > 0) {
				str = str.substring(0, mpInteger).trim();
				val = Integer.parseInt(str);
				return val * DistanceCalcEarth.KM_MILE;
			}

			int knotInteger = str.indexOf("knots");
			if (knotInteger > 0) {
				str = str.substring(0, knotInteger).trim();
				val = Integer.parseInt(str);
				return val * 1.852;
			}

			int kmInteger = str.indexOf("km");
			if (kmInteger > 0) {
				str = str.substring(0, kmInteger).trim();
			} else {
				kmInteger = str.indexOf("kph");
				if (kmInteger > 0) {
					str = str.substring(0, kmInteger).trim();
				}
			}

			return Integer.parseInt(str);
		} catch (Exception ex) {
			return -1;
		}
	}
}
