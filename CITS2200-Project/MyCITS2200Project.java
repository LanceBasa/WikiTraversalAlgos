import java.util.*;
import java.io.*;


public class MyCITS2200Project implements CITS2200Project{
    private Map <String, Integer> urlIDs;
    private int nodeCount;
    private ArrayList<ArrayList<Integer>> adjacencyList;

    public MyCITS2200Project(){
        adjacencyList = new ArrayList<ArrayList<Integer>>();
        urlIDs = new HashMap<String, Integer>();
        nodeCount=0;
    }

	//remove. just for testing
	public void print(){
		//FIXME: FOR TESTING PURPOSES only

		System.out.println("\n----------------------------------Mapping-------------------------------------\n");
		for (Map.Entry<String, Integer> entry: urlIDs.entrySet()){
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println("ID: " + value + "\tUrl: " + key );
		}
		
		System.out.println("\n-------------------------------Direct Connections-------------------------------------\n");
		for (int i = 0; i < adjacencyList.size(); i++) {
			ArrayList<Integer> aNode = adjacencyList.get(i);
			System.out.print("Node " + i + ": ");
			for (int j = 0; j < aNode.size(); j++) {
				int adjacentNode = aNode.get(j);
				System.out.print(adjacentNode + " ");
			}
			System.out.println();
		}


	}

    /**
	 * Adds an edge to the Wikipedia page graph. If the pages do not
	 * already exist in the graph, they will be added to the graph.
	 * 
	 * @param urlFrom the URL which has a link to urlTo.
	 * @param urlTo the URL which urlFrom has a link to.
	 */
	public void addEdge(String urlFrom, String urlTo){
        //check
        boolean nodeFromExists = urlIDs.containsKey(urlFrom);
        boolean nodeToExists = urlIDs.containsKey(urlTo);

        if(!nodeFromExists){
            urlIDs.put(urlFrom,nodeCount);
            adjacencyList.add(new ArrayList<Integer>());
            nodeCount++;
        }
        if(!nodeToExists){
            urlIDs.put(urlTo,nodeCount);
            adjacencyList.add(new ArrayList<Integer>());
            nodeCount++;
        }

        int currentNodeID = urlIDs.get(urlFrom);
        ArrayList<Integer> currentNode;

        currentNode=adjacencyList.get(currentNodeID);
        //adding nodes that are adjacent to currentNode
        int adjnodeID = urlIDs.get(urlTo);
        currentNode.add(adjnodeID);
        //adjacencyList.add(currentNodeID,currentNode); 
    }

	/**
	 * Finds the shorest path in number of links between two pages.
	 * If there is no path, returns -1.
	 * 
	 * @param urlFrom the URL where the path should start.
	 * @param urlTo the URL where the path should end.
	 * @return the legnth of the shorest path in number of links followed.
	 */
	public int getShortestPath(String urlFrom, String urlTo){  // needs to be breath first search using a queue

		LinkedList<Integer> queue = new LinkedList<Integer>();      // queue for traversing the graph
		int startID=urlIDs.get(urlFrom);							//starting id
		int finishID=urlIDs.get(urlTo);								//finish id
		
		int[] distances= new int[nodeCount];						// distance from given starting node  to finish node
		boolean[] visited = new boolean[nodeCount];					// keep track of what is visited
		for (int i=0;i<nodeCount;i++){								// initialize distances to 0 and visited to false
			distances[i]=0;
			visited[i]=false;
		}

		queue.addLast(startID);                                 // adding the root (startVertex)
		while (!queue.isEmpty()) {

			int head = queue.poll();
			visited[head]=true;
			ArrayList<Integer> currentNodeDirectPaths= adjacencyList.get(head);

			//check if head of the queue has direct link to finish vertex if so just add whatever 
			if(currentNodeDirectPaths.contains(finishID)){
				distances[finishID]= distances[head] +1;
				return distances[finishID];
			}

			for (int i = 0; i < currentNodeDirectPaths.size(); i++) {
				if(!visited[currentNodeDirectPaths.get(i)] && !queue.contains(currentNodeDirectPaths.get(i))){
					queue.addLast(currentNodeDirectPaths.get(i));				
					distances[currentNodeDirectPaths.get(i)]= distances[head] + 1;
				}
			}
		}
		return distances[finishID];                                             
    }







	/**
	 * Finds all the centers of the page graph. The order of pages
	 * in the output does not matter. Any order is correct as long as
	 * all the centers are in the array, and no pages that aren't centers
	 * are in the array.
	 * 
	 * @return an array containing all the URLs that correspond to pages that are centers.
	 */
	public String[] getCenters(){
        String[] bob = new String[1];

        return bob;
    }

	/**
	 * Finds all the strongly connected components of the page graph.
	 * Every strongly connected component can be represented as an array 
	 * containing the page URLs in the component. The return value is thus an array
	 * of strongly connected components. The order of elements in these arrays
	 * does not matter. Any output that contains all the strongly connected
	 * components is considered correct.
	 * 
	 * @return an array containing every strongly connected component.
	 */
	public String[][] getStronglyConnectedComponents(){
        String[][] bob = new String[1][1];
        return bob;

    }

	/**
	 * Finds a Hamiltonian path in the page graph. There may be many
	 * possible Hamiltonian paths. Any of these paths is a correct output.
	 * This method should never be called on a graph with more than 20
	 * vertices. If there is no Hamiltonian path, this method will
	 * return an empty array. The output array should contain the URLs of pages
	 * in a Hamiltonian path. The order matters, as the elements of the
	 * array represent this path in sequence. So the element [0] is the start
	 * of the path, and [1] is the next page, and so on.
	 * 
	 * @return a Hamiltonian path of the page graph.
	 */
	public String[] getHamiltonianPath(){
        String[] bob = new String[1];

        return bob;

    }



	










    //--------------------------------------------------------------------------------------------------------
    public static void loadGraph(CITS2200Project project, String path) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			while (reader.ready()) {
				String from = reader.readLine();
				String to = reader.readLine();
				System.out.println("Adding edge from " + from + " to " + to);
				project.addEdge(from, to);
			}
			project.print();
		} catch (Exception e) {
			System.out.println("There was a problem:");
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		// Change this to be the path to the graph file.
		String pathToGraphFile = "./example_graph.txt";
		// Create an instance of your implementation.
		CITS2200Project proj = new MyCITS2200Project();
		// Load the graph into the project.
		loadGraph(proj, pathToGraphFile);

		// Write your own tests!

		//change it to test
		String urlFrom = "/wiki/Braess%27_paradox";
		String urlTo = "/wiki/Minimum-cost_flow_problem";


		int q1result = proj.getShortestPath(urlFrom, urlTo);

		
		System.out.println("\n----------------------------------Question 1: ---------------------------------------------------");
		System.out.println("-------min number of traversed vertex from given url to finish url---------------\n");

		System.out.println("Shortest path from" + urlFrom + " to " + urlTo + " is " + q1result + "\n");

	}

}