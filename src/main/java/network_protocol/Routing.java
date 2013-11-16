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
		int tableSize = orgRoutingTable.size();
		for (int i = 0; i < tableSize; i++) {
			System.out.print("  R"+(i+1)+" ");
		}
		System.out.println();
		for(int i = 0; i < tableSize; i++) {
			System.out.println("R" + (i+1)+":" + orgRoutingTable.get(i));
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
			
	}

}
