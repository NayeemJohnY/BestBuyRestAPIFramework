package tests;

import static io.restassured.RestAssured.*;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.APITestBase;
import endpoints.Products;

public class TestProductsEndpoint extends APITestBase {

	Products products;

	@BeforeTest
	public void productEndpointSetup() {
		products = new Products();
		basePath = "/products";
	}

	/**
	 * TC_01 Retrieve default Product details
	 */
	@Test(priority = 1, testName = "Products_TC_01_Get All products")
	public void getDefaultProductDetails() {
		response = when().get();
		validateResponse(400, 51957, "total");

	}

	@Test(priority = 2, testName = "Products_TC_02_Get products by limit")
	public void getDefaultProductDetailsByLimit() {
		int limit=10;
		response = given().queryParam("$limit", limit).when().get();
		validateResponse(200, limit, "limit");
	}

	@Test(priority = 3, testName = "Products_TC_03_Get product By id")
	public void getProductDetailByID() {
		int id=346575;
		response=given().log().all().pathParam("id", id).when().log().all().get("/{id}");
		validateResponse(200, id, "id");

	}
	@Test(testName = "Products_TC_04_Get products By Skipping results")
	public void getDefaultProductDetailBySkippingResults() {
		int skip=2000;
		response=given().log().all().queryParam("$skip", skip).when().log().all().get();
		validateResponse(200, skip, "skip");
	}
	@Test(testName = "Products_TC_05_Get products sort by highest price")
	public void getHighestPriceProductDetail() {
		int sort=1;
		response = given().queryParam("$sort{price]", sort).when().get();
		List<String>res=response.getBody().jsonPath().get("data[.].price");
		System.out.println(res);
	}
}
