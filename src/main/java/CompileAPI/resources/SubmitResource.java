package CompileAPI.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import CompileAPI.api.ResultsMessage;
import CompileAPI.api.SubmittedFiles;



@Path("/java/submit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 * Resource to allow file submission
 * 
 * question path parameter allows question to be selected
 * 
 * @author andrew
 *
 */
public class SubmitResource {
	
	@POST
	public String submit(SubmittedFiles submittedFiles) {
		
//		// print received JSON object
//		System.out.println(submittedFiles);
		
		// get project root dir
		String currDir = System.getProperty("user.dir");
		
		// directory of compiler program
		String compDir = currDir + "/testCompiler/";
		
		// create temp file directory for code execution
		String destDir = currDir + "/temp/" + System.nanoTime() + "/";
		new File(destDir).mkdirs();
		
		// Add TestRunner.java into temp dir
		String testRunnerLoc = currDir + "/testRunner/";
		try {
			FileUtils.copyDirectory(new File(testRunnerLoc), 
										new File(destDir));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// loop through incoming files and write them to temp folder
		for(int i = 0; i < submittedFiles.getFiles().length; i++) {
			
			// create file to store received file
			File javaFile = new File(destDir + "/" + submittedFiles.getFiles()[i].getName());
			
			// writer for creating .java file
			PrintWriter pw;
			try {
				pw = new PrintWriter(new FileWriter(javaFile));
				
				// scan input & write a new line for each line break (\n)
				Scanner s = new Scanner(submittedFiles.getFiles()[i].getContent()).useDelimiter("\n");
				while(s.hasNext()) {
					pw.write(s.next() + "\n");
				}
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// String to store output to return to client
		String output = null;
	
		/*
		 *  run QuestionCompiler program using java command, 
		 *  passing destination directory as an argument
		 */
		output = runProcess("java -cp "
				
				// program classpath and target file name
				+ compDir + " QuestionCompiler "
				
				// argument
				+ destDir);
			
		// delete temporary directory
		try {
			FileUtils.deleteDirectory(new File(destDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// return output from compiler
		return output;
	}
	
	/*
	 * Helper methods for calling testing
	 */
	
	/**
	 * Method to convert a command 'name' and lines of a passed input stream
	 * to a String
	 * @param name command
	 * @param ins input stream
	 * @return input stream as a String
	 * @throws Exception
	 */
	private static String linesToString(String name, InputStream ins) {
		
		// String for each line
		String line = null;
		
		// Reader
		BufferedReader in = new BufferedReader(
				new InputStreamReader(ins));
	    
		// Output string
		String lines = "";
		
		// read each line and add to lines
		try {
			while ((line = in.readLine()) != null) {
				System.out.println(name + " " + line);
				lines += line + "\n\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		// return all lines
		return lines;
	}
	
	/**
	 * Method to run process a return stdout and stderr 
	 * as a String
	 * @param command
	 * @return output as string
	 */
	private static String runProcess(String command) {
  
		Process pro;
		try {
			
			// run the command
			pro = Runtime.getRuntime().exec(command);
			
			// get standard and error output
			String sOut = linesToString(command + " stdout:", pro.getInputStream());
			String sErr = linesToString(command + " stderr:", pro.getErrorStream());
			
			// wait for process to finish
			pro.waitFor();
			
			// Print exit value
			System.out.println(command + " exitValue() " + pro.exitValue());
			
			// create message (output and error) and convert to JSON
			ResultsMessage message = new ResultsMessage(sOut, sErr);
			
			ObjectMapper mapper = new ObjectMapper();
			
			String jsonMessage = mapper.writeValueAsString(message);
			
			// return output
			return jsonMessage;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null; 
	  }
}
