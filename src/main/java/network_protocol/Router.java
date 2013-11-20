package network_protocol;

import java.util.HashMap;
import java.util.Map;

public class Router {
	
	String name;
	
	Map<String,Object> routingTable = new HashMap<String, Object>();
	
	Map<String, Object> shortestPath = new HashMap<String, Object>(); 
}
