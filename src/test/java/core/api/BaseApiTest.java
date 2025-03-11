package core.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseApiTest {

    protected static final String BASE_URL = "http://localhost:8080/api/";

    String token = "API-KEY_SYI2ZqtkTY-dW0xH5o82RCEXohSM_gAMppycnJhWXhbRVXJfVG6PYdUUXp8fL9An";

    RequestSpecification requestSpecification = RestAssured.given()
            .headers("accept", "*/*",
                    "Authorization", "bearer" + token,
                    "Content-Type", "application/json")
            .baseUri(BASE_URL);
}
