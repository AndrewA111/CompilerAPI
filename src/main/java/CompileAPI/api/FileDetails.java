package CompileAPI.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used to store a single file's details as string 
 * for conversion to JSON
 * @author andrew
 *
 */
public class FileDetails {
	
	// name of file
	String name;
	
	// content as String
	String content;
	
	public FileDetails() {
		
	}
	
	public FileDetails(String name, String content) {
		this.name = name;
		this.content = content;
	}
	
	@JsonProperty
	public String getName() {
		return name;
	}
	
	@JsonProperty
	public String getContent() {
		return content;
	}

}
