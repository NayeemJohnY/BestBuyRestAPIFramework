package base;

import static io.restassured.RestAssured.baseURI;
import static org.hamcrest.CoreMatchers.is;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ReadProperties;

public class APITestBase {

	public String filePath=ReadProperties.getProperties("filePath");
	public String testScope="Products";//"Products";//System.getProperty("testScope");
	public ContentType contentType=ContentType.JSON;
	public Response response;
	
	
	public APITestBase() {
		baseURI = ReadProperties.getProperties("baseURI");
	}
	
	public void validateResponse(int expectedStatusCode, int data, String expectedField){
		response.then().statusCode(expectedStatusCode);
	}
}
