Feature: validating Place API's

@AddPlace @Regression
Scenario Outline: Verify if Place is Successfully added using AddPlaceAPI
	Given Add Place Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" using "POST" http request
	Then the API call is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_id created maps to "<name>" using "getPlaceAPI"
	
Examples:
	|name |language |address|
	|Arun |English  |Bermuda|
#	|Kumar|Spanish  |Korea  |


@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working

	Given DeletePlace Payload
	When user calls "deletePlaceAPI" using "POST" http request
	Then the API call is success with status code 200
	And "status" in response body is "OK"