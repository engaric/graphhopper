/*
 *  Licensed to GraphHopper and Peter Karich under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for 
 *  additional information regarding copyright ownership.
 * 
 *  GraphHopper licenses this file to you under the Apache License, 
 *  Version 2.0 (the "License"); you may not use this file except in 
 *  compliance with the License. You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.http;

import com.graphhopper.GraphHopper;
import com.graphhopper.storage.StorableProperties;
import com.graphhopper.util.Constants;
import com.graphhopper.util.Helper;
import com.graphhopper.util.shapes.BBox;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Karich
 */
public class InfoServlet extends GHBaseServlet
{
    @Inject
    private GraphHopper hopper;

    @Override
    public void doGet( HttpServletRequest req, HttpServletResponse res ) throws ServletException, IOException
    {
        BBox bb = hopper.getGraph().getBounds();
        List<Double> list = new ArrayList<Double>(4);
        list.add(bb.minLon);
        list.add(bb.minLat);
        list.add(bb.maxLon);
        list.add(bb.maxLat);

        JSONObject json = new JSONObject();
        json.put("bbox", list);

        String[] vehicles = hopper.getGraph().getEncodingManager().toString().split(",");
        json.put("supported_vehicles", vehicles);
        JSONObject features = new JSONObject();
        for (String v : vehicles)
        {
            JSONObject perVehicleJson = new JSONObject();
            perVehicleJson.put("elevation", hopper.hasElevation());
            features.put(v, perVehicleJson);
        }
        json.put("features", features);

        json.put("version", Constants.VERSION);
        String versionType = (Constants.SNAPSHOT) ? "!! NON-PRODUCTION RELEASE !!" : "Production";
        json.put("version_type", versionType);
        
        
        json.put("build_date", Constants.BUILD_DATE);

        StorableProperties props = hopper.getGraph().getProperties();
        addIfSet(json, props, "osmreader.import.date", "import_date");
        addIfSet(json, props, "prepare.date");
        addIfSet(json, props, "itn.data_version","itn_data_version");
        addIfSet(json, props, "hn.data_version","hn_data_version");
        addIfSet(json, props, "dpn.data_version","dpn_data_version");
        addIfSet(json, props, "import_version");
        addIfSet(json, props, "import_version.type");

        writeJson(req, res, json);
    }

	private void addIfSet(JSONObject json, StorableProperties props,
			String propertyName) {
		addIfSet(json, props, propertyName, propertyName);
	}

	private void addIfSet(JSONObject json, StorableProperties props, String propertyName, String outputName) {
		String value = props.get(propertyName);
		if(!Helper.isEmpty(value)) {
			json.put(outputName, value);
		}
	}
}
