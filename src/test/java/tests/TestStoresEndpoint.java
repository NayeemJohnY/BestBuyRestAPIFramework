package tests;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class TestStoresEndpoint {
	@BeforeTest
	public void storesEndpointSetup() {
		basePath = "/stores";
	}

	/**
	 * TC_01 Retrieve default Product details
	 */
	@Test(priority = 1,testName="Stores_TC_01_Get All Stores")
	public void getDefaultStoresDetails() {
		when().get();
	}

	@Test(priority = 2, testName = "Stores_TC_02_Get Stores by limit")
	public void getDefaultStoresDetailsByLimit() {
		given().queryParam("$limit", 1).when().get();
	}

	@Test(priority = 3, testName = "Stores_TC_03_Get Store by id")
	public void getDefaultStoreDetailByID() {
		given().log().all().pathParam("id", 45380).when().log().all().get("/{id}");
	}

}
