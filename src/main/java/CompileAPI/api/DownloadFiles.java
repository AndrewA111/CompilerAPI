package CompileAPI.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used to store file information for 
 * downloading questions
 * @author andrew
 *
 */
public class DownloadFiles {
	
	// question info file
	private FileDetails infoFile;
	
	// question decription file
	private FileDetails descFile;
	
	// java files
	private FileDetails[] questionFiles;
	
	// empty constructor for JSONifying
	public DownloadFiles() {
		
	}
	
	public DownloadFiles(FileDetails infoFile,
							FileDetails descFile,
							FileDetails[] questionFile) {
		this.infoFile = infoFile;
		this.descFile = descFile;
		this.questionFiles = questionFile;
	}
	
	@JsonProperty
	public FileDetails getInfoFile() {
		return infoFile;
	}
	
	@JsonProperty
	public FileDetails getDescFile() {
		return descFile;
	}
	
	@JsonProperty
	public FileDetails[] getQuestionFiles() {
		return questionFiles;
	}
}
