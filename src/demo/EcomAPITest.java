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
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.List;

import groovy.util.OrderBy;


public class EcomAPITest {

	public static void main(String[] args) 
	{
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		ResponseSpecification resSpec = new ResponseSpecBuilder().log(LogDetail.ALL).expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		LoginRequest lr = new LoginRequest();
		lr.setUserEmail("fit.dsf@gmail.com");
		lr.setUserPassword("Qwerty1!");
		
		RequestSpecification reqLogin = given().log().all().spec(req).body(lr);
		
		LoginResponse resLogin = reqLogin.when().post("/api/ecom/auth/login")
		.then().spec(resSpec).extract().response().as(LoginResponse.class);
		
		String token = resLogin.getToken();
		String userId = resLogin.getUserId();
		
		System.out.println(resLogin.getToken());
		System.out.println(resLogin.getUserId());
		
		//Add Product 
		
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification reqAddProduct = 	given().log().all().spec(addProductBaseReq).param("productName", "Omega Watch")
			.param("productAddedBy", userId).param("productCategory", "Fashion")
			.param("productSubCategory", "shirts").param("productPrice", "414986")
			.param("productDescription", "Omega SpeedMaster").param("productFor", "men")
			.multiPart("productImage", new File("\\Users\\MohammedSaif\\Downloads\\Omega.jpg"));
		
		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
				.then().log().all().assertThat().statusCode(201).body("message",equalTo("Product Added Successfully")).extract().response().asString();
		
		JsonPath jp = new JsonPath(addProductResponse);
		String productId = jp.getString("productId");
		
		System.out.println(productId);
		
		// Create Order 
		
		RequestSpecification reqCreateOrderBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.setContentType(ContentType.JSON).build();
		
		OrderDetails od = new OrderDetails();
		od.setCountry("India");
		od.setProductOrderedId(productId);
		
		List<OrderDetails> orderDetList = new ArrayList<OrderDetails>();
		orderDetList.add(od);
		
		Orders order = new Orders();
		order.setOrders(orderDetList);
		
		RequestSpecification createOrderReq = given().log().all().spec(reqCreateOrderBase)
				.body(order);
		String createOrderResponse =createOrderReq.when().post("/api/ecom/order/create-order")
		.then().log().all().assertThat().statusCode(201).body("message", equalTo("Order Placed Successfully")).extract().response().asString();
		
		System.out.println(createOrderResponse);
		
		//Delete Product 
		
		RequestSpecification baseDeleteReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification deleteProdReq = given().log().all().spec(baseDeleteReq).pathParam("productId", productId);
		String deleteProdResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().assertThat().statusCode(200).body("message", equalTo("Product Deleted Successfully")).extract().response().asString();
		
		
		System.out.println(deleteProdResponse);
		
	}

}
