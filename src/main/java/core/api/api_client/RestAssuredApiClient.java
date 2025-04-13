package core.api.api_client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestAssuredApiClient implements IApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestAssuredApiClient.class);
    private final RequestSpecification requestSpecification;

    public RestAssuredApiClient(CustomRequestData customRequestData){
        this.requestSpecification = handleRequest(getDefaultSpecification(customRequestData));
    }

    public CustomResponse post(String url, Object requestBody) {
        return handleResponse(requestSpecification.body(requestBody).when().post(url));
    }

    public CustomResponse put(String url, Object requestBody) {
        return handleResponse(requestSpecification.body(requestBody).when()
                .put(url));
    }

    public CustomResponse get(String url) {
        return handleResponse(requestSpecification.when().get(url));
    }

    public CustomResponse delete(String url) {
        return handleResponse(requestSpecification.when().delete(url));
    }

    private RequestSpecification handleRequest(RequestSpecification requestSpecification) {
        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation().log().all();
    }

    private CustomResponse handleResponse(Response response) {
        Response restAssured = response.then().log().all().extract().response();
        CustomResponse customResultResponse = new CustomResponse(restAssured.getStatusCode());
        customResultResponse.setResponseBodyString(restAssured.getBody().prettyPrint());

        LOGGER.info("Received response with status code: {}", restAssured.getStatusCode());
        LOGGER.debug("Response body: {}", restAssured.prettyPrint());

        return customResultResponse;
    }

    private RequestSpecification getDefaultSpecification(CustomRequestData customRequestData) {
        LOGGER.debug("Adding headers: {}", customRequestData.getHeaders().entrySet());
        return RestAssured.given()
                .headers(customRequestData.getHeaders())
                .baseUri(customRequestData.getBaseUri());
    }
}
