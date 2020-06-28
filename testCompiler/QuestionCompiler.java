import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class QuestionCompiler {

    /**
     * Main method used to compile question code and run TestRunner.java
     * 
     * Single command line argument specifying source code folder location 
     * expected
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            
            // arg[0] is folder location of files to be compiled
            String folderLocation = args[0];
            
            // string to build file names for arguments to javac
            String javaFiles = "";

            // get all files in folder
            File[] files = new File(folderLocation).listFiles();

            // for all .java files, append to javaFiles string
            for(File file: files){
                if(file.getName().endsWith(".java")){;
                    javaFiles = javaFiles + " " + folderLocation + file.getName();
                }
            }

            // compile and run tests
            String compOut = runCompile("javac -cp " + folderLocation + 
            ":/home/andrew/eclipse-workspace/dropwizardTutorial/testCompiler/junit-4.13.jar " + 
            javaFiles);

            /*
             *  If compilation output is not empty, error has occured
             *  Print error and return
             */
            if(!compOut.equals("")){

                // remove file location
                compOut = compOut.replace(folderLocation, "");
                System.out.println("Compilation error\n" + compOut);
                return;
            }

            // run the tests
            String result = runTests("java -cp " + folderLocation + 
            ":/home/andrew/eclipse-workspace/dropwizardTutorial/testCompiler/junit-4.13.jar:" + 
            "/home/andrew/eclipse-workspace/dropwizardTutorial/testCompiler/hamcrest-core-1.3.jar" + " TestRunner");

            // empty string indicated tests passsed, print 0 to indicate
            if(result.equals("")){
                System.out.println("0");
            }
            else{
                System.out.println(result);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to run compilation
     * @param command
     */
    public static String runCompile(String command) {
          
        try {
            // Execute command
            Process pro = Runtime.getRuntime().exec(command);

            // Get any error output and return it
            String cErr = streamToString(pro.getErrorStream());
            return cErr;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to run tests
     * @param command
     */
    public static String runTests(String command) {
          
        try {
            // Execute command
            Process pro = Runtime.getRuntime().exec(command);
            
            // string to build output
            String output = "";

            // get command output
            String cOut = streamToString(pro.getInputStream());
            
            // get error output
            String cErr = streamToString(pro.getErrorStream());

            /*
             * If no text returned from tests and no errors,
             * tests were successful, return empty string
             */
            if(cOut.equals("") && cErr.equals("")){
                return "";
            }
            /*
             *  Else, if no errors return output (test failures)
             */
            else if (cErr.equals("")){
                return cOut;
            }

            /*
             *  Else return error message
             */
            else{
                return cErr;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method to read input stream and convert to a string
     * 
     * @param is
     */
    public static String streamToString(InputStream is) {

        BufferedReader input = new BufferedReader(new InputStreamReader(is));

        String line = null;
        String lines = "";
        try {
            while ((line = input.readLine()) != null) {
                lines += line;
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}