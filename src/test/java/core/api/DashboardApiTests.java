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
        Response response = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(response);
        Assert.assertEquals(response.getStatusCode(), 201);
        Assertions.assertEquals(response.getStatusCode(), 201);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void getAllDashboardsTest() {
        Response response = reportPortalApiClient.getAllDashboards();
        Assert.assertEquals(response.getStatusCode(), 200);
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void deleteDashboardByIdTest() {
        //prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        var createdDashboard = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(createdDashboard);
        var createdId = createdDashboard.path("id").toString();

        //act
        Response deleteResponse = reportPortalApiClient.deleteDashboardById(createdId);

        //verify
        Assert.assertEquals(deleteResponse.getStatusCode(), 200);
        Assertions.assertEquals(deleteResponse.getStatusCode(), 200);
    }

}
