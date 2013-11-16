package network_protocol;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Routing {
	
	ArrayList<String> orgRoutingTable;
	FileReading fr = new FileReading();
	
	private final Logger LOGGER = Logger.getLogger(Routing.class);
	
	public void readsInputRoutingTable(String filePath) {
		orgRoutingTable = fr.readFile(filePath);
		if(orgRoutingTable == null) {
			LOGGER.info("Routing tables read in failed.");
		}
		
		LOGGER.info("Start reads in routing tables...");
		System.out.println("Original routing table is as follows:");
		int tableSize = orgRoutingTable.size();
		for(int i = 0; i < tableSize; i++) {
			System.out.println(orgRoutingTable.get(i));
		}
		LOGGER.info("Routing table reads in finished");
	}
	
	public void getRouterRoutingTable(int routerNum) {
		if(orgRoutingTable == null) {
			LOGGER.info("No routing tables provided");
			return;
		}
		
		if(routerNum < 1 || routerNum > orgRoutingTable.size()) {
			LOGGER.info("Router number not exits");
			return;
		}
		
		String[] routerTable = (orgRoutingTable.get(routerNum-1)).split("     ");
		
		for (int i = 0; i < routerTable.length; i++) {
			int distance = Integer.parseInt(routerTable[i]);
			if (distance != 0 && distance != -1) {
				System.out.println("R"+(i+1)+":"+distance);
			}
		}
	}
	
	public void findMinPath(int startRouter, int endRouter) {
		
		String[] routingDistance = (orgRoutingTable.get(startRouter-1)).split("     ");
		int distance = Integer.parseInt(routingDistance[endRouter-1]);
		
		if(distance != -1)
			System.out.println("The shortest path from " + startRouter+" to " 
			+ endRouter +" is " + startRouter + "-" + endRouter+", total cost is " + distance);
		else {
			
		}
	}

}
