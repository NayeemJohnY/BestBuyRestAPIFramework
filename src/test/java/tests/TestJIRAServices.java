package tests;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class TestJIRAServices {

	@Test
	public void testJIRAService1() {
		baseURI= "https://jira.cengage.com";
		System.out.println(get());
	}

}
