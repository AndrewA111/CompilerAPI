import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class to compile and run java files for a given question and run
 * associated tests
 */
public class QuestionCompiler {

    /*
     *  When compiling with javac command, each dependency is delimited by
     *  a separator. The separator is different depending on operating system.
     *  For unix-style systems, the separator is ':', while for windows it is ';'
     * 
     *  By default, separator is set to ':'
     */
    private static String cpSeparator = ":";

    /**
     * Main method used to compile question code and run TestRunner.java
     * 
     * Expects a single command line argument specifying source code folder location 
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            
            // arg[0] is folder location of files to be compiled
            String folderLocation = args[0];

            /*
             * To run tests, compiler must be provided with dependency locations.
             * 
             * Dependencies are:
             * 
             *      - JUnit 4.13
             *      - Hamcrest Core 1.3
             * 
             * By default, these are provided in the same location as this class
             */
            
            // get project root dir
            String currDir = System.getProperty("user.dir");

            // set dependency locations
            final String JUNIT_LOC = currDir + "/testCompiler/junit-4.13.jar";
            final String HAMCREST_CORE_LOC = currDir + "/testCompiler/hamcrest-core-1.3.jar";

            // if on windows OS, change cpSeparator
            if(System.getProperty("os.name").toLowerCase().contains("windows")){
                cpSeparator = ";";
            }
            
            // String to build file names for arguments to javac
            String javaFiles = "";

            // Get all files in folder
            File[] files = new File(folderLocation).listFiles();

            // For all .java files, append to javaFiles string (space separated)
            for(File file: files){
                if(file.getName().endsWith(".java")){;
                    javaFiles = javaFiles + " " + folderLocation + file.getName();
                }
            }

            // Compile and run tests
            String compOut = runCompile("javac -cp " 
                                        + folderLocation + cpSeparator + JUNIT_LOC + " " + 
                                        javaFiles);

            /*
             *  If compilation output is not empty, error has occured
             *  Print error and return
             */
            if(!compOut.equals("")){

                // Remove file location from error
                compOut = compOut.replace(folderLocation, "");

                // Print
                System.out.println("Compilation output:\n" + compOut);
            }

            /*
             *  Run the tests 
             * 
             *  It is expected that the file TestRunner will be available, 
             *  which runs all tests and returns results.
             */
            String result = runTests("java -cp " + 
                                    folderLocation + cpSeparator + JUNIT_LOC + cpSeparator + HAMCREST_CORE_LOC + 
                                    " TestRunner");

            // Empty string indicates tests passsed, print 0 to indicate
            if(result.equals("")){
                System.out.println("0");
            }
            /*
             *  Else, print result. Result with either indicate which tests have failed, 
             *  or show any errors in running TestRunner
             */
            else{
                System.out.println(result);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to run compilation
     * @param command compilation command
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
     * @param command command to run TestRunner
     */
    public static String runTests(String command) {
          
        try {
            // Execute command
            Process pro = Runtime.getRuntime().exec(command);
            
            // String to build output
            String output = "";

            // Get command output
            String cOut = streamToString(pro.getInputStream());
            
            // Get error output
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
     * @param is input stream
     */
    public static String streamToString(InputStream is) {

        // Create BufferedReader to manipulate data
        BufferedReader input = new BufferedReader(new InputStreamReader(is));
        
        // String to store each line
        String line = null;

        // String to build output from lines
        String lines = "";

        try {
            // Loop while new lines available
            while ((line = input.readLine()) != null) {
                // Add line to output string
                lines += line + "\n";
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}