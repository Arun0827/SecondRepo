package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void BeforeScenario() throws IOException {
		
		stepDefinition s  = new stepDefinition();
		if(stepDefinition.place_id == null) {
			
		s.add_Place_Payload_with("Singam", "Kilikili", "Assam");
		s.user_calls_using_http_request("AddPlaceAPI", "POST");
		s.verify_place_id_created_maps_to_using("Singam", "getPlaceAPI");
		
		}
	}

}
