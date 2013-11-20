package network_protocol;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class RoutingMenu {

	public static void main(String[] args) {
		
		RoutingMenu rm = new RoutingMenu();
		rm.startMenu();
	}
	
	private final Logger LOGGER = Logger.getLogger(RoutingMenu.class);
	
	ArrayList<String> orgRoutingTable;
	FileReading fr = new FileReading();
	
	
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
	 * read input file 
	 * @param filePath
	 */
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
	
	/**
	 * generate the routing table for a router 
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
		
		String[] routerTable = (orgRoutingTable.get(routerNum-1)).split("     ");
		
		for (int i = 0; i < routerTable.length; i++) {
			int distance = Integer.parseInt(routerTable[i]);
			if (distance != 0 && distance != -1) {
				System.out.println("R"+(i+1)+":"+distance);
			}
		}
	}
	
	/**
	 * get the minimum path 
	 * @param startRouter
	 * @param endRouter
	 */
	public void findMinPath(int startRouter, int endRouter) {
		
		String[] routingDistance = (orgRoutingTable.get(startRouter-1)).split("     ");
		int distance = Integer.parseInt(routingDistance[endRouter-1]);
		
//		if(distance != -1)
//			System.out.println("The shortest path from " + startRouter+" to " 
//			+ endRouter +" is " + startRouter + "-" + endRouter+", total cost is " + distance);
//		else {
//			
//		}
	}

}
