import java.util.Map;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.Test;


/**
 * Custom listener used to populate a map of information for the tests provided
 *  in Tests.java
 *  
 * @author Andrew
 *
 */
public class CustomListener extends RunListener {
	
	// map for storing test information
	Map<String, TestInfo> outputMap;

	// metadata object
	TestMetadata meta;
	
	// Constructor 
	public CustomListener(Map<String, TestInfo> outputMap, TestMetadata meta) {
		
		// call superconstructor
		super();
		
		// set map
		this.outputMap = outputMap;

		// set metadata
		this.meta = meta;
	}
	
	/**
	 * Called when a test is started.
	 * 
	 * Creates the TestInfo object and populates name and descirption (if available)
	 */
	public void testStarted(Description description) {

		// if not a an annotated test return
		if(description.getAnnotation(Test.class) == null) {
			return;
		}

		// increment test count
		meta.incrementNumTests();
		
		// create entry for this test
		this.outputMap.put(description.getMethodName(), 
				new TestInfo(description.getMethodName()));
		
		
		// Check for description annotation
		if(description.getAnnotation(TestDescription.class) != null) {
			
			// get annotation
			TestDescription td = description.getAnnotation(TestDescription.class);
			
			// get value
			String descriptionText = td.value();
			
			// get map entry and set its description
			outputMap.get(description.getMethodName()).setDescription(descriptionText);
			
		}		
		// if no description, set empty string
		else {
			// get map entry and set its description
			outputMap.get(description.getMethodName()).setDescription("");
		}

		// Check for hint annotation
		if(description.getAnnotation(TestDescription.class) != null) {

			// get annotation
			TestHint th = description.getAnnotation(TestHint.class);

			// get value
			String hintText = th.value();

			// get map entry and set its hint
			outputMap.get(description.getMethodName()).setHint(hintText);

		}

		// if no hint, set empty string
		else{
			// get map entry and set its hint
			outputMap.get(description.getMethodName()).setHint("");
		}
	}
	
	/**
	 * If a test fails, set 'passed'  boolean to false;
	 */
	public void testFailure(Failure failure){

		// if initializationError (no tests) return
		if(failure.getTestHeader().equals("initializationError(Tests)")) {
			return;
		}

		// increment failure count
		meta.incrementNumFailed();
		
		// get method (key to map)
		String testMethodName = failure.getDescription().getMethodName();
		
		// set passed status false
		this.outputMap.get(testMethodName).setPassed(false);
		
		// set description
		this.outputMap.get(testMethodName).setFailureText(failure.getMessage());

	}
	


}
