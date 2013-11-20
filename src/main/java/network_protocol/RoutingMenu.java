package network_protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class RoutingMenu {

	public static void main(String[] args) {
		
		RoutingMenu rm = new RoutingMenu();
		rm.startMenu();
	}
	
	private final Logger LOGGER = Logger.getLogger(RoutingMenu.class);
	
	FileLoader fr = new FileLoader();
	ArrayList<ArrayList<Double>> orgRoutingTable;
	
	
	
	public void startMenu() {
		boolean flag = true;
		Scanner input = new Scanner(System.in);
		final String FILE_PATH = "network.txt";
		
		while(flag) {
			System.out.println("1 - Load File");
			System.out.println("2 - Build Routing Table for Each Router");
			System.out.println("3 - Out Optimal Path and Minmum Cost");
			System.out.println("-1 - exit");
					
			int optionNum = input.nextInt();
			
			switch (optionNum) {
				case 1 :
					readsInputRoutingTable(FILE_PATH);
					initRouters();
					break;		
				case 2 :
					System.out.println("Please select a router:");
					int routerNum = input.nextInt();
					getRouterRoutingTable(routerNum);
					break;			
				case 3 :
					System.out.println("Please input the source and destination router number:");
					int start = input.nextInt();
					int end = input.nextInt();
					findMinPath(start, end);
					break;
				case -1:
					flag = false;
					break;
				default :
					break;
			}
		}
		
		input.close();
	}
	
	/**
	 * Load original routing table
	 * @param filePath
	 */
	public void readsInputRoutingTable(String filePath) {
		orgRoutingTable = fr.readFile(filePath);
		if(orgRoutingTable == null) {
			LOGGER.info("Routing tables read in failed.");
		}
		
		LOGGER.info("Start reads in routing tables...");
		System.out.println("Original routing table is as follows:");
		System.out.println(orgRoutingTable);
		LOGGER.info("Routing table reads in finished");
	}
	
	public void initRouters() {
		int i = 1;
		for(ArrayList<Double> lsp : orgRoutingTable) {
			Router router = new Router();
			router.addLSP(lsp);
			SystemContext.ROUTERS.put(i, router);
			i++;
		}
	}
	
	/**
	 * Generate routing table for each router 
	 * @param routerNum
	 */
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
	
	/**
	 * get the minimum path 
	 * @param startRouter
	 * @param endRouter
	 */
	public void findMinPath(int startRouter, int endRouter) {

	}

}
