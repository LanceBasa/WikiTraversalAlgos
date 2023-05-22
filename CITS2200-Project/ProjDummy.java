import java.util.*;
import java.io.*;
import static java.lang.Math.min;



public class ProjDummy implements CITS2200Project{
    private Map <String, Integer> urlIDs;
    private int nodeCount;
    private ArrayList<ArrayList<Integer>> adjacencyList;

    public ProjDummy(){
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
        int[] maxShortestPathPerNode = new int[nodeCount];  // to store the furthest node of each nodes
        int min = -1;                                       // for storing the minimum of the maximums
        List<Integer> centersID = new ArrayList<Integer>(); //temporary to stor centersID as we dont know how mny centers

        //traverse through each node and perform bfs for each node
        for(String currentFrom: urlIDs.keySet()){
            //array for current node shortest path for each node
            int[] currentNodeShortestPaths= new int[nodeCount];

            //inner loop to loop the starting vertex to next vertex, changing the ending vertex.
            for(String currentTo: urlIDs.keySet()){
                // not considering the starting vertex for centers
                if (currentFrom!= currentTo){
                    int currentToID=urlIDs.get(currentTo);
                    int shortestpath = getShortestPath(currentFrom,currentTo);
                    currentNodeShortestPaths[currentToID]= shortestpath;
                }
            }
            // getting the currentFromID in the loop to set its maximum shortest path in the array
            int currentFromID=urlIDs.get(currentFrom);
            maxShortestPathPerNode[currentFromID]=getMax(currentNodeShortestPaths);

        }

        // after all maximum shortest path for each vertex are collected,
        // get the minimum of all the maximum, if the minimum is equal to more than one node, add it to output
        min = getMin(maxShortestPathPerNode);
        for (int i=0; i<maxShortestPathPerNode.length;i++){
            if(maxShortestPathPerNode[i]==min){
                centersID.add(i);
            }
        }


        // converting the id back to strings
        String[] centers = new String[centersID.size()];
        for (int i = 0; i < centersID.size(); i++) {
            for(Map.Entry<String, Integer> entry: urlIDs.entrySet()) {
                if(entry.getValue()== centersID.get(i) ) {
                    centers[i] = entry.getKey();
                    break;
                }
            }
        }

        return centers;
    }

    // helper for getCenter to find the furthest node reachable from a node
    private int getMax(int[] nums){
        int max=nums[0];
        for (Integer i : nums){
            if (i>max){
                max=i;
            }
        }
        return max;
    }

    // helper for get center to find minimum of the maxes
    private int getMin(int[] nums){
        int min=nums[0];
        for (Integer i : nums){
            if (i<min){
                min=i;
            }
        }
        return min;
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
	public String[][] getStronglyConnectedComponents() {
        int[] lowLinkVal = new int[nodeCount];
        int[] lowestLink = new int[nodeCount];
        boolean[] onStack = new boolean[nodeCount];
        Stack<Integer> stack = new Stack<>();

        // Initialize arrays
        for (int i = 0; i < nodeCount; i++) {
            lowLinkVal[i] = -1;
            lowestLink[i] = -1;
            onStack[i] = false;
        }

        List<List<Integer>> scc = new ArrayList<>();

        for (int nodeID = 0; nodeID < nodeCount; nodeID++) {
            if (lowestLink[nodeID] == -1) {
                dfs(nodeID, lowestLink, lowLinkVal, onStack, stack, scc);
            }
        }

        // Convert the strongly connected components to a 2D array
        String[][] sccArray = new String[scc.size()][];
        for (int i = 0; i < scc.size(); i++) {
            List<Integer> component = scc.get(i);
            String[] componentArray = new String[component.size()];
            for (int j = 0; j < component.size(); j++) {
                for(Map.Entry<String, Integer> entry: urlIDs.entrySet()) {
                    if(entry.getValue() == component.get(j)) {
                        componentArray[j] = entry.getKey();
                      break;
                    }
                }
            }
            sccArray[i] = componentArray;
        }
        return sccArray;
    }


    private void dfs(int nodeID, int[] lowestLink, int[] lowLinkVal, boolean[] onStack, Stack<Integer> stack , List<List<Integer>> scc) {
        lowestLink[nodeID] = nodeID;
        lowLinkVal[nodeID] = nodeID;
        stack.push(nodeID);
        onStack[nodeID] = true;

        List<Integer> currentNodeAdj = adjacencyList.get(nodeID);
        for (int w : currentNodeAdj) {
            if (lowestLink[w] == -1) {
                dfs(w, lowestLink, lowLinkVal, onStack, stack, scc);
                lowLinkVal[nodeID] = Math.min(lowLinkVal[nodeID], lowLinkVal[w]);
            } else if (onStack[w]) {
                lowLinkVal[nodeID] = Math.min(lowLinkVal[nodeID], lowestLink[w]);
            }
        }

        if (lowLinkVal[nodeID] == lowestLink[nodeID]) {
            List<Integer> component = new ArrayList<>();
            int w;
            do {
                w = stack.pop();
                onStack[w] = false;
                component.add(w);
            } while (w != nodeID);
            scc.add(component);
        }
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
}

    