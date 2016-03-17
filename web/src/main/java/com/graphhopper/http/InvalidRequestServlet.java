/*
 * Copyright © 2011. Team Lazer Beez (http://teamlazerbeez.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.graphhopper.http;

import com.graphhopper.GHResponse;
import org.eclipse.jetty.http.HttpStatus.Code;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

public class InvalidRequestServlet extends GHBaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String resource = req.getRequestURI();
        if (resource.startsWith("/") && resource.length() > 1) {
            resource = resource.substring(1, resource.length());
        }

        String message = "Resource " + resource
                + " does not exist. Valid resources are route, nearest.";
        boolean isGpx = "gpx".equals(req.getParameter("type"));
        if (isGpx) {
            GHResponse ghResponse = new GHResponse().addError(new APIException(Code.NOT_FOUND,
                    message));
            String xml = createGPXString(req, res, ghResponse);
            res.setStatus(SC_NOT_FOUND);
            res.getWriter().append(xml);
        } else {
            JSONObject json = new JSONObject();
            Map<String, Object> map = new HashMap<>();
            map.put("statuscode", "" + HttpServletResponse.SC_NOT_FOUND);
            map.put("message", message);
            json.put("error", map);
            writeJsonError(res, HttpServletResponse.SC_NOT_FOUND, json);
        }
    }
}