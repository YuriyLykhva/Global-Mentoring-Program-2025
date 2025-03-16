package core.api;

import core.utils.RandomStringGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardApiTests extends BaseApiTest {

    @Test
    @org.junit.jupiter.api.Test
    public void createDashboardTest() {
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        ApiClient apiClient = new ApiClient();
        Response response = apiClient.createDashboardWithName(targetDashboardName);
        Assert.assertEquals(response.getStatusCode(), 201);
        Assertions.assertEquals(response.getStatusCode(), 201);

    }

    @Test
    @org.junit.jupiter.api.Test
    public void getAllDashboardsTest() {
        ApiClient apiClient = new ApiClient();
        Response response = apiClient.getAllDashboards();
        System.out.println(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void deleteDashboardByIdTest() {
        //todo: get id from the response
        String id = "127";
        ApiClient apiClient = new ApiClient();
        Response response = apiClient.deleteDashboardById(id);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
