import java.util.*;
import java.io.*;
import static java.lang.Math.min;



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
        ArrayList<Integer> currentNode = adjacencyList.get(currentNodeID);
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
		//variable to store nodes in a list. to be converted to an array later
        List<String> centerslist = new ArrayList<String>();		
		// Tracker of the current minimum of the maximum shortest path
		int minimum=Integer.MAX_VALUE;

        //traverse through each node and perform bfs for each node
        for(String currentFrom: urlIDs.keySet()){
			// tracker for the maximum shortest path in the current node
			int maxShortestPath = 0;

            // inner loop to loop the starting vertex to next vertex, changing the ending vertex.
			// the starting vertex will not change untill all available vertex for ending has been tested
            for(String currentTo: urlIDs.keySet()){
                // not considering the starting vertex for centers
                if (currentFrom!= currentTo){
                    int curentShortestPath = getShortestPath(currentFrom,currentTo);
					// if there is a node further than the currentTo node update the max shortest path
 					if (maxShortestPath<curentShortestPath){
						maxShortestPath=curentShortestPath;
					}
                }
            }
			// before changing the current from node, check if the max shortest path of the current node is the minimum
			// if so, clear the list and add the current node, else if it is equal, dont clear it and ad the node.
			if(maxShortestPath<minimum){
				minimum=maxShortestPath;
				centerslist.clear();
				centerslist.add(currentFrom);
			}else if (maxShortestPath==minimum){
				centerslist.add(currentFrom);
			}
        }

		// convert the ArrayListOf Centers into a list
        String[] centers = centerslist.toArray(new String[centerslist.size()]); 
        return centers;
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


	/** old strongly connected components.... works but does not work...
	public String[][] getStronglyConnectedComponents(){
		ArrayList<ArrayList<String>> scc = new ArrayList<>();		// 2D array Lists of strings
		Integer sccCount=0;											// SCC count
		int[] lowLinkVal=new int[nodeCount];						//low link val of each nodes
		Stack<Integer> myStack = new Stack<Integer>();
 
		//initialize all values as unvisited if Integer.NA
		for(int i=0; i<nodeCount;i++){
			scc.add(new ArrayList<String>());
			lowLinkVal[i]=Integer.MAX_VALUE;					
		}

		// call dfs function for each node that has not been visited
		for (int i=0; i<nodeCount;i++){
			if(lowLinkVal[i]== Integer.MAX_VALUE){
				myStack.add(i);
				dfs(myStack, lowLinkVal,scc,sccCount);
			}
		}

		// array to keep track of what is added in the stack
		boolean[] added = new boolean[nodeCount];
		Arrays.fill(added,false);

		while (!myStack.isEmpty()){
			int partofSCC=myStack.pop();
			int currentLowestSCC=lowLinkVal[partofSCC];
			if(added[currentLowestSCC]){
				continue;
			}else{
				added[currentLowestSCC]=true;
			}
			for(int j=0; j<nodeCount;j++){
				if (lowLinkVal[j]==currentLowestSCC){
					for (Map.Entry<String, Integer> entry: urlIDs.entrySet()){
						String key = entry.getKey();
						Integer value = entry.getValue();
						ArrayList<String> temp=scc.get(sccCount);
						if(value==j && !temp.contains(key) ){
							temp.add(key);
						}
					}						
				}
			}
			sccCount++;
		}

		String[][] sccArray = new String[scc.size()][];
		for (int i = 0; i < scc.size(); i++) {
			ArrayList<String> component = scc.get(i);
			if(!scc.isEmpty()){}
			sccArray[i] = component.toArray(new String[component.size()]);
		}
        return sccArray;
    }

	private void dfs(Stack<Integer> myStack, int[] lowLinkVal, ArrayList<ArrayList<String>> scc, Integer counter) {
		int nodeID = myStack.peek();
		lowLinkVal[nodeID] = nodeID;
		int minLowLinkVal = nodeID; // Track the minimum lowLinkVal during traversal
		ArrayList<Integer> currentNodeAdj = adjacencyList.get(nodeID);
		for (Integer j : currentNodeAdj) {
			if (!myStack.contains(j) && lowLinkVal[j] == Integer.MAX_VALUE) {
				myStack.add(j);
				dfs(myStack, lowLinkVal,scc,counter);
			}
			if (myStack.contains(j)) {
				minLowLinkVal = Math.min(minLowLinkVal, lowLinkVal[j]);
			}
		}


		
		lowLinkVal[nodeID] = minLowLinkVal; // Update the lowLinkVal for the current node
		ArrayList<Integer> currAdj = adjacencyList.get(lowLinkVal[nodeID]);
		for(int neighbours : currAdj){
			if(lowLinkVal[nodeID] > lowLinkVal[neighbours]){
				lowLinkVal[nodeID] = lowLinkVal[neighbours];
			}
		}
		

		if(nodeID==lowLinkVal[nodeID]){
			ArrayList<String> currentSCC = new ArrayList<String>();
			for (int i=0; i<nodeCount;i++){
				if(lowLinkVal[i]==nodeID){
					for (Map.Entry<String, Integer> entry: urlIDs.entrySet()){
						String key = entry.getKey();
						Integer value = entry.getValue();
						ArrayList<String> temp=scc.get(counter);
						if(value==i && !temp.contains(key) ){
							currentSCC.add(key);
						}
						else
						{
							continue;
						}	
					}
				}
			}		
			counter++;
			scc.add(currentSCC);
		}
	}

	*/

	public String[][] getStronglyConnectedComponents() {
        int[] lowLinkVal = new int[nodeCount];			// to keep track current low link val
        int[] lowestLink = new int[nodeCount];			// to keep track of the lowest link and when to create a new scc
        boolean[] onStack = new boolean[nodeCount];
        Stack<Integer> stack = new Stack<>();

        // Initialize arrays
        for (int i = 0; i < nodeCount; i++) {
            lowLinkVal[i] = -1;
            lowestLink[i] = -1;
            onStack[i] = false;
        }

		//initialize the arrayList
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
	public String[] getHamiltonianPath() {
		List<Integer> hamiltonianPath = findHamiltonianPath();
	
		if (hamiltonianPath.isEmpty()) {
			return new String[0]; // Return an empty array if no Hamiltonian path is found
		}
	
		String[] path = new String[hamiltonianPath.size()];
	
		for (int i = 0; i < hamiltonianPath.size(); i++) {
			int vertex = hamiltonianPath.get(i);
			String url = getUrlFromId(vertex);
			path[i] = url;
		}
	
		return path;
	}
	
	public List<Integer> findHamiltonianPath() {
		List<Integer> path = new ArrayList<>();
		boolean[] visited = new boolean[nodeCount];
	
		for (int v = 0; v < nodeCount; v++) {
			if (backtrack(v, path, visited)) {
				return path; // Hamiltonian path found
			}
		}
		return new ArrayList<>(); // No Hamiltonian path found
	}
	
	public boolean backtrack(int vertex, List<Integer> path, boolean[] visited) {
		path.add(vertex);
		visited[vertex] = true;
		int startVertex;
	
		if (path.size() == nodeCount) {
			startVertex = path.get(0);
			if (adjacencyList.get(vertex).contains(startVertex)) {
				return true; // Hamiltonian path found
			}
		}
	
		for (int v : adjacencyList.get(vertex)) {
			if (!visited[v]) {
				if (backtrack(v, path, visited)) {
					return true; // Hamiltonian path found
				}
			}
		}
	
		path.remove(path.size() - 1); // Remove the last vertex from the path
		visited[vertex] = false; // Unvisit the node
		return false; // No Hamiltonian path found
	}
	
	public String getUrlFromId(int id) {
		for (Map.Entry<String, Integer> entry : urlIDs.entrySet()) {
			if (entry.getValue().equals(id)) {
				return entry.getKey(); // Return the URL associated with the ID
			}
		}
		return null; // Return null if the ID is not found (should not happen in a valid graph)
	}

}