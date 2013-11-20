package network_protocol;

import java.io.*;
import java.util.*;

class Vertex {
	public String label;
	public boolean wasVisited;

	public Vertex(String label) {
		this.label = label;
		wasVisited = false;
	}
}

class DistPare {

	public int parentVertex;
	public int distance;

	public DistPare(int parentVertex, int distance) {
		this.parentVertex = parentVertex;
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "DistPare [parentVertex=" + parentVertex + ", distance=" + distance + "]";
	}
	
	
}

class Graph {

	private final int MAX_VERTEX;
	private final int INFINITY = 999999;
	private int nVerts;
	private int nTree;
	private int currentVertex;
	private int startToCurrent;
	public int adjMatrix[][];
	public Vertex vertexList[];
	public DistPare sPath[];

	public Graph(int MAX_VERTEX) {
		this.MAX_VERTEX = MAX_VERTEX;
		adjMatrix = new int[MAX_VERTEX][MAX_VERTEX];
		vertexList = new Vertex[MAX_VERTEX];
		sPath = new DistPare[MAX_VERTEX];
		nVerts = 0;
		nTree = 0;
		for (int i = 0; i < MAX_VERTEX; i++)
			for (int j = 0; j < MAX_VERTEX; j++)
				adjMatrix[i][j] = INFINITY;
	}

	public void addVertex(String label) {
		vertexList[nVerts++] = new Vertex(label);
	}

	public void addEdge(int start, int end, int weight) {
		adjMatrix[start][end] = weight;
	}

	public void dijkstra() {
		int startTree = 0;
		vertexList[startTree].wasVisited = true;
		nTree = 1;
		for (int j = 0; j < nVerts; j++) {
			int tempDist = adjMatrix[startTree][j];
			sPath[j] = new DistPare(startTree, tempDist);
		}
		while (nTree < nVerts) {
			int indexMin = getMin();
			int minDist = sPath[indexMin].distance;
			if (minDist == INFINITY) {
				System.out.println("[Error]: Unable to reach!");
			} else {
				currentVertex = indexMin;
				startToCurrent = sPath[indexMin].distance;
			}
			vertexList[currentVertex].wasVisited = true;
			nTree++;
			adjust_sPath();
		}
		displaypaths();
	}

	private void displaypaths() {
       
        ArrayList<String> list = new ArrayList<String>();
        
        for(int i = 1; i < nVerts; i++){
        	System.out.print(vertexList[0].label + " --> " + vertexList[i].label + " : Costs is " + sPath[i].distance );
        	int vex = sPath[i].parentVertex;
        	while(  vertexList[vex] != vertexList[0]){
        		list.add(vertexList[vex].label);
        		vex = sPath[vex].parentVertex;
        	}
        	Collections.reverse(list);
        	System.out.print(" , Path is " + vertexList[0].label );
        	for(int j = 0; j<list.size(); j++){
        			System.out.print( " --> " +list.get(j));
        
        	}
        	System.out.print(" --> " + vertexList[i].label);
        		
        	System.out.println("");
        	
        	vex = 0;
        	list.clear();
        }
    }
	private void adjust_sPath() {
		int column = 1;
		while (column < nVerts) {
			if (vertexList[column].wasVisited) {
				column++;
				continue;
			}
			int currentToFringe = adjMatrix[currentVertex][column];
			int startToFringe = startToCurrent + currentToFringe;
			int sPathDist = sPath[column].distance;
			if (startToFringe < sPathDist) {
				sPath[column].parentVertex = currentVertex;
				sPath[column].distance = startToFringe;
			}
			column++;
		}
	}

	private int getMin() {
		int minDist = INFINITY;
		int indexMin = 0;
		for (int j = 0; j < nVerts; j++) {
			if (!vertexList[j].wasVisited && sPath[j].distance < minDist) {
				minDist = sPath[j].distance;
				indexMin = j;
			}
		}
		return indexMin;
	}

}

public class Dijkstra {
	public static void main(String[] args) throws FileNotFoundException {
		
		Graph theGraph = new Graph(20);

		// read first line of matrix which contains all the vertexs;
		
		Scanner sc = new Scanner(new File("DijkstraGraphTopology.txt"));
		if(sc.hasNextLine()){
			String vertexs = sc.nextLine();
			String[] vertexsArray = vertexs.split("\\s{1,}");
			for(int i = 0 ; i < vertexsArray.length; i++){
				if(vertexsArray[i].equals(""))
					continue;
				theGraph.addVertex(vertexsArray[i]);
			}
		}
		
		// read the remaining lines of matrix which contains path and weight 
		
		int row = 0;
		while(sc.hasNextLine()){
			String weights = sc.nextLine();
			String[] weightsArray = weights.split("\\s{1,}");
			for(int i = 1 ; i < weightsArray.length; i++){
				if(weightsArray[i].equals("") || weightsArray[i].equals("0") )
					continue;
				theGraph.addEdge(row, i-1, Integer.parseInt(weightsArray[i]));
			}
			
			row++;
		}
		
		theGraph.dijkstra();
		
	}

}
