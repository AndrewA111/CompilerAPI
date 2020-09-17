/**
 * Class for object to log metadata on tests
 * @author Andrew
 *
 */
public class TestMetadata {
	
	// number of tests
	private int numTests;
	
	// number of test failures
	private int numFailed;
	
	/**
	 * Constructor
	 */
	public TestMetadata() {
		this.numTests = 0;
		this.numFailed = 0;
	}
	
	// increment test count
	public void incrementNumTests() {
		numTests++;
	}
	
	// increment failure count
	public void incrementNumFailed() {
		numFailed++;
	}
	
	/*
	 * Getters
	 */
	public int getNumTests() {
		return numTests;
	}

	public int getNumFailed() {
		return numFailed;
	}
}
