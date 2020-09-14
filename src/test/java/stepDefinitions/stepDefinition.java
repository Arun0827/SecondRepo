package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDefinition extends Utils {
	
	ResponseSpecification resSpec;
	RequestSpecification req;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {

		req = given().spec(requestSpecification())
				.body(data.addPlacePayLoad(name,language,address));
	}

	@When("user calls {string} using {string} http request")
	public void user_calls_using_http_request(String resource, String httpMethod) {
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(httpMethod.equalsIgnoreCase("POST")) {
			
		 response = req.when().post(resourceAPI.getResource());
		}
		
		else if(httpMethod.equalsIgnoreCase("GET")) {
			
			response = req.when().get(resourceAPI.getResource());
		}
				
	}

	@Then("the API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(Integer int1) {
	   assertEquals(response.getStatusCode(),200);
	}

	@And("^\"([^\"]*)\" in response body is \"([^\"]*)\"$")
    public void something_in_response_body_is_something(String keyValue, String expectedValue) {
	    
		String resp = response.asString();
		
		JsonPath jp = new JsonPath(resp);
		assertEquals(getJsonPath(response,keyValue),expectedValue);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	   
		place_id = getJsonPath(response, "place_id");
		req = given().spec(requestSpecification().queryParam("place_id", place_id));
		user_calls_using_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName,expectedName);
	}
	
	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
	   
		req = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}


}
