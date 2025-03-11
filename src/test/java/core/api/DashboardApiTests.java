package core.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class DashboardApiTests extends BaseApiTest {

    public void createDashboardTest(String dashboardName) {
        String url = "v1/2025-project/dashboard";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "This is a test dashboard");
        requestBody.put("name", dashboardName);

        Response response =
                RestAssured.given(requestSpecification)
                        .body(requestBody)
                        .when()
                        .post(url)
                        .then()
                        .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    public void deleteDashboardByIdTest() {
        String url = "v1/2025-project/dashboard";

        //todo: get id from the response
        String id = "76";

        Response response =
                RestAssured.given(requestSpecification)
                        .when()
                        .delete(url + "/" + id)
                        .then()
                        .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void getAllDashboardsTest() {
        String url = "v1/2025-project/dashboard";

        Response response =
                RestAssured.given(requestSpecification)
                        .when()
                        .get(url)
                        .then()
                        .extract().response();

        System.out.println(response.prettyPrint());

        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
