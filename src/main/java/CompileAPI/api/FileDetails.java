package CompileAPI.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileDetails {
	String name;
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
