package core.api.api_client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredApiClient implements IApiClient {
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
        return customResultResponse;
    }

    private RequestSpecification getDefaultSpecification(CustomRequestData customRequestData) {
        return RestAssured.given()
                .headers(customRequestData.getHeaders())
                .baseUri(customRequestData.getBaseUri());
    }
}
