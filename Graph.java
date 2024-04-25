import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Nabil El-Hage
 * @project 6
 */

public class Graph implements GraphInterface<Town,Road>{
	private Set<Town> towns;
	private Set<Road> roads;
	private Set<Town> visited;
	private Set<Town> unvisited;

	private Map<Town, Town> previous;
	private Map<Town, Integer> distance;
	
	
	public Graph(){
		towns = new HashSet<>();
		roads = new HashSet<>();
		visited = new HashSet<>();
		unvisited = new HashSet<>();
		distance = new HashMap<>();
		previous = new HashMap<>();
	}

	/** Gets the edge connecting two towns
	 * 
	 * @param sorceVer
	 * @param destVer
	 * @return result road, or null
	 */
	@Override
	public Road getEdge(Town sorceVer, Town destVer) {
		for (Road road : roads) {
			if (road.contains(sorceVer) && road.contains(destVer)) {
				return road;
			}
		}
		
		return null;
	}

	/** Adds an edge between two towns
	 * 
	 * @param Town sorceVer
	 * @param Town destVer
	 * @param int weight
	 * @param String name
	 * 
	 * @return The edge added to the graph
	 */
	@Override
	public Road addEdge(Town sorceVer, Town destVer, int weight, String name) {
		Road newRoad = new Road(sorceVer, destVer, weight, name);
		roads.add(newRoad);
		return newRoad;
	}

	/** Adds a town vertex
	 * 
	 * @param Town v
	 * @return Boolean
	 */
	@Override
	public boolean addVertex(Town v) {
		for (Town town : towns) {
			if(town.equals(v)) {
				return false;
			}
		}
		towns.add(v);
		return true;
	}

	/** Checks if an edge exists
	 * 
	 * @param sorceVer
	 * @param destVer
	 * 
	 * @return True if a road exists with both towns, false if not
	 */
	@Override
	public boolean containsEdge(Town sorceVer, Town destVer) {
		for (Road road : roads){
			if (road.contains(sorceVer) && road.contains(destVer)) {
				return true;
			}
		}
		return false;
	}

	/** Checks if a vertex is on the graph
	 * @param v
	 * @return True if a vertex is on the graph, false if otherwise
	 */
	@Override
	public boolean containsVertex(Town v) {
		for (Town town : towns) {
			if (town.equals(v)){
				return true;
			}
		}
		return false;
	}

	/** Gets all edges from graph
	 *  
	 * @return Set<Road>
	 */
	@Override
	public Set<Road> edgeSet() {
		Set<Road> copyOfRoads = new HashSet<>();
		
		for(Road road: roads){
			copyOfRoads.add(road);
		}
		
		return copyOfRoads;
	}

	/** Gets edges of town
	 * @param vertex
	 */
	@Override
	public Set<Road> edgesOf(Town vertex) {
		Set<Road> containsTown = new HashSet<>();
		for (Road road : roads){
			if (road.contains(vertex)) {
				containsTown.add(road);
			}
		}
		
		return containsTown;
	}

	/** Removes road between two towns
	 * 
	 * @param sorceVer
	 * @param destVer 
	 * @param weight 
	 * @param name
	 * 
	 * @return road removed
	 */
	@Override
	public Road removeEdge(Town sorceVer, Town destVer, int weight, String name) {
		Road roadRemoved = null;
		for(Road road : roads) {
			if (road.contains(sorceVer) && road.contains(destVer)) {
				if (road.getWeight() == weight && road.getName().equals(name)) {
					roadRemoved = road;
					roads.remove(road);
					break;
				}
			}
		}
		
		return roadRemoved;
	}
	
	/** Removes all roads connected to a town
	 * 
	 * @param v
	 */
	private void removeConnectedRoads(Town v) {
		Set<Road> connectedRoads = edgesOf(v);
		
		for (Road road : connectedRoads) {
			removeEdge(road.getSource(), road.getDestination(), road.getWeight(), road.getName());
		}
	}

	/** Removes a town from the graph
	 * 
	 * @param Town v
	 * @return Boolean if removed
	 */
	@Override
	public boolean removeVertex(Town v) {
		towns.remove(v);
		removeConnectedRoads(v);
		return true;
	}

	/** Gets a set of towns on the graph
	 * 
	 * @return Set<Town> vertices
	 */
	@Override
	public Set<Town> vertexSet() {
		Set<Town> copyOfTowns = new HashSet<>();
		
		for (Town town : towns){
			copyOfTowns.add(town);
		}
		
		return copyOfTowns;
	}

	/** Gets the shortest path from the source to the destination
	 * 
	 * @param sorceVer
	 * @param destVer
	 */
	@Override
	public ArrayList<String> shortestPath(Town sorceVer, Town destVer) {
		ArrayList<String> path = new ArrayList<>();
		dijkstraShortestPath(sorceVer);
		Town previousTown = destVer;
		
		while(previousTown != null){
			Town currentTown = previousTown;
			previousTown = previous.get(previousTown);
			Road roadPath = getEdge(currentTown, previousTown);
			if (previousTown != null) {
				path.add(previousTown.getName() + " via " + roadPath.getName() + " to " + currentTown.getName() + " " + roadPath.getWeight() + " mi");
				}
			}
		Collections.reverse(path);
		return path;
	}

	/** Creates shortest pathways from source to destination
	 * 
	 * @param sorceVer
	 */
	@Override
	public void dijkstraShortestPath(Town sorceVer) {
		for (Town town : towns) {
			distance.put(town, Integer.MAX_VALUE);
			previous.put(town, null);
			unvisited.add(town);
		}
		distance.put(sorceVer, 0);
		
		while (!unvisited.isEmpty()) {
			Town nearestTown = getUnvisitedTown();
			unvisited.remove(nearestTown);
			Set<Town> neighbors = unvisitedTown(nearestTown);
			
			for (Town neighbor : neighbors) {
				int netWeight = distance.get(nearestTown) + getEdge(nearestTown, neighbor).getWeight();
				
				if(netWeight < distance.get(neighbor)) {
					distance.put(neighbor, netWeight);
					previous.put(neighbor, nearestTown);
				}
			}
		}
	}
	
	private Set<Town> unvisitedTown(Town town) {
		Set<Town> unvisitedNeighbors = new HashSet<>();
		
		for (Road road : edgesOf(town)) {
			Town neighbor = road.getSource() == town ? road.getDestination() : road.getSource();
			if (unvisited.contains(neighbor) && !visited.contains(neighbor))
				unvisitedNeighbors.add(neighbor);
		}
		return unvisitedNeighbors;
	}

	private Town getUnvisitedTown() {
		int lowestCost = Integer.MAX_VALUE;
		Town lowestTown = null;
		
		for (Town town : unvisited) {
			if (distance.get(town) <= lowestCost) {
				lowestCost = distance.get(town);
				lowestTown = town;
			}
		}
		
		return lowestTown;
	}
}