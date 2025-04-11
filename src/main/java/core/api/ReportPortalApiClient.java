package core.api;

import core.api.api_client.*;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import core.meta.HttpClientType;
import core.model.ReportPortalDashboardsResponse;

import java.util.HashMap;
import java.util.Map;

public class ReportPortalApiClient {
    private final IApiClient defaultClient;
    private final String defaultProject;

    public ReportPortalApiClient() {
        ConfigProperties configProperties = PropertiesHolder.getInstance().getConfigProperties();
        HttpClientType clientType = configProperties.httpClientType();
        var baseUrl = configProperties.rpUrl() + "/api/v1";
        var token = configProperties.apiKey();
        CustomRequestData customRequestData = new CustomRequestData();
        customRequestData.setBaseUri(baseUrl);
        Map<String, Object> headers = new HashMap<>();
        headers.put( "Authorization", "bearer" + token);
        headers.put( "Content-Type", "application/json");
        customRequestData.setHeaders(headers);
        switch (clientType) {
            case REST_ASSURED -> defaultClient = new RestAssuredApiClient(customRequestData);
            case OK_HTTP -> defaultClient = new OkHttpApiClient();
            default -> throw new IllegalArgumentException("Unsupported http client type: " + clientType);
        }

        defaultProject = configProperties.defaultRpProjectName();
    }

    public CustomResponse createDashboardWithName(String dashboardName) {
        return createDashboardWithName(dashboardName, defaultProject);
    }

    public CustomResponse createDashboardWithName(String dashboardName, String projectName) {
        String url = String.format("/%s/dashboard", projectName);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", "This is a test dashboard");
        requestBody.put("name", dashboardName);
        return defaultClient.post(url, requestBody);
    }

    public CustomResponse updateDescriptionOfDashboardById(String id) {
        return updateDescriptionOfDashboardById(id, defaultProject);
    }

    public CustomResponse updateDescriptionOfDashboardById(String id, String projectName) {
        String description = getDashboardById(id).getFiledValueFromJson("description") + " - updated";
        String name = getDashboardById(id).getFiledValueFromJson("name");
        String url = String.format("/%s/dashboard/%s", projectName, id);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("description", description);
        requestBody.put("name", name);
        return defaultClient.put(url, requestBody);
    }

    public CustomResponse getAllDashboards() {
        return getAllDashboards(defaultProject);
    }

    public CustomResponse getAllDashboards(String projectName) {
        String url = String.format("/%s/dashboard", projectName);
        return defaultClient.get(url);
    }

    public CustomResponse getDashboardById(String id) {
        return getDashboardById(id, defaultProject);
    }

    public CustomResponse getDashboardById(String id, String projectName) {
        String url = String.format("/%s/dashboard/%s", projectName, id);
        return defaultClient.get(url);
    }

    public CustomResponse deleteDashboardById(String id) {
        return deleteDashboardById(id, defaultProject);
    }

    public CustomResponse deleteDashboardById(String id, String projectName) {
        String url = String.format("/%s/dashboard/%s", projectName, id);
        return defaultClient.delete(url);
    }

    public void deleteDashboardByName(String dashboardByName) {
        deleteDashboardByName(dashboardByName, defaultProject);
    }

    public void deleteDashboardByName(String dashboardByName, String projectName) {
        var allDashboards = getAllDashboards(projectName);
        ReportPortalDashboardsResponse dashboards = allDashboards.getResponseBodyAsObject(ReportPortalDashboardsResponse.class);

        dashboards.content.stream().filter(d -> dashboardByName.equalsIgnoreCase(d.name)).findFirst()
                .ifPresent(id -> deleteDashboardById(id.toString(), projectName));
    }
}
