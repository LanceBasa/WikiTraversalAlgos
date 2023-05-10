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
            nodeCount++;
        }
        if(!nodeToExists){
            urlIDs.put(urlTo,nodeCount);
            nodeCount++;
        }

        int currentNodeID = urlIDs.get(urlFrom);
        ArrayList<Integer> currentNode = new ArrayList<Integer>();
        if (adjacencyList.get(currentNodeID).isEmpty()){
            adjacencyList.add(currentNodeID,currentNode); 
        }else{
            currentNode=adjacencyList.get(currentNodeID);
        }

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
	public int getShortestPath(String urlFrom, String urlTo){
        return 0;
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
	}

}