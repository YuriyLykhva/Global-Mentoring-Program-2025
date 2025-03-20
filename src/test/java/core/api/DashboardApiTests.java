package core.api;

import core.utils.RandomStringGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

public class DashboardApiTests extends BaseApiTest {
    private final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    private final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        createdDashboard.remove();
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardById(createdDashboard.get());
        }
    }

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
    }

    private void extractAndSetCreatedDashboard(Response response){
        Optional.ofNullable(response.path("id")).ifPresent(id -> {
            createdDashboard.set(id.toString());
        });
    }
}
