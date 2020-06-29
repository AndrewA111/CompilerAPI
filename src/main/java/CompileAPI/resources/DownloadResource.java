package CompileAPI.resources;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import CompileAPI.api.DownloadFiles;
import CompileAPI.api.FileDetails;

@Path("{question}/download")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 * Resource to allow client to download question files
 * 
 * question path parameter allows question to be selected
 * 
 * @author andrew
 *
 */
public class DownloadResource {
	
	// filters for selecting files
	private static FileFilter txtFilter = new ExtensionFilter(".txt");
	private static FileFilter mdFilter = new ExtensionFilter(".md");
	private static FileFilter javaFilter = new ExtensionFilter(".java");
	
	@GET
	public String download(@PathParam("question") String question) {
		
		// get project root dir
		String currDir = System.getProperty("user.dir");
		
		// get question location
		String questDir = currDir + "/questions/" + question + "/dl/";
		
		// get all files in folder
        File files = new File(questDir);
        
        // Get question info file
        File infoFile = files.listFiles(txtFilter)[0];
        String infoString = fileReader(infoFile);
        FileDetails infoDetails = new FileDetails(infoFile.getName(), infoString);
        
        // get description file
        File descriptionFile = files.listFiles(mdFilter)[0];
        String descriptionString = fileReader(descriptionFile);
        FileDetails descriptionDetails = new FileDetails(descriptionFile.getName(), descriptionString);
        
        
        // get java files
        File[] javaFiles = files.listFiles(javaFilter);
        FileDetails[] javaDetails = new FileDetails[javaFiles.length];
        
        
        for(int i = 0; i < javaFiles.length; i++) {
        	String javaString = fileReader(javaFiles[i]);
        	javaDetails[i] = new FileDetails(javaFiles[i].getName(), javaString);
        }
        
        // create POJO with data to send
        DownloadFiles downloadFiles = new DownloadFiles(
					        				infoDetails,
					        				descriptionDetails,
					        				javaDetails);
        
        ObjectMapper om = new ObjectMapper();
        
        try {
        	// map to json
			String jsonOutput = om.writeValueAsString(downloadFiles);
			
			// return json string to client
			return jsonOutput;
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Filter for selecting files with passed extension
	 * @author andrew
	 *
	 */
	private static class ExtensionFilter implements FileFilter{
		
		private String extension;
		
		/**
		 * Constructor
		 * @param ext extension for filtering
		 */
		protected ExtensionFilter(String ext) {
			this.extension = ext;
		}

		@Override
		public boolean accept(File file) {
			if(file.getName().endsWith(this.extension)) {
				return true;
			}
			else {
				return false;
			}	
		}
		
	}
	
	/*
	 * Helper methods
	 */
	
	/**
     * Method to parse a file and convert to a string
     * @param path path of file
     * @return string representation of file
     */
    public static String fileReader(File file) {

		try {
			Scanner s = new Scanner(file);
			String output = "";
			
			// scan lines, append each to string
			while(s.hasNextLine()) {
				output += s.nextLine() + "\n";
			}
			
			// return string representing file
			return output;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}			
	}
}
