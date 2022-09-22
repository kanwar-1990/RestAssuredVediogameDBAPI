package TestingVedioGameAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestResponseFromVideoGameDb {

	@Test(priority = 1)
	public void getResponseVediogameFromDb() {

		given().get("http://localhost:8080/app/videogames").then().statusCode(200);

	}

	@Test(priority = 2)
	public void postResponseOfAddingNewGameInDb() {

		HashMap mapdetails = new HashMap();
		mapdetails.put("id", "101");
		mapdetails.put("name", "daredevil");
		mapdetails.put("releaseDate", "2022-09-21T10:24:31.162Z");
		mapdetails.put("reviewScore", "9");
		mapdetails.put("category", "fiction");
		mapdetails.put("rating", "universal");

		/*
		 * JSONObject jsonObj=new JSONObject(); jsonObj.put("id","100");
		 * jsonObj.put("name", "daredevil"); jsonObj.put("releaseDate",
		 * "2022-09-21T10:24:31.162Z"); jsonObj.put("reviewScore", "9");
		 * jsonObj.put("category", "Action"); jsonObj.put("rating", "5");
		 */

		Response response = given().contentType(ContentType.JSON).body(mapdetails).when()
				.post("http://localhost:8080/app/videogames").then().log().body().extract().response();

		int statuscode = response.getStatusCode();
		System.out.println(statuscode);

	}

	@Test
	public void getSingleGameDetails() {

		given().when().get("http://localhost:8080/app/videogames/10").then().header("content-length", "238").log()
				.body()

				.body("videogames.id", equalTo("10")).body("videogames.name", equalTo("Grand Theft Auto III"));
	}

	@Test
	public void putRequestUpdatingExistingVedioGame() {
		HashMap mapdetails1 = new HashMap();
		mapdetails1.put("id", "101");
		mapdetails1.put("name", "moonLight");
		mapdetails1.put("releaseDate", "2022-09-21T10:24:31.162Z");
		mapdetails1.put("reviewScore", "8");
		mapdetails1.put("category", "Action");
		mapdetails1.put("rating", "universal");

		given().contentType(ContentType.JSON).body(mapdetails1).when().put("http://localhost:8080/app/videogames/101")

				.then().log().body().body("vediogames.reviewScore", equalTo("8"))
				.body("vediogames.name", equalTo("moonLight"));

	}

	@Test
	public void deleteExistingVedioGame() {

		given()
              .contentType(ContentType.JSON)
				.when()
				
				.delete("http://localhost:8080/app/videogames/101")
				.then()
								
				.statusCode(200).log().all();

	}

}
