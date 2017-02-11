package ca;

/**
 * Launches the simulation of the 1 dimension cellular automaton.
 *
 * @author Javier Cabero Guerra
 */
public class Main {

	public static void main(String[] args) {

		System.out.println("*** Starting 1 dimensional Cellular Automaton Simulation ***");

		// Rules specified by file
		String rulesFilename = "rules.properties";
		CA1D ca = new CA1D(rulesFilename);

		// Rules specified by array
//		int[] rules = new int[] {0, 1, 1, 0, 1, 1, 0, 0};
//		CA1D ca = new CA1D(rules);

		// Generate the topology
		ca.generate();
		
		// Print it
		System.out.println(ca.toString());
	}
}