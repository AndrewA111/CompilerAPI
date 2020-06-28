package CompileAPI.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmittedFiles {
	
	private FileDetails[] files;
	
	public SubmittedFiles() {
		
	}
	
	public SubmittedFiles(FileDetails[] files) {
		this.files = files;
	}
	
	@JsonProperty
	public FileDetails[] getFiles() {
		return files;
	}
}
