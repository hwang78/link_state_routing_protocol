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
	
	
	/**
	 * user menu 
	 */
	public void startMenu() {
		boolean flag = true;
		Scanner input = new Scanner(System.in);
		
		
		while(flag) {
			System.out.println("1 - Load File");
			System.out.println("2 - Build Routing Table for Each Router");
			System.out.println("3 - Out Optimal Path and Minmum Cost");
			System.out.println("-1 - exit");
					
			int optionNum = input.nextInt();
			
			switch (optionNum) {
				case 1 :
					System.out.println("Please load original routing table data file:");
					String filePath = input.nextLine();
					readsInputRoutingTable(filePath);
					initRouters();
					floodNetwork();
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
	
	/**
	 * initialize routers
	 */
	public void initRouters() {
		int i = 1;
		for(ArrayList<Double> lsp : orgRoutingTable) {
			Router router = new Router();
			router.setId(i);
			router.setName("R"+i);
			router.addLSP(lsp);
			SystemContext.ROUTERS.put(i, router);
			i++;
		}
	}
	
	/**
	 * flood the network
	 */
	public void floodNetwork() {
		for(Router router: SystemContext.ROUTERS.values()) {
			router.floodLSP(router);
		}
	}
	
	/**
	 * Generate routing table for each router 
	 * @param routerNum
	 */
	public void getRouterRoutingTable(int routerNum) {
		
		for(Router router: SystemContext.ROUTERS.values()) {
			System.out.println("Router["+router.getName()+"]"+router.routerList);
		}
		
		for(Router router: SystemContext.ROUTERS.values()) {
			router.constructGraph();
		}
		
	}
	
	/**
	 * get the minimum path using Dijkstra algorithm
	 * @param startRouter
	 * @param endRouter
	 */
	public void findMinPath(int startRouter, int endRouter) {
		System.out.print("Path: "+SystemContext.ROUTERS.get(startRouter).getName());
		double total = SystemContext.ROUTERS.get(startRouter).calculatePath(endRouter);
		System.out.println("   total cost = "+total);
	}

}
