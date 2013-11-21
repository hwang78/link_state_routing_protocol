package network_protocol;

import java.util.HashMap;
import java.util.Map;

public class SystemContext {
	/**
	 * To load original routing table
	 */
	public static Map<Integer, Router> ROUTERS = new HashMap<Integer, Router>(); 
	public static boolean isSetup = false;
}
