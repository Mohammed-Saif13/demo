package demo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.AddPlaceLocation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;
public class SerialisationEg {

	public static void main(String[] args) 
	{
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		AddPlace p = new AddPlace();
		
		AddPlaceLocation apl = new AddPlaceLocation();
		apl.setLat(-38.383765);
		apl.setLng(33.427432);
		p.setLocation(apl);
		
		p.setAccuracy(50);
		p.setName("Dwaar Chaudhary Munna Niwaas ");
		p.setPhone_number(" +918318622136 ");
		p.setAddress(" 8/1-A , Mall Avenue , Lucknow ");
		
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setType(myList);
		
		p.setWebsite("http://google.com");
		p.setLanguage("French-IN");
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
			ResponseSpecification resSpec =	new ResponseSpecBuilder().log(LogDetail.ALL).expectStatusCode(200).expectContentType(ContentType.JSON).build();
			
		RequestSpecification res =given().spec(req).body(p);
		
		
		Response response = res.when().post("/maps/api/place/add/json")
				
		.then().spec(resSpec).body("scope",equalTo("APP")).extract().response();
		
		String resString = response.toString();
		System.out.println(resString);
		
//		JsonPath jp2 = new JsonPath(resString);
//		System.out.println(jp2.getString("place_id"));
//		
		
	}

}
