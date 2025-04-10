package core.api.OkHttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.utils.RandomStringGenerator;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

import static core.api.OkHttp.ThreadLocalHolder.createdDashboard;

public class OkHttpDashboardApiTests extends OkHttpBaseApiTest {

    @Test
    @org.junit.jupiter.api.Test
    public void getAllDashboardsTest() {
        //act
        Response getAllDashboards = okHttpReportPortalApiClient.getAllDashboards();
        // verify
        Assert.assertEquals(getAllDashboards.code(), 200, "All dashboards are received successfully");
    }

    @Test
    @org.junit.jupiter.api.Test
    public void createDashboardTest() {
        // prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        //act
        Response response = okHttpReportPortalApiClient.createDashboardWithName(targetDashboardName);
        // verify
        Assert.assertEquals(response.code(), 201, "Dashboard is created successfully");
    }

    @Test
    @org.junit.jupiter.api.Test
    public void getDashboardByIdTest() {
        // prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        okHttpReportPortalApiClient.createDashboardWithName(targetDashboardName);
        String id = createdDashboard.get();
        String responseBody;
        Response response;
        //act
        try {
            response = okHttpReportPortalApiClient.getDashboardById(id);
            // verify
            Assert.assertEquals(response.code(), 200, "Dashboard is fetched successfully");

            responseBody = response.peekBody(Long.MAX_VALUE).string();
            System.out.println("Raw response body: " + responseBody);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @org.junit.jupiter.api.Test
    public void updateDashboardTest() {
        // prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        okHttpReportPortalApiClient.createDashboardWithName(targetDashboardName);
        String id = createdDashboard.get();

        //act
        String updatedDescription = "This is an updated description";
        Response updatedDashboard = okHttpReportPortalApiClient
                .updateDashboardById(id, targetDashboardName, updatedDescription);

        // verify
        Assert.assertEquals(updatedDashboard.code(), 200, "Dashboard is updated successfully");

        updatedDashboard = okHttpReportPortalApiClient.getDashboardById(id);

        String responseBody;
        try {
            responseBody = updatedDashboard.peekBody(Long.MAX_VALUE).string();
            Map<String, Object> responseMap = new ObjectMapper().readValue(responseBody, Map.class);

            Object descObj = responseMap.get("description");

            String actualDescription = descObj != null ? descObj.toString() : "";
            boolean isUpdated = actualDescription.contains("updated");

            Assert.assertTrue(isUpdated, "Description was updated");
        } catch (Exception e) {
            throw new RuntimeException("Failed to read response body", e);
        }
    }

    @Test
    @org.junit.jupiter.api.Test
    public void deleteDashboardTest() {
        // prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        okHttpReportPortalApiClient.createDashboardWithName(targetDashboardName);
        String id = createdDashboard.get();
        //act
        Response deletedDashboard = okHttpReportPortalApiClient.deleteDashboardById(id);
        // verify
        Assert.assertEquals(deletedDashboard.code(), 200, "Dashboard is deleted successfully");
    }
}
