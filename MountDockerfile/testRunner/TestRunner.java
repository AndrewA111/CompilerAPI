import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.runner.JUnitCore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestRunner {
	public static void main(String[] args){
        
        /*
		 * Create a map to store method-info pairs
		 * 
		 * Use LinkedHashMap to preserve insertion order
		 * 
		 */
		Map<String, TestInfo> outputMap = new LinkedHashMap<String, TestInfo>();

		/*
		 * Create metadata object
		 */
		TestMetadata meta = new TestMetadata();
		
		/*
		 *  Setup JUnit
		 *  
		 *  Pass map to listener which will add relevant info during testing
		 *  
		 */
		JUnitCore core = new JUnitCore();
		CustomListener listener = new CustomListener(outputMap, meta);
		core.addListener(listener);
		
		// run tests
		core.run(Tests.class);       
		
		// Get iterator
        Iterator<Map.Entry<String, TestInfo>> it = outputMap.entrySet().iterator();

        // arraylist to store values 
        ArrayList<TestInfo> infoList = new ArrayList<TestInfo>();
        
        while(it.hasNext()) {
        	Map.Entry<String, TestInfo> entry = it.next();
        	
        	infoList.add(entry.getValue());
        }
        
        // convert to array
        TestInfo[] infoArray = new TestInfo[infoList.size()];
        infoArray = infoList.toArray(infoArray);

        // build response object
        TestJsonResponse response = new TestJsonResponse(meta.getNumTests(),
        												meta.getNumFailed(),
        												infoArray);
        
        // string to store json response
        String json = "";
        
        // Convert to JSON
        try {
			json = new ObjectMapper().writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        System.out.println(json);
    }
}