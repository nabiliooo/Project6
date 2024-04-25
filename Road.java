/**
 * @author Nabil El-Hage
 * @project 6
 */

public class Road implements Comparable<Road>{
	private Town source;
	private Town destination;
	private String name;
	private int weight;
	
	/**
	 * @param Town source
	 * @param Town destination
	 * @param Int weight
	 * @param String name
	 * @return this
	 */
	public Road(Town sorce, Town dest, int weight, String name){
		this.source = sorce;
		this.destination = dest;
		this.weight = weight;
		this.name = name;
	}
	
	/**
	 * @param Town source
	 * @param Town destination
	 * @param String name
	 */
	public Road(Town sorce, Town dest, String name){
		this.source = sorce;
		this.destination = dest;
		this.weight = 1;
		this.name = name;
	}
	
	/** Gets the source of the road
	 * 
	 * @return Town source
	 */
	public Town getSource(){
		return source;
	}
	
	
	
	/** Gets the destination of the road
	 * 
	 * @return Town destination
	 */
	public Town getDestination(){
		return destination;
	}
	
	
	/** Gets the weight of the road
	 * 
	 * @return Int weight
	 */
	public int getWeight(){
		return weight;
	}
	
	
	/** Gets the name of the road
	 * 
	 * @return String name
	 */
	public String getName(){
		return name;
	}
	
	/** Checks if the road contains a town
	 * 
	 * @param Town town
	 * @return Boolean contains town
	 */
	public boolean contains(Town town){
		if (town == null)
			return false;
		
		if (source.equals(town)){
			return true;
		}else if (destination.equals(town)){
			return true;
		}
		return false;
	}

	/** Compares two roads by checking the source and destination
	 * 
	 * @return boolean if equal or not
	 */
	@Override
	public boolean equals(Object r){
		Road newR = (Road) r;
		if (newR.destination == destination && newR.source == source){
			return true;
			
		}else if(newR.destination == source && newR.source == destination){
			return true;
			
		}
		return false;
	}
	
	/** Describes the road
	 * 
	 * @return A description of the road
	 */
	@Override
	public String toString(){
		return this.name; 
	}

	/** Compares the weight of two roads
	 * 
	 * @param Road r
	 * @return Int compare
	 */
	@Override
	public int compareTo(Road r){
		return weight - r.getWeight();
	}
}