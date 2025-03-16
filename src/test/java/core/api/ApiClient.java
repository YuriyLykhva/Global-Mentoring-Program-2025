package core.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class ApiClient extends BaseApiTest {

    RequestSpecification requestSpecification = RestAssured.given()
            .headers("accept", "*/*",
                    "Authorization", "bearer" + token,
                    "Content-Type", "application/json")
            .baseUri(homePageURL + BASE_URL);

    public Response createDashboardWithName(String dashboardName) {

        String url = String.format("v1/%s/dashboard", projectName);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "This is a test dashboard");
        requestBody.put("name", dashboardName);

        return RestAssured.given(requestSpecification)
                .body(requestBody)
                .when()
                .post(url)
                .then()
                .extract().response();

    }

    public Response getAllDashboards() {

        String url = String.format("v1/%s/dashboard", projectName);

        return RestAssured.given(requestSpecification)
                .when()
                .get(url)
                .then()
                .extract().response();

    }

    public Response deleteDashboardById(String id) {

        String url = String.format("v1/%s/dashboard", projectName);

        return RestAssured.given(requestSpecification)
                .when()
                .delete(url + "/" + id)
                .then()
                .extract().response();

    }
}
