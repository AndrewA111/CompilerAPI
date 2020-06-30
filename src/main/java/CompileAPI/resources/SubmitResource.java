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

import CompileAPI.api.SubmittedFiles;



@Path("/{question}/submit")
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
	public String submit(@PathParam("question") String question,
						SubmittedFiles submittedFiles) {
		
		// print received JSON object
		System.out.println(submittedFiles);
		
		// get project root dir
		String currDir = System.getProperty("user.dir");
		
		// get question supporting files location
		String questDir = currDir + "/questions/" + question + "/sub/";
		
		// directory of compiler program
		String compDir = currDir + "/testCompiler/";
		
		// create temp file directory for code execution
		String destDir = currDir + "/temp/" + System.nanoTime() + "/";
		File destDirF = null;
		
		// copy question files to temp 
		try {
			FileUtils.copyDirectory(new File(questDir), destDirF = new File(destDir));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		// create file to store received file
		File javaFile = new File(destDir + "/" + submittedFiles.getFiles()[0].getName());
		
		// String to store output to return to client
		String output = null;
		
		try {
			
			// writer for creating .java file
			PrintWriter pw = new PrintWriter(new FileWriter(javaFile));
			
			// scan input & write a new line for each line break (\n)
			Scanner s = new Scanner(submittedFiles.getFiles()[0].getContent()).useDelimiter("\n");
			while(s.hasNext()) {
				pw.write(s.next());
			}
			pw.close();
			
			/*
			 *  run QuestionCompiler program using java command, 
			 *  passing destination directory as an argument
			 */
			output = runProcess("java -cp "
					
					// program path
					+ compDir + " QuestionCompiler "
					
					// argument
					+ destDir);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// delete temporary directory
		try {
			FileUtils.deleteDirectory(destDirF);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
			
			// return output
			return sOut + sErr;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null; 
	  }
}
