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
        //prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();

        //act
        Response response = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(response);
        var createdId = response.path("id").toString();
        var createdDashboardBody = reportPortalApiClient.getDashboardById(createdId);
        String dashboardName = createdDashboardBody.path("name").toString();
        boolean isCreated = dashboardName.equals(targetDashboardName);

        //verify
        Assert.assertEquals(response.getStatusCode(), 201);
        Assertions.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(isCreated);
        Assertions.assertTrue(isCreated);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void createEmptyNameDashboardNegativeTest() {
        //prepare
        String targetDashboardName = "";

        //act
        Response response = reportPortalApiClient.createDashboardWithName(targetDashboardName);

        //verify
        Assert.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void createTooLongNameDashboardNegativeTest() {
        //prepare
        String targetDashboardName = "tonireytvnorevyntowerynotwyernotvwyotnuwrntoernhgowrnhgociwreunvoiurevntoreuhnt" +
                "vowrenvtoiwrueyntovuwyrenotvuyrewovynwtvnweiutvnoireu";

        //act
        Response response = reportPortalApiClient.createDashboardWithName(targetDashboardName);

        //verify
        Assert.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void getAllDashboardsTest() {
        //act
        Response response = reportPortalApiClient.getAllDashboards();

        //verify
        Assert.assertEquals(response.getStatusCode(), 200);
        Assertions.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void getAllDashboardsNegativeTest() {
        //act
        Response response = reportPortalApiClient.getAllDashboards("");

        //verify
        Assert.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getStatusCode(), 400);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void updateDashboardTest() {
        //prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        var createdDashboard = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(createdDashboard);
        var createdId = createdDashboard.path("id").toString();

        //act
        Response response = reportPortalApiClient.updateDescriptionOfDashboardById(createdId);
        var createdDashboardBody = reportPortalApiClient.getDashboardById(createdId);
        String updatedDescription = createdDashboardBody.path("description").toString();
        boolean isUpdated = updatedDescription.contains("updated");

        //verify
        Assert.assertEquals(response.getStatusCode(), 200);
        Assertions.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(isUpdated);
        Assertions.assertTrue(isUpdated);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void updateDashboardInvalidTest() {
        //prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        var createdDashboard = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(createdDashboard);
        var createdId = createdDashboard.path("id").toString();

        //act
        Response response = reportPortalApiClient.updateDescriptionOfDashboardById(createdId);
        var createdDashboardBody = reportPortalApiClient.getDashboardById(createdId);
        String updatedDescription = createdDashboardBody.path("description").toString();
        boolean isUpdated = updatedDescription.contains("updated!!!!!!!!!");

        //verify
        Assert.assertEquals(response.getStatusCode(), 200);
        Assertions.assertEquals(response.getStatusCode(), 200);
        Assert.assertFalse(isUpdated);
        Assertions.assertFalse(isUpdated);
    }

    @Test
    @org.junit.jupiter.api.Test
    public void updateDashboardWithWrongIdTest() {
        //prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        var createdDashboard = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(createdDashboard);
        var createdId = createdDashboard.path("id").toString();

        //act
        Response response = reportPortalApiClient.updateDescriptionOfDashboardById(createdId + "1");
        var createdDashboardBody = reportPortalApiClient.getDashboardById(createdId);
        String updatedDescription = createdDashboardBody.path("description").toString();
        boolean isUpdated = updatedDescription.contains("updated");

        //verify
        Assert.assertEquals(response.getStatusCode(), 400);
        Assertions.assertEquals(response.getStatusCode(), 400);
        Assert.assertFalse(isUpdated);
        Assertions.assertFalse(isUpdated);
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

    @Test
    @org.junit.jupiter.api.Test
    public void deleteDashboardByWrongIdTest() {
        //prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        var createdDashboard = reportPortalApiClient.createDashboardWithName(targetDashboardName);
        extractAndSetCreatedDashboard(createdDashboard);
        var createdId = createdDashboard.path("id").toString();

        //act
        Response deleteResponse = reportPortalApiClient.deleteDashboardById(createdId + "1");

        //verify
        Assert.assertEquals(deleteResponse.getStatusCode(), 404);
        Assertions.assertEquals(deleteResponse.getStatusCode(), 404);
    }

}
