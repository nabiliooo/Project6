/**
 * @author Nabil El-Hage
 * @project 6
 */

public class Town implements Comparable<Town>{
	private String name;
	
	/** Gives town a name 
	 * @param String name
	 * @return this
	 */
	public Town(String name) {
		this.name = name;
	}
	
	/** Copies name of another town 
	 * @param Town t
	 */
	public Town(Town t) {
		name = t.getName();
	}
	
	/** Compares town by scanning names
	 * @return true if the names are the same; false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		return ((Town) obj).getName().equals(name);
	}
	
	/** Gets name of town
	 * @return String name
	 */
	public String getName() {
		return this.name;
	}
	
	/** Hashcode of the town
	 * @return Int hashCode
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
	/** Sets name toString
	 *  @return String name
	 */
	public String toString() {
		return this.getName();
	}

	/** Compares the two towns
	 * @param Town t
	 * @return comparison
	 */
	@Override
	public int compareTo(Town t) {
		return this.name.compareTo(t.getName());
	}
}