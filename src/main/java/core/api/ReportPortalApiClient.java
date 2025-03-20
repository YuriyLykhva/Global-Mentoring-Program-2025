package core.api;

import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class ReportPortalApiClient {
    private final ApiClient defaultClient;
    private final ConfigProperties configProperties;
    private final String defaultProject;

    public ReportPortalApiClient() {
        defaultClient = new ApiClient(getDefaultReportPortalSpecification());
        configProperties = PropertiesHolder.getInstance().getConfigProperties();
        defaultProject = configProperties.defaultRpProjectName();
    }

    public Response createDashboardWithName(String dashboardName) {
        return createDashboardWithName(dashboardName, defaultProject);
    }

    public Response createDashboardWithName(String dashboardName, String projectName) {
        String url = String.format("v1/%s/dashboard", projectName);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "This is a test dashboard");
        requestBody.put("name", dashboardName);
        return defaultClient.post(url, requestBody);
    }

    public Response getAllDashboards() {
        return getAllDashboards(defaultProject);
    }

    public Response getAllDashboards(String projectName) {
        String url = String.format("v1/%s/dashboard", projectName);
        return defaultClient.get(url);
    }

    public Response deleteDashboardById(String id) {
        return deleteDashboardById(id, defaultProject);
    }

    public Response deleteDashboardById(String id, String projectName) {
        String url = String.format("v1/%s/dashboard/%s", projectName, id);
        return defaultClient.delete(url);
    }

    private RequestSpecification getDefaultReportPortalSpecification() {
        var properties = PropertiesHolder.getInstance().getConfigProperties();
        var baseUrl = properties.apiUrl();
        var token = properties.token();
        return RestAssured.given()
                .headers("accept", "*/*",
                        "Authorization", "bearer" + token,
                        "Content-Type", "application/json")
                .baseUri(baseUrl);
    }
}
