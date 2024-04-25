import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Nabil El-Hage
 * @project 6
 */

public class TownGraphManager implements TownGraphManagerInterface{
	private Graph townGraph;
	public TownGraphManager() {
		townGraph = new Graph();
	}

	/** Adds a road to the graph
	 * 
	 * @param town1
	 * @param town2
	 * @param weight
	 * @param roadName
	 * 
	 * @return Boolean if the road was added
	 */
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town source = getTown(town1);
		Town dest = getTown(town2);
		
		if (source == null || dest == null) {
			return false;
		}
		if (townGraph.containsEdge(source, dest)) {
			return false;
		}
		townGraph.addEdge(source, dest, weight, roadName);
		
		return true;
	}

	/** Gets requested road
	 * 
	 * @param town1
	 * @param town2
	 * 
	 * @return road name
	 */
	@Override
	public String getRoad(String town1, String town2) {
		String roadResult = "";
		Town source = getTown(town1);
		Town dest = getTown(town2);
		
		for(Road road : townGraph.edgeSet()) {
			if (road.contains(source) && road.contains(dest)) {
				roadResult = road.getName();
			}
		}
		return roadResult;
	}
	

	
	/** Adds a town
	 * @param v
	 * @param Boolean if the town was created
	 */
	@Override
	public boolean addTown(String v) {
		return townGraph.addVertex(new Town(v));
	}

	/** Gets a town based off of its name
	 * @param String name
	 * 
	 * @return The town with the name requested
	 */
	@Override
	public Town getTown(String name) {
		Town townResult = null;
		for (Town town : townGraph.vertexSet()) {
			if (town.getName().equals(name)) {
				townResult = town;
			}
		}
		return townResult;
	}

	/** Checks if the graph contains a certain town
	 * @param v
	 * @return Boolean if town is in graph
	 */
	@Override
	public boolean containsTown(String v) {
		if (getTown(v) == null) {
			return false;
		}else{
			return true;
	}
	}

	/** Gets road between towns
	 * 
	 * @param town1
	 * @param town2
	 * 
	 * @return Boolean if the road exists
	 */
	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return this.townGraph.containsEdge(getTown(town1), getTown(town2));
	}

	/** Gets a list of roads in a sorted order
	 * 
	 * @return List of roads in sorted order
	 */
	@Override
	public ArrayList<String> allRoads() {
		ArrayList<String> roadNames = new ArrayList<>();
		for (Road road: townGraph.edgeSet()) {
			roadNames.add(road.getName());
		}
		
		roadNames.sort(String.CASE_INSENSITIVE_ORDER);
		return roadNames;
	}
	
	
	public Road result(String town1, String town2) {
		Road roadResult = null;
		Town source = getTown(town1);
		Town dest = getTown(town2);
		
		for(Road road : townGraph.edgeSet())
			if (road.contains(source) && road.contains(dest))
				roadResult = road;
		
		return roadResult;
	}

	/** Deletes a road connection between two towns
	 * 
	 * @param town1
	 * @param town2
	 * 
	 * @return True if deleted, false otherwise
	 */
	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Road roadResult = result(town1, town2);
		townGraph.removeEdge(getTown(town1), getTown(town2), roadResult.getWeight(), roadResult.getName());
		return true;
	}

	/** Deletes a town from the graph
	 * @param v
	 * @return True town was removed, false if not
	 */
	@Override
	public boolean deleteTown(String v) {
		return townGraph.removeVertex(getTown(v));
	}

	/** Gets a list of all towns in sorted order
	 * @return List of all towns in sorted order
	 */
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<String> townNames = new ArrayList<>();
		for (Town town : townGraph.vertexSet()) {
			townNames.add(town.getName());
		}
		
		townNames.sort(String.CASE_INSENSITIVE_ORDER);
		return townNames;
	}

	/** Gets the shortest path from town1 and town2
	 * 
	 * @param town1
	 * @param town2
	 * 
	 * @return shortest path
	 */
	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		return townGraph.shortestPath(getTown(town1), getTown(town2));
	}

	/** Makes a graph from a file
	 * 
	 * @param selectedFile 
	 * @throws FileNotFoundException
	 */
	public void populateTownGraph(File selectedFile) throws FileNotFoundException{
		List<String> inList = new ArrayList<>();
		
		if (!selectedFile.exists())
			throw new FileNotFoundException();
		
		Scanner inFile = new Scanner(selectedFile);
		
		while (inFile.hasNextLine()) {
			inList.add(inFile.nextLine());
		}
		
		for (String line : inList) {
			String[] currentLine = line.split(";");
			int commaIndex = currentLine[0].indexOf(",");
			String roadName = currentLine[0].substring(0,commaIndex);
			String weight = currentLine[0].substring(commaIndex+1,currentLine[0].length());
			String source = currentLine[1];
			String destination = currentLine[2];
			
			addTown(source);
			addTown(destination);
			
			addRoad(source, destination, Integer.parseInt(weight), roadName);
		}
		
		inFile.close();
	}

}