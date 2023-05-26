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

		long start_q1 = System.nanoTime();
		int q1result = proj.getShortestPath(urlFrom, urlTo);
		long end_q1 = System.nanoTime();

		
		System.out.println("\n---------------------------------- Question 1: ---------------------------------------------------");
		System.out.println("-------------------- min number of traversed vertex from given url to finish url------------------\n");

		System.out.println("Shortest path from" + urlFrom + " to " + urlTo + " is " + q1result + "\n");
		System.out.println("Elapsed Time in nano seconds: "+ (end_q1-start_q1));

		System.out.println("\n---------------------------------- Question 2: ---------------------------------------------------");
		System.out.println("--------------------- finds the hameltonian path using backtracking. -----------------------------\n");
		long start_q2 = System.nanoTime();
		String[] hameltonian = proj.getHamiltonianPath();
		long end_q2 = System.nanoTime();
		for (String s : hameltonian){
			System.out.print (s + "\t");
		}
		System.out.println("");

		System.out.println("Elapsed Time in nano seconds: "+ (end_q2-start_q2));
		

		System.out.println("\n---------------------------------- Question 3: ---------------------------------------------------");
		System.out.println("--------------------- finds every strongly connected component of pages. -------------------------\n");
		long start_q3 = System.nanoTime();
		String[][] scc = proj.getStronglyConnectedComponents();
		long end_q3 = System.nanoTime();
		for (String[] arr : scc) {
			System.out.println("SCC:\t");
			for (String value : arr) {
				System.out.print(value + "\t|\t");
				
			}
			System.out.println("\n"); // Prints a new line after each outer array
		}
		System.out.println("Elapsed Time in nano seconds: "+ (end_q3-start_q3));


		System.out.println("\n---------------------------------- Question 4: ---------------------------------------------------");
		System.out.println("--------------------------------------- get centers of a graph -----------------------------------\n");
		long start_q4 = System.nanoTime();
		String[] centers = proj.getCenters();
		long end_q4 = System.nanoTime();
		for (String s : centers){
			System.out.print (s + "\t");
		}
		System.out.println("");
		System.out.println("Elapsed Time in nano seconds: "+ (end_q4-start_q4));

	}

}