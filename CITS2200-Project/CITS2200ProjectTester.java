import java.io.*;
import java.util.*;

public class CITS2200ProjectTester {
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
		String pathToGraphFile = "./example_graph2.txt";
		// Create an instance of your implementation.
		CITS2200Project proj = new MyCITS2200Project();
		// Load the graph into the project.
		loadGraph(proj, pathToGraphFile);

		// Write your own tests!

		//change it to test
		String urlFrom = "1";
		String urlTo = "3";


		int q1result = proj.getShortestPath(urlFrom, urlTo);

		
		System.out.println("\n---------------------------------- Question 1: ---------------------------------------------------");
		System.out.println("-------------------- min number of traversed vertex from given url to finish url------------------\n");

		System.out.println("Shortest path from" + urlFrom + " to " + urlTo + " is " + q1result + "\n");
		

		System.out.println("\n---------------------------------- Question 2: ---------------------------------------------------");
		System.out.println("--------------------- finds hamiltonian path using heldkarp algorithm. ---------------------------\n");
		String[] hamiltonian = proj.getHamiltonianPath();




		System.out.println("\n---------------------------------- Question 3: ---------------------------------------------------");
		System.out.println("--------------------- finds every strongly connected component of pages. -------------------------\n");
		String[][] scc = proj.getStronglyConnectedComponents();
		for (String[] arr : scc) {
			System.out.println("SCC:\t");
			for (String value : arr) {
				System.out.print(value + "\t|\t");
				
			}
			System.out.println("\n"); // Prints a new line after each outer array
		}


		System.out.println("\n---------------------------------- Question 4: ---------------------------------------------------");
		System.out.println("--------------------------------------- get centers of a graph -----------------------------------\n");

		String[] centers = proj.getCenters();
		for (String s : centers){
			System.out.print (s + "\t");
		}
		System.out.println("");


	}

}