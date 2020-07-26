package CompileAPI.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to combine test and error outputs to return to client (as JSON)
 * @author Andrew
 *
 */
public class ResultsMessage {
	
	// output stream
	String output;
	
	// error stream
	String errors;
	
	public ResultsMessage() {
		
	}
	
	public ResultsMessage(String output, String errors) {
		this.errors = errors;
		this.output = output;
	}
	@JsonProperty
	public String getErrors() {
		return errors;
	}
	@JsonProperty
	public String getOutput() {
		return output;
	}
	
	
}
