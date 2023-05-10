import CITS2200Project;
import CITS2200.*;
import java.util.*;


public class DSAProject implements CITS2200Project{
    private Map <String, Integer> urlIDs;
    private int nodeCount;
    private ArrayList<AdjNodes> adjacencyList;

    public DSAProject(){
        adjacencyList = new ArrayList<AdjNodes>();
        urlIDs = new HashMap<String, Integer>();
        nodeCount=0;
    }




    public class AdjNodes{
        private int nodeID;
        private ArrayList<Integer> adjacents;         //storing adjacent to parent

        public AdjNodes(int NodeID,){
            nodeID=NodeID;
            adjacents=new ArrayList<Integer> ();
        }

        public int getNodeID(){
            return nodeID;
        }

        public void add(int nodeID){
            adjacents.add(nodeID);
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
        boolean nodeExists = urlIDs.containsKey(urlFrom);
        if(!nodeExists){
            urlIDs.put(urlFrom,nodeCount);
            nodeCount++;
        }

        int currentNodeID = urlIDs.get(urlFrom);
        AdjNodes currentNode = adjacencyList.get(currentNodeID);
        int adjnodeID = urlIDs.get(urlTo);
        currentNode.add(adjnodeID);
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

    }

}