package osu.cse2123;

/**
 * An interface for paths in shortest path project
 * 
 * @author Zina Pichkar
 * @version 12112021
 *
 */

public interface Path {
	
	/**
	 * @return the name of the endpoint for this path
	 */
	public String getEndpoint();  
	
	
	/**
	 * @return the cost for this path
	 */
	public double getCost();




	

}
