package network_protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
	
	private String name;
	private ArrayList<Double> lsp;
	
	private Map<String,Object> routingTable = new HashMap<String, Object>();
	
	private Map<String, Object> shortestPath = new HashMap<String, Object>(); 
	
	
	public void addLSP(ArrayList<Double> lsp) {
		this.lsp = lsp;
	}
	
	public void floodLSP(){
		
	}
}
