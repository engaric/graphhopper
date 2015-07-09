package com.graphhopper.tools;

import com.graphhopper.GraphHopper;
import com.graphhopper.storage.StorableProperties;
import com.graphhopper.util.CmdArgs;
import com.graphhopper.util.Constants;

/**
 * @author Peter Karich
 */
public class Import
{
    public static void main( String[] strs ) throws Exception
    {
        CmdArgs args = CmdArgs.read(strs);
        GraphHopper hopper = new GraphHopper().init(args);
        hopper.importOrLoad();
        StorableProperties properties = hopper.getGraph().getProperties();
		properties.put("import_version", Constants.VERSION);
        String versionType = (Constants.SNAPSHOT) ? "!! NON-PRODUCTION RELEASE !!" : "Production";
        properties.put("import_version.type", versionType);
        hopper.getGraph().flush();
        hopper.close();
    }
}
