package network_protocol;

import java.util.Scanner;

public class RoutingMenu {

	public static void main(String[] args) {
		
		RoutingMenu rm = new RoutingMenu();
		rm.startMenu();
	}
	
	Routing routing = new Routing();
	
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
					routing.readsInputRoutingTable(FILE_PATH);
					break;		
				case 2 :
					System.out.println("Please select a router:");
					int routerNum = input.nextInt();
					routing.getRouterRoutingTable(routerNum);
					break;			
				case 3 :
					System.out.println("Please input the source and destination router number:");
					int start = input.nextInt();
					int end = input.nextInt();
					routing.findMinPath(start, end);
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

}
