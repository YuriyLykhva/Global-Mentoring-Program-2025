package core.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {
    private final RequestSpecification requestSpecification;

    public ApiClient(RequestSpecification requestSpecification){
        this.requestSpecification = handleRequest(requestSpecification);
    }

    public Response post(String url, Object requestBody) {
        return handleResponse(requestSpecification.body(requestBody).when().post(url));
    }

    public Response put(String url, Object requestBody) {
        return handleResponse(requestSpecification.body(requestBody).when()
//                .patch(url));
                .put(url));
    }

    public Response get(String url) {
        return handleResponse(requestSpecification.when().get(url));
    }

    public Response delete(String url) {
        return handleResponse(requestSpecification.when().delete(url));
    }

    private RequestSpecification handleRequest(RequestSpecification requestSpecification) {
        return RestAssured.given(requestSpecification)
                .relaxedHTTPSValidation().log().all();
    }

    private Response handleResponse(Response response) {
        return response.then().log().all().extract().response();
    }
}
