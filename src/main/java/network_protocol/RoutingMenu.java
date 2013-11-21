package network_protocol;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
			System.out.println("******************* MENU ******************");
			System.out.println("    1 - Load File.");
			System.out.println("    2 - Build Routing Table for Each Router.");
			System.out.println("    3 - Out Optimal Path and Minmum Cost.");
			System.out.println("   -1 - exit.");
			System.out.println("*********************************************");
			
			int optionNum = input.nextInt();
			
			switch (optionNum) {
				case 1 :
					System.out.println("Please load original routing table data file:");
					String filePath = input.next();
					if(!readsInputRoutingTable(filePath))
						break;
					if(!initRouters())
						break;
					if(!floodNetwork())
						break;
					if(!generateRoutingTableForAllLink())
						break;
					SystemContext.isSetup = true;
					break;		
				case 2 :
					if(SystemContext.isSetup == false) {
						System.err.println("[ERROR] please load network graph in first setp. then try again.");
						break;
					}
					System.out.println("Please select a router:");
					try {
						int routerNum = input.nextInt();
						getRouterRoutingTable(routerNum);
					} catch (Exception e) {
						System.err.println("[ERROR] invalid input. please try again.");
						break;
					}
					break;			
				case 3 :
					if(SystemContext.isSetup == false) {
						System.err.println("[ERROR] please load network graph in first setp. then try again.");
						break;
					}
					System.out.println("Please input the source and destination router number:");
					int start = input.nextInt();
					int end = input.nextInt();
					findMinPath(start, end);
					break;
				case -1:
					flag = false;
					System.out.println("Thank you!"); 
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
	public boolean readsInputRoutingTable(String filePath) {
		try {
			orgRoutingTable = fr.readFile(filePath);
		} catch (FileNotFoundException e) {
			System.err.println("[ERROR] file read error, please make sure the input is correct.");
			return false;
		} catch (IOException e) {
			System.err.println("[ERROR] system I/O error.");
			return false;
		}
		if(orgRoutingTable == null) {
			LOGGER.info("Routing tables read in failed.");
		}
		
		LOGGER.info("Start reads in routing tables...");
		System.out.println("Original routing table is as follows:");
		
		for(ArrayList<Double> tableList: orgRoutingTable) {
			for (int i = 0; i < tableList.size(); i++) {
				System.out.print(tableList.get(i).intValue() + "         ");
			}
			System.out.println();
		}
		
		LOGGER.info("Routing table reads in finished");
		return true;
	}
	
	/**
	 * initialize routers
	 */
	public boolean initRouters() {
		int i = 1;
		for(ArrayList<Double> lsp : orgRoutingTable) {
			Router router = new Router();
			router.setId(i);
			router.setName("R"+i);
			router.addLSP(lsp);
			SystemContext.ROUTERS.put(i, router);
			i++;
		}
		return true;
	}
	
	/**
	 * flood the network
	 */
	public boolean floodNetwork() {
		for(Router router: SystemContext.ROUTERS.values()) {
			router.floodLSP(router);
		}
		return true;
	}
	
	public boolean generateRoutingTableForAllLink() {
		for(Router router: SystemContext.ROUTERS.values()) {
			router.constructGraph();
			router.calculateRouteTable();
		}
		return true;
	}
	
	/**
	 * Generate routing table for each router 
	 * @param routerNum
	 */
	public void getRouterRoutingTable(int routerNum) {
	
		Router router = SystemContext.ROUTERS.get(routerNum);
		router.printRouteTable();
		
	}
	
	/**
	 * get the minimum path using Dijkstra algorithm
	 * @param startRouter
	 * @param endRouter
	 */
	public void findMinPath(int startRouter, int endRouter) {
		try {
			System.out.print("Path: "+SystemContext.ROUTERS.get(startRouter).getName());
			double total = SystemContext.ROUTERS.get(startRouter).calculatePath(endRouter);
			System.out.println("   total cost = "+total);
		} catch (Exception e) {
			System.err.println("invalid input, please try again.");
		}
		
	}

}
