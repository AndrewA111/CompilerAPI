package CompileAPI.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import CompileAPI.api.SubmittedFiles;



@Path("/{question}/submit")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubmitResource {
	
	
	@POST
	public String submit(@PathParam("questions") String question,
						SubmittedFiles submittedFiles) {
		
		String currDir = System.getProperty("user.dir");
		
		System.out.println("Present Project Directory : " + currDir);
		
		System.out.println(submittedFiles);
		
		String destDir = currDir + "/questions/" + submittedFiles.getFiles()[0].getName() + "/";
		
		File javaFile = new File(destDir + "Calculator.java");
		
		String output = null;
		
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(javaFile));
			
			Scanner s = new Scanner(submittedFiles.getFiles()[0].getContent()).useDelimiter("\n");
			
			while(s.hasNext()) {
				pw.write(s.next());
			}
			
			pw.close();
			
			output = runProcess("java -cp "
					+ "/home/andrew/eclipse-workspace/dropwizardTutorial/testCompiler/ QuestionCompiler "
					+ destDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return output;
		
//		String executionFolderPath = System.getProperty("user.dir") + "/" 
//										+  question + "/";
//		System.out.println("Test print");
//		return "Test";
	}
	
	/*
	 * Helper methods for calling testing
	 */
	
	private static String printLines(String name, InputStream ins) throws Exception {
	    String line = null;
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(ins));
	    
	    String lines = "";
	    while ((line = in.readLine()) != null) {
	        System.out.println(name + " " + line);
	        lines += line;
	    }
	    
	    return lines;
	  }

	  private static String runProcess(String command) throws Exception {
		  
		// run the command
	    Process pro = Runtime.getRuntime().exec(command);
	    
	    // string to build output
	    String output = "";
	    
	    
	    String sOut = printLines(command + " stdout:", pro.getInputStream());
	    String sErr = printLines(command + " stderr:", pro.getErrorStream());
	    pro.waitFor();
	    System.out.println(command + " exitValue() " + pro.exitValue());
	    
	    return sOut + sErr;
	  }
}
