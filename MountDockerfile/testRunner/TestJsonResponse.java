import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to create response object for overall test results
 * @author Andrew
 *
 */
public class TestJsonResponse {
	
	// number of tests
	int numTests;
	
	// number of test failures
	int numFailed;
	
	// array of test information 
	TestInfo[] tests;
	
	// no-parameter constructor for serialization
	public TestJsonResponse() {
		
	}
	
	/**
	 * Constructor for instantiation
	 * 
	 * @param numTests
	 * @param numFailed
	 * @param tests
	 */
	public TestJsonResponse(int numTests,
							int numFailed,
							TestInfo[] tests) {
		this.numTests = numTests;
		this.numFailed = numFailed;
		this.tests = tests;
	}
	
	@JsonProperty
	public int getNumTests() {
		return numTests;
	}
	
	@JsonProperty
	public int getNumFailed() {
		return numFailed;
	}

	@JsonProperty
	public TestInfo[] getTests() {
		return tests;
	}
	
	
}
