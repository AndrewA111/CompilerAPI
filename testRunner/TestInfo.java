import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to create objects to store data on tests
 * @author Andrew
 *
 */
public class TestInfo {
	
	// name of test method
	String name;
	
	// description
	String description;

	// hint
	String hint;
	
	// pass/fail status
	boolean passed;
	
	// failure description
	String failureText;
	
	// no-parameter constructor for serialization
	public TestInfo() {
		
	}
	
	/*
	 * Constructor for instantiation
	 * 
	 *  Name set by parameter
	 *  
	 *  Failure conditions set to 'passing' by default, 
	 *  as listener only identifies failures and not passes
	 */
	public TestInfo(String name) {
		
		this.name = name;
		
		this.passed = true;
		
		this.failureText = "";

	}
	
	
	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty
	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
	
	@JsonProperty
	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	
	@JsonProperty
	public String getFailureText() {
		return failureText;
	}

	public void setFailureText(String failureText) {
		this.failureText = failureText;
	}
	
	public String toString() {
		
		String output = "";
		
		output += "Name: " + this.name + "\n";
		output += "Description: " + this.description + "\n";
		output += "Hint: " + this.hint + "\n";
		output += "Passed: " + this.passed + "\n";
		output += "Failure Text: " + this.failureText + "\n";
		
		return output;

	}
	

	

	
	

}
