package ca;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Generates the cellular automaton topology based
 * on the rules provided.
 * </br></br>
 * The rules must be an array of eight integers with
 * 0 or 1 as values.
 *
 * @author Javier Cabero Guerra
 */
public class CA1D {

	public final int HEIGHT = 32;
	public final int WIDTH = 64;

	private int[] rules = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
	private int[][] resultMatrix = null;

	/** Uses the rules specified by parameter. */
	public CA1D(int[] rules) {
		this.rules = rules;
	}
	
	/** Loads the rules from file.
	 *  </br></br>
	 *  Format example (without quotes): "rules=0,1,0,1,0,1"
	 */
	public CA1D(String rulesFilename) {

		try {
			InputStream is = new FileInputStream(rulesFilename);
			Properties prop = new Properties();
			prop.load(is);
			String[] rulesRead = prop.getProperty("rules").split(",");
			rules = new int[8];
			for (int i=0; i<8; i++) {
				rules[i] = Integer.parseInt(rulesRead[i]);
			}
		} catch (Exception e) {
			System.err.println("Error reading the rules file.");
			e.printStackTrace();
		}
	}
	
	/** Generates the topology based on the rules */
	public void generate() {
		
		System.out.println("Generating automaton...");

		// Initialize matrix
		resultMatrix = new int[HEIGHT][WIDTH];
		
		// The seed is an active cell in the middle of the first row
		resultMatrix[0][WIDTH/2] = 1;
		
		for (int i = 1; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				int aux1 = 1;
				int aux2 = 1;
				if (j == 0) {
					aux1 = 0;
				}
				if (j == WIDTH - 1) {
					aux2 = 0;
				}

				int ruleToApply = 4 * resultMatrix[i - 1][j - aux1]
								+ 2 * resultMatrix[i - 1][j]
								+ resultMatrix[i - 1][j + aux2];
				resultMatrix[i][j] = rules[ruleToApply];
			}
		}
		
		System.out.println("Generation completed!");
	}
	
	/** Prints the current rules */
	public void printRules() {
		
		// Check if rules were correctly initialized
		if (rules == null) {
			System.out.println("No rules are set");
			return;
		}
		
		System.out.print("Rules: ");
		for (int i = 0; i < rules.length; i++) {
			System.out.print(rules[i] + " ");
		}
		System.out.println();
	}

	/** Returns the generated matrix */
	public int[][] getMatrix() {
		return resultMatrix;
	}

	/** Creates a String representation of the computed automaton */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder("Resulting topology:\n");
		
		// Check if the automaton was computed
		if (resultMatrix == null) {
			System.out.println("Can't create automaton string representation. "
							 + "The automaton was not generated.");
			return "";
		}
		
		for (int j = 0; j < WIDTH+2; j++) {
			sb.append("_");
		}
		sb.append('\n');
		for (int i = 0; i < HEIGHT; i++) {
			sb.append("|");
			for (int j = 0; j < WIDTH; j++) {
				if (resultMatrix[i][j] == 1) {
					sb.append("X");
				} else {
					sb.append(" ");
				}
			}
			sb.append("|");
			sb.append('\n');
		}
		for (int j = 0; j < WIDTH+2; j++) {
			sb.append("_");
		}
		
		return sb.toString();
	}
}