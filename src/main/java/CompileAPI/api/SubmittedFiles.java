package CompileAPI.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used to store details of files for 
 * transmitting as JSON
 * @author andrew
 *
 */
public class SubmittedFiles {
	
	// array of files
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
