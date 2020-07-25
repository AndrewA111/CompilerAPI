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
		 *  Setup JUnit
		 *  
		 *  Pass map to listener which will add relevant info during testing
		 *  
		 */
		JUnitCore core = new JUnitCore();
		CustomListener listener = new CustomListener(outputMap);
		core.addListener(listener);
		
		// run tests
		core.run(Tests.class);       
		
		// ######## Testing #########
        // Iterator<Map.Entry<String, TestInfo>> it = outputMap.entrySet().iterator();
        
        // while(it.hasNext()) {
        // 	Map.Entry<String, TestInfo> entry = it.next();
        	
        // 	System.out.println(entry.getValue());
        // }
        
        String json = "";
        
        // Convert to JSON
        try {
			json = new ObjectMapper().writeValueAsString(outputMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        
        System.out.println(json);
    }
}