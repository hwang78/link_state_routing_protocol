package network_protocol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;

public class Router {

	private final Logger LOGGER = Logger.getLogger(Router.class);
	private Integer id;
	private String name;
	private ArrayList<Double> lsp;
	public ArrayList<Router> routerList = new ArrayList<Router>();
	Graph theGraph;

	public Map<Integer, Integer> routingTable = new HashMap<Integer, Integer>();

	/**
	 * add LSP to router
	 * 
	 * @param lsp
	 */
	public void addLSP(ArrayList<Double> lsp) {
		this.lsp = lsp;
		routerList.add(this);
	}

	/**
	 * add a router to the router list
	 * 
	 * @param router
	 */
	public void addRouter(Router router) {
		routerList.add(router);
	}

	/**
	 * flood LSP to direct neighbors
	 * 
	 * @param router
	 */
	public void floodLSP(Router router) {
		int i = 1;
		for (Double dis : lsp) {
			if (dis != 0 && dis != -1) {
				if (!SystemContext.ROUTERS.get(i).isRouterExist(router)) {
					LOGGER.debug("flood router[" + router.getName() + "] to [" + SystemContext.ROUTERS.get(i).getName() + "]");
					SystemContext.ROUTERS.get(i).addRouter(router);
					SystemContext.ROUTERS.get(i).floodLSP(router);
				}
			}
			i++;
		}
	}

	class MyComparator implements Comparator<Router> {

		public int compare(Router o1, Router o2) {
			if (o1.getId() < o2.getId())
				return -1;
			else if (o1.getId() > o2.getId())
				return 1;
			else
				return 0;
		}

	}

	class VertexComparator implements Comparator<Vertex> {

		public int compare(Vertex o1, Vertex o2) {
			return o1.label.compareTo(o2.label);
		}

	}

	/**
	 * construct graph to for Dijkstra algorithm
	 */
	public void constructGraph() {
		theGraph = new Graph(routerList.size());
		for (Router router : routerList) {
			theGraph.addVertex(String.valueOf(router.getId()));
		}

		ArrayList<Router> sortedRouterList = (ArrayList<Router>) routerList.clone();
		Collections.sort(sortedRouterList, new MyComparator());

		for (int i = 0; i < sortedRouterList.size(); i++) {
			Router router = sortedRouterList.get(i);
			for (int j = 0; j < router.getLsp().size(); j++) {
				if (router.getLsp().get(j) == 0.0 || router.getLsp().get(j) == -1.0)
					continue;
				theGraph.addEdge(i, j, router.getLsp().get(j).intValue());
			}
		}

		// matrix transpose
		for (int i = 0; i < theGraph.adjMatrix.length; i++) {
			int temp;
			temp = theGraph.adjMatrix[i][this.getId() - 1];
			theGraph.adjMatrix[i][this.getId() - 1] = theGraph.adjMatrix[i][0];
			theGraph.adjMatrix[i][0] = temp;
		}

		for (int i = 0; i < theGraph.adjMatrix.length; i++) {
			int temp;
			temp = theGraph.adjMatrix[this.getId() - 1][i];
			theGraph.adjMatrix[this.getId() - 1][i] = theGraph.adjMatrix[0][i];
			theGraph.adjMatrix[0][i] = temp;
		}

		Arrays.sort(theGraph.vertexList, new VertexComparator());
		Vertex temp = theGraph.vertexList[0];
		theGraph.vertexList[0] = theGraph.vertexList[this.getId() - 1];
		theGraph.vertexList[this.getId() - 1] = temp;

	}

	/**
	 * calculate the routing table for the router
	 */
	public void calculateRouteTable() {

		theGraph.dijkstra();

		for (int i = 1; i < routerList.size(); i++) {
			int vex = theGraph.sPath[i].parentVertex;
			int preVex = i;
			while (theGraph.vertexList[vex] != theGraph.vertexList[0]) {
				preVex = vex;
				vex = theGraph.sPath[vex].parentVertex;
			}
			routingTable.put(Integer.valueOf(theGraph.vertexList[i].label), Integer.valueOf(theGraph.vertexList[preVex].label));
		}

		// ArrayList<String> list = new ArrayList<String>();
		// for(int i = 1; i<theGraph.adjMatrix.length;i++) {
		// int vex = theGraph.sPath[i].parentVertex;
		// while( theGraph.vertexList[vex] != theGraph.vertexList[0]){
		// list.add(theGraph.vertexList[vex].label);
		// vex = theGraph.sPath[vex].parentVertex;
		// }
		// Collections.reverse(list);
		// if(list.size()==0) {
		// routingTable.put(Integer.valueOf(theGraph.vertexList[i].label),
		// Integer.valueOf(theGraph.vertexList[i].label));
		// }else {
		// routingTable.put(Integer.valueOf(theGraph.vertexList[i].label),
		// Integer.valueOf(list.get(0)));
		// }
		//
		// }

	}

	public void printRouteTable() {
		System.out.println("Destination  NextHop");

		for (Map.Entry<Integer, Integer> entry : routingTable.entrySet()) {
			System.out.println("      " + entry.getKey() + "             " + entry.getValue());
		}
	}

	/**
	 * calculate path to the destination(direct or indirect)
	 * 
	 * @param dest
	 * @return
	 */
	public double calculatePath(int dest) {
		int nextHopId = routingTable.get(dest);
		Router nextHop = null;
		for (Router router : routerList) {
			if (router.getId() == nextHopId) {
				nextHop = router;
				break;
			}
		}
		if (nextHop == null) {
			throw new RuntimeException("Calculate path error");
		}
		if (nextHopId == dest) {
			System.out.print("-->" + nextHop.getName());
			return lsp.get(dest - 1);
		}

		System.out.print("-->" + nextHop.getName());
		return lsp.get(nextHopId - 1) + nextHop.calculatePath(dest);
	}

	public boolean isRouterExist(Router router) {
		return routerList.contains(router);
	}

	public ArrayList<Double> getLsp() {
		return lsp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Router [name=" + name + "]";
	}

}
