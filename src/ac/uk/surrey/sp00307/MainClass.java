package ac.uk.surrey.sp00307;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to run the Benford's Law Tests
 * 
 * @author Simon Page
 *
 */
public class MainClass {
	// Holds the results ready to be printed to Output CSV files
	static List<String> outputStrings;

	/**
	 * Main method used to start the program
	 * 
	 * Input [1] to start tests, Input [2] to exit
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Running Benford's Law checks");
		int noOfTests = searchForTests();
		System.out.println(" Found " + noOfTests + " tests");
		System.out.println(" Proceed? [1] Exit? [2]");
		int benOrXbar = sc.nextInt();
		if (benOrXbar == 1) {
			sc.close();
			System.out.println("\nStarting Benford's Tests");

			// For statement to run the tests with each of the three ways of
			// applying Benford's law
			for (int j = 0; j < 3; j++) {
				outputStrings = new ArrayList<String>();

				// For method used to conduct all the tests that it finds in its
				// directory
				for (int i = 1; i < noOfTests + 1; i++) {
					System.out.println("\nTest no: " + i);
					outputStrings.add(i + "," + RunBenfords.run(i, j));
					outputResultsToCSV(j);

				}
			}
		} else {
			System.out.println("\nQuitting");
			sc.close();
			System.exit(0);
		}

	}

	/**
	 * Method used to output the results to CSV files once finished
	 * 
	 * @param option
	 */
	public static void outputResultsToCSV(int option) {

		// Creates a CSV file for each of the three methods being tested for
		// Benford's Law
		try {
			String title = "Output_TIMESTAMP.csv";
			if (option == 0) {
				title = "Output_Differencing.csv";
			} else if (option == 1) {
				title = "Output_DifferencingFromMean.csv";
			} else if (option == 2) {
				title = "Output_DifferencingInProportionToMean.csv";
			}
			PrintWriter writer = new PrintWriter(title, "UTF-8");

			// Writes the column titles for the results
			writer.println("Test No,1's,2's,3's,4's,5's,6's,7's,8's,9's,Euclidean Distance, Pearsons Correlation");
			for (String i : outputStrings) {
				writer.println(i.replace("[", "").replace("]", ""));

			}

			writer.close();
		} catch (IOException e) {

		}
	}

	/**
	 * Method used to determine how many tests are needed to be conducted
	 * 
	 * @return
	 */
	public static int searchForTests() {
		int count = 0;
		boolean stop = false;
		while (stop == false) {
			count++;
			File temp = new File("test" + (count + 1) + ".csv");
			if (!temp.exists())
				stop = true;
		}
		return count;
	}

}
