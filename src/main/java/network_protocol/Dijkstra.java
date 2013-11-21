package network_protocol;


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

	@SuppressWarnings("unused")
	private final int MAX_VERTEX;
	private final int INFINITY = 99999999;
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
