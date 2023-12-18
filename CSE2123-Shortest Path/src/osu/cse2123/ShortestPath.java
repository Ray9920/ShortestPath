package osu.cse2123;
import java.io.File;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;



/**
 *   A simulator program for shortest path simulation based on the list of destinations 
 *   The user will pick which file cities and distances input in the code and will pick a main city to compare with the other cities in that file to find the shortest path  
 *   
 *   @author Ryan Khalili
 *   @version 12/12/2022
 */

public class ShortestPath {

// paths.txt text file
	
	/** 
	 * 
	 * @return Putting all the methods together to return what the final product should look like
	 */
	public static void main(String[] args) throws FileNotFoundException{

		//Created a scanner to read the inputs of the user
		Scanner input = new Scanner(System.in);

		//Asking the user a filename with paths
		System.out.print("Enter a filename with paths: ");
		String file = input.nextLine();
		System.out.println();

		//Created a new map and associate it with the readpaths method 
		Map<String, List<Path>> map1 = readPaths(file);

		//Then displaying the paths in that file
		displayAdjacencyList(map1);
		System.out.println();

		//Then asking the user to pick a start city 
		System.out.print("Enter a start city (empty line to quit): ");
		String city = input.nextLine();
		System.out.println();

		//if the user enter's a city with a length more than one then enter the while loop
		while(city.length() > 0) {
			//Then a second map is created for the distances of the cities 
			Map<String, Double> map2 = findDistances(city,map1);
			//Then printing out the distance differences of the cities compare to the city you picked
			displayShortest(city,map2);

			//Then asking the user again which city he wants to do next if not enter nothing to leave the loop
			System.out.print("Enter a start city (empty line to quit): ");
			city = input.nextLine();
			System.out.println();
		}
		//Once out of the while loop print this message
		System.out.println("Goodbye!");
	}



	/** 
	 * 
	 * @return This method takes a start node name and the adjacency list that was created and returns a new Map of node names and distances. 
	 * It will calculate the differences from the original city to the other cities
	 */
	public static Map<String, Double> findDistances(String start, Map<String, List<Path>> adj_list){

		//Created an empty map to put the distances and put the start location in it with a distance of 0
		Map<String, Double> shortestDistances = new HashMap<String, Double>();
		shortestDistances.put(start, 0.0);

		//Then created an empty priority queue to store the paths that have been traversed
		PriorityQueue<Path> shortQueue= new PriorityQueue<Path>();
		//Then adding the starting location and distance to it
		shortQueue.add(new SimplePath(start, 0.0));

		//if the queue is not empty then enter the while loop
		while(!shortQueue.isEmpty()) {
			Path myPath = shortQueue.poll();
			double oldDistance = myPath.getCost();

			// iterating over the map with the shortest distances
			for(Path city : adj_list.get(myPath.getEndpoint())) {
				//Calculate the new distance to the city by adding the oldDistance to the cost to get there
				double newDistance = oldDistance + city.getCost();

				//if the city you pick in the shortestDistances map has not been to that endpoint add it to the shortestDistances map and short Queue
				if (!shortestDistances.containsKey(city.getEndpoint())) {
					shortestDistances.put(city.getEndpoint(), newDistance);
					shortQueue.add(new SimplePath(city.getEndpoint(), newDistance));
				}
			}
		}
		return shortestDistances;	
	}


	/**
	 * 
	 * @return returns a new Map of node names and distances. 
	 */
	public static void displayShortest(String start, Map<String, Double> shortest) {

		//Printing out the correct following heading for the method displayShortest
		System.out.println();
		System.out.println("Distances from " + start + " to each city:" );
		System.out.println("Dest. City     Distance");
		System.out.println("-------------- --------");

		//Then printing each key and value in the map
		for (Map.Entry<String, Double> entry : shortest.entrySet()) {
			String key = entry.getKey();
			Double distance = entry.getValue();

			//This prints it in the correct format
			System.out.printf("%-15s %7.2f\n", key, distance);
		}
		System.out.println();
	}


	/** 
	 * 
	 * @return Displays the entire adjacency list stored in the multimap that is passed in as a parameter. 
	 */ 
	public static void displayAdjacencyList(Map< String, List<Path> > map) {

		//Printing out the correct header for the displayAdjacenecyList method
		System.out.println("Start City     Paths" );
		System.out.println("-------------- ------------------------------");

		//Print each key and value in the map
		for (Map.Entry<String, List<Path>> entry : map.entrySet()) {
			String key = entry.getKey();
			List<Path> distance = entry.getValue();

			//Print the keys of the list in the right format
			System.out.printf("%-14s ", key);

			//Printing the values in the right format with the commas separating them
			for(int idx = 0; idx < distance.size(); idx++) {
				System.out.print(distance.get(idx));

				if(idx < distance.size() - 1) {
					System.out.print(", ");
				}
			}
			System.out.println();
		}
	}


	/**
	 * Takes a filename for a file containing paths list as an argument.  
	 *Creates an adjacency list by reading paths from the input file and organizing them in the multimap 
	 *Returns a map of paths
	 * 
	 * @param fname name of properly formatted path file to load data from
	 * @precond fname is a properly formatted file as given in the assignment
	 * @return a map of paths
	 */
	public static Map< String, List<Path> > readPaths(String fname) {

		//Created an empty TreeMap to input the file into
		Map<String, List<Path>> myMap = new TreeMap<String, List<Path>>() ;

		try {
			String nextline;
			File textFile = new File(fname);
			//Created a scanner to read the textFile
			Scanner read = new Scanner(textFile);

			//While loop to read each line in the file
			while(read.hasNextLine()) {
				nextline = read.nextLine();
				//Creating a split
				String [] split = nextline.split(",");
				//Assigning the cost with the correct element in the file
				double cost = Double.parseDouble(split[2]);

				//Then assigning path 1 with the correct element in the file
				Path path1 = new SimplePath(split[1], cost);
				//Then making a new Linked list for that path
				List<Path> firstPath = new LinkedList<Path>();
				//Assigning path 2 with the correct element in the file
				Path path2 = new SimplePath(split[0], cost);
				//Also creating another Linked list for that path
				List<Path> secondPath = new LinkedList<Path>();

				//If the map does not contain that city add it to the first path and put it in the map
				if(!myMap.containsKey(split[0])) {
					firstPath.add(path1);
					myMap.put(split[0], firstPath);
				}
				//If it is in the map then get the first city and add it to path1
				else {
					myMap.get(split[0]).add(path1);
				}

				//If the map does not contain that second city add it to the second path and put it in the map
				if(!myMap.containsKey(split[1])) {
					secondPath.add(path2);
					myMap.put(split[1], secondPath);
				}
				//If it is in the map then get the second city and add it to path2
				else {
					myMap.get(split[1]).add(path2);
				}
			}
			read.close();

		} catch (FileNotFoundException e) {
			System.out.print("Error");
		}
		return myMap;
	}
}