package ac.uk.surrey.sp00307;

/**
 * Class used to hold and store the information that is gathered during the
 * process of the Benford's law analysis
 * 
 * @author Simon Page
 *
 */
public class BenfordObject {
	// Used to store the occurrence of each digit in the form of a tally
	private int one = 0, two = 0, three = 0, four = 0, five = 0, six = 0, seven = 0, eight = 0, nine = 0;
	// Stores the percentage occurrence of each digit in True Benfor'ds
	// distribution for comparing checks
	static final private double TRUE_ONE = 30.1, TRUE_TWO = 17.6, TRUE_THREE = 12.5, TRUE_FOUR = 9.7, TRUE_FIVE = 7.9,
			TRUE_SIX = 6.7, TRUE_SEVEN = 5.8, TRUE_EIGHT = 5.1, TRUE_NINE = 4.6;
	// Stores the total number of numbers used in the Benford's Data
	private double total = 0;

	/**
	 * Default Constructor
	 */
	BenfordObject() {

	}

	public int getOne() {
		return one;
	}

	public int getTwo() {
		return two;
	}

	public int getThree() {
		return three;
	}

	public int getFour() {
		return four;
	}

	public int getFive() {
		return five;
	}

	public int getSix() {
		return six;
	}

	public int getSeven() {
		return seven;
	}

	public int getEight() {
		return eight;
	}

	public int getNine() {
		return nine;
	}

	public double getTotal() {
		return this.total;
	}

	public double getOneP() {
		return one / this.total * 100;
	}

	public double getTwoP() {
		return two / this.total * 100;
	}

	public double getThreeP() {
		return three / this.total * 100;
	}

	public double getFourP() {
		return four / this.total * 100;
	}

	public double getFiveP() {
		return five / this.total * 100;
	}

	public double getSixP() {
		return six / this.total * 100;
	}

	public double getSevenP() {
		return seven / this.total * 100;
	}

	public double getEightP() {
		return eight / this.total * 100;
	}

	public double getNineP() {
		return nine / this.total * 100;
	}

	/**
	 * Method used to add a datapoint to one of the 9 digit tallys
	 * 
	 * @param in
	 */
	public void addDataPoint(int in) {
		switch (in) {
		case 1:
			this.one++;
			break;

		case 2:
			this.two++;
			break;

		case 3:
			this.three++;
			break;

		case 4:
			this.four++;
			break;

		case 5:
			this.five++;
			break;

		case 6:
			this.six++;
			break;

		case 7:
			this.seven++;
			break;

		case 8:
			this.eight++;
			break;

		case 9:
			this.nine++;
			break;
		}
		this.total++;

	}

	/**
	 * Method to calculate Euclidean distance between the True Benford's
	 * Distribution and the input data
	 * 
	 * @return
	 */
	public double calcEuclidleanDistance() {
		double total = 0.0;

		total += Math.pow(Math.abs(TRUE_ONE - this.getOneP()), 2);
		total += Math.pow(Math.abs(TRUE_TWO - this.getTwoP()), 2);
		total += Math.pow(Math.abs(TRUE_THREE - this.getThreeP()), 2);
		total += Math.pow(Math.abs(TRUE_FOUR - this.getFourP()), 2);
		total += Math.pow(Math.abs(TRUE_FIVE - this.getFiveP()), 2);
		total += Math.pow(Math.abs(TRUE_SIX - this.getSixP()), 2);
		total += Math.pow(Math.abs(TRUE_SEVEN - this.getSevenP()), 2);
		total += Math.pow(Math.abs(TRUE_EIGHT - this.getEightP()), 2);
		total += Math.pow(Math.abs(TRUE_NINE - this.getNineP()), 2);

		return Math.sqrt(total);
	}

	/**
	 * Method to calculate Pearsons Coefficient Correlation between the True
	 * Benford's Distribution and the input data
	 * 
	 * @return
	 */
	public double calcPearsonsCoefficient() {
		double N = 9, sumXY = 0.0, sumX = 0.0, sumY = 0.0, sumXsq = 0.0, sumYsq = 0.0;

		sumXY += TRUE_ONE * this.getOneP();
		sumXY += TRUE_TWO * this.getTwoP();
		sumXY += TRUE_THREE * this.getThreeP();
		sumXY += TRUE_FOUR * this.getFourP();
		sumXY += TRUE_FIVE * this.getFiveP();
		sumXY += TRUE_SIX * this.getSixP();
		sumXY += TRUE_SEVEN * this.getSevenP();
		sumXY += TRUE_EIGHT * this.getEightP();
		sumXY += TRUE_NINE * this.getNineP();

		sumX += TRUE_ONE;
		sumX += TRUE_TWO;
		sumX += TRUE_THREE;
		sumX += TRUE_FOUR;
		sumX += TRUE_FIVE;
		sumX += TRUE_SIX;
		sumX += TRUE_SEVEN;
		sumX += TRUE_EIGHT;
		sumX += TRUE_NINE;

		sumY += this.getOneP();
		sumY += this.getTwoP();
		sumY += this.getThreeP();
		sumY += this.getFourP();
		sumY += this.getFiveP();
		sumY += this.getSixP();
		sumY += this.getSevenP();
		sumY += this.getEightP();
		sumY += this.getNineP();

		sumXsq += TRUE_ONE * TRUE_ONE;
		sumXsq += TRUE_TWO * TRUE_TWO;
		sumXsq += TRUE_THREE * TRUE_THREE;
		sumXsq += TRUE_FOUR * TRUE_FOUR;
		sumXsq += TRUE_FIVE * TRUE_FIVE;
		sumXsq += TRUE_SIX * TRUE_SIX;
		sumXsq += TRUE_SEVEN * TRUE_SEVEN;
		sumXsq += TRUE_EIGHT * TRUE_EIGHT;
		sumXsq += TRUE_NINE * TRUE_NINE;

		sumYsq += this.getOneP() * this.getOneP();
		sumYsq += this.getTwoP() * this.getTwoP();
		sumYsq += this.getThreeP() * this.getThreeP();
		sumYsq += this.getFourP() * this.getFourP();
		sumYsq += this.getFiveP() * this.getFiveP();
		sumYsq += this.getSixP() * this.getSixP();
		sumYsq += this.getSevenP() * this.getSevenP();
		sumYsq += this.getEightP() * this.getEightP();
		sumYsq += this.getNineP() * this.getNineP();

		double total = ((N * sumXY) - (sumX * sumY))
				/ Math.sqrt(((N * sumXsq) - (sumX * sumX)) * ((N * sumYsq) - (sumY * sumY)));

		return total;
	}

	/**
	 * Method used to reset the digit tallys to 0
	 */
	public void clearList() {
		this.one = 0;
		this.two = 0;
		this.three = 0;
		this.four = 0;
		this.five = 0;
		this.six = 0;
		this.seven = 0;
		this.eight = 0;
		this.nine = 0;
		this.total = 0;
	}
}
