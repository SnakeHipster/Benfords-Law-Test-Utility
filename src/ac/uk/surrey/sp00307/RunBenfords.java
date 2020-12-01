package ac.uk.surrey.sp00307;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class used to store the methods used when running Benford's Law analysis
 * 
 * @author User
 *
 */
public class RunBenfords {
	// Holds the raw input list of data from the test CSV's as doubles
	static List<Double> inputList;
	// Holds the list of first significant digits used for the Benford's
	// Analysis
	static List<Integer> benfordList;
	// Hold the outputs of the test in string form
	static List<String> outputStrings;
	static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	// Method used to run the Benford's Analysis, is called from the
	// MainClass.java file
	public static List<String> run(int testNumber, int option) {
		inputList = new ArrayList<Double>();
		benfordList = new ArrayList<Integer>();
		outputStrings = new ArrayList<String>();
		readInData("test" + testNumber + ".csv");

		// Distinguishes between which method is being used to apply Benford's
		// Law on the dataset
		if (option == 0) {
			changeListToLeadingDigitList();
		} else if (option == 1) {
			changeListToLeadingDigitListWithSpreadFromAverage();
		} else if (option == 2) {
			changeListToLeadingDigitListWithAverageAndRange();
		}

		calculateNumericFrequencies();
		return outputStrings;
	}

	/**
	 * Method used to read in the data from the CSV files into an ArrayList of
	 * doubles
	 * 
	 * @param fileName
	 */
	private static void readInData(String fileName) {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			s = br.readLine();

			while (s != null) {
				inputList.add(Double.valueOf(s));
				s = br.readLine();
			}

			br.close();

		} catch (IOException ioe) {
			System.out.println("Error");
		}
	}

	/**
	 * Method used to convert the list of doubles of raw input data to a list of
	 * the first significant digits in integer form using the differencing
	 * technique
	 */
	private static void changeListToLeadingDigitList() {
		// Pre-processing data using differences in numbers
		System.out.println("Size: " + inputList.size());
		for (int i = 0; i < inputList.size() - 1; i++) {
			benfordList.add(leadingDigit(Math.abs(inputList.get(i) - inputList.get(i + 1))));
		}
	}

	/**
	 * Method used to convert the list of doubles of raw input data to a list of
	 * the first significant digits in integer form using the differencing from
	 * the average technique
	 */
	private static void changeListToLeadingDigitListWithSpreadFromAverage() {
		// Calculates the average
		double average = 0;
		for (int i = 0; i < inputList.size() - 1; i++)
			average = average + inputList.get(i);
		average = average / inputList.size();

		// Uses the difference from the mean as the digit to take the first
		// significant digit from
		System.out.println("Size: " + inputList.size() + " Mean: " + average);
		for (int i = 0; i < inputList.size() - 1; i++) {
			benfordList.add(leadingDigit(Math.abs(average - inputList.get(i))));
		}
	}

	/**
	 * Method used to convert the list of doubles of raw input data to a list of
	 * the first significant digits in integer form using the differencing from
	 * the average considering the range of the data technique
	 */
	private static void changeListToLeadingDigitListWithAverageAndRange() {
		// Calcualtes the average and the range
		double average = 0;
		double lowest = 0.0, highest = 0.0, range = 0.0;
		for (int i = 0; i < inputList.size() - 1; i++) {
			if (i == 0) {
				lowest = inputList.get(i);
				highest = inputList.get(i);
			}
			if (inputList.get(i) > highest)
				highest = inputList.get(i);
			if (inputList.get(i) < lowest)
				lowest = inputList.get(i);
			average = average + inputList.get(i);
		}
		average = average / inputList.size();
		range = highest - lowest;

		// Determines the size of each of the 9 bands to separate the data into
		// (9 bands above and below the average)
		double bandSize = range / 18;
		System.out.println("Size: " + inputList.size() + " Band: " + bandSize + " Range: " + range + " Mean: " + average
				+ " Highest: " + highest + " Lowest: " + lowest);

		// Divide each into the 9 bands dependent on how many band widths they
		// are from the mean
		for (int i = 0; i < inputList.size() - 1; i++) { //
			if (inputList.get(i) == highest || inputList.get(i) == lowest) {
				benfordList.add(9);
			} else {
				benfordList.add(leadingDigit(1 + Math.abs(average - inputList.get(i)) / bandSize));
			}
		}

	}

	/**
	 * Method used to obtaing the first significant digit of a double and return
	 * it as a integer
	 * 
	 * @param x
	 * @return
	 */
	private static Integer leadingDigit(Double x) {
		if (x == 0.0)
			return 1; // If for some reason the input is 0 then return a 1
		while (x >= 10) {
			x = x / 10;
		}
		while (x < 1) {
			x = x * 10;
		}

		return Integer.parseInt(Double.toString(x).substring(0, 1));
	}

	/**
	 * Method used to tally up the list of first significant bits and output the
	 * results to the console and the outputStrings list ready to print to the
	 * output CSV file
	 */
	private static void calculateNumericFrequencies() {
		// Clears the
		BenfordObject bObj = new BenfordObject();
		bObj.clearList();
		System.out.println("Length: " + benfordList.size());
		for (int i : benfordList)
			bObj.addDataPoint(i);

		System.out.println(bObj.getOneP());
		System.out.println(bObj.getTwoP());
		System.out.println(bObj.getThreeP());
		System.out.println(bObj.getFourP());
		System.out.println(bObj.getFiveP());
		System.out.println(bObj.getSixP());
		System.out.println(bObj.getSevenP());
		System.out.println(bObj.getEightP());
		System.out.println(bObj.getNineP());

		System.out.println("Euclidlean Distance: " + bObj.calcEuclidleanDistance());
		System.out.println("Pearsons Coefficient: " + bObj.calcPearsonsCoefficient());
		System.out.println("Total: " + bObj.getTotal());

		outputStrings.add(bObj.getOneP() + "," + bObj.getTwoP() + "," + bObj.getThreeP() + "," + bObj.getFourP() + ","
				+ bObj.getFiveP() + "," + bObj.getSixP() + "," + bObj.getSevenP() + "," + bObj.getEightP() + ","
				+ bObj.getNineP() + "," + bObj.calcEuclidleanDistance() + "," + bObj.calcPearsonsCoefficient());

	}
}
