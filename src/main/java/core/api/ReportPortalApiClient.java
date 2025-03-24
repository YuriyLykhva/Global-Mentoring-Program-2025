package core.api;

import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        String url = String.format("/%s/dashboard", projectName);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "This is a test dashboard");
        requestBody.put("name", dashboardName);
        return defaultClient.post(url, requestBody);
    }

    public Response getAllDashboards() {
        return getAllDashboards(defaultProject);
    }

    public Response getAllDashboards(String projectName) {
        String url = String.format("/%s/dashboard", projectName);
        return defaultClient.get(url);
    }

    public Response deleteDashboardById(String id) {
        return deleteDashboardById(id, defaultProject);
    }

    public Response deleteDashboardById(String id, String projectName) {
        String url = String.format("/%s/dashboard/%s", projectName, id);
        return defaultClient.delete(url);
    }

    public void deleteDashboardByName(String dashboardByName) {
        deleteDashboardByName(dashboardByName, defaultProject);
    }

    public void deleteDashboardByName(String dashboardByName, String projectName) {
        var allDashboards = getAllDashboards(projectName);
        Optional.ofNullable(allDashboards.jsonPath().get(String.format("content.find { it.name == '%s' }.id", dashboardByName))).ifPresent(id -> {
            deleteDashboardById(id.toString(), projectName);
        });
    }

    private RequestSpecification getDefaultReportPortalSpecification() {
        var properties = PropertiesHolder.getInstance().getConfigProperties();
        var baseUrl = properties.rpUrl() + "/api/v1";
        var token = properties.apiKey();
        return RestAssured.given()
                .headers("accept", "*/*",
                        "Authorization", "bearer" + token,
                        "Content-Type", "application/json")
                .baseUri(baseUrl);
    }
}
