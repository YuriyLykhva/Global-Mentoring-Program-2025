package core.api.OkHttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.api.api_client.OkHttpApiClient;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static core.api.OkHttp.ThreadLocalHolder.createdDashboard;

public class OkHttpReportPortalApiClient {
    private final String baseUrl;
    private final OkHttpApiClient defaultClient;
    private final String defaultProject;

    public OkHttpReportPortalApiClient() {
        ConfigProperties configProperties = PropertiesHolder.getInstance().getConfigProperties();
        baseUrl = configProperties.rpUrl() + "/api/v1";
        defaultClient = new OkHttpApiClient();
        defaultProject = configProperties.defaultRpProjectName();
    }

    public Response createDashboardWithName(String dashboardName) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final MediaType JSON = MediaType.parse("application/json");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", dashboardName);
        requestBody.put("description", "This is a test dashboard");

        String jsonBody, id;

        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body", e);
        }

        RequestBody body = RequestBody.create(jsonBody, JSON);
        String endpoint = String.format("/%s/dashboard", defaultProject);

        Request request = defaultClient.buildRequest(baseUrl + endpoint, "POST", body);

        Response response = defaultClient.newCall(request);
        if (response.body() != null) {
            String responseBody;
            try {
                responseBody = response.peekBody(Long.MAX_VALUE).string();
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                id = responseMap.get("id").toString();
                setCreatedDashboardId(id);
                return response;
            } catch (IOException ex) {
                throw new RuntimeException("API call failed", ex);
            }
        } else {
            throw new RuntimeException("Empty response body");
        }
    }

    public Response deleteDashboardById(String id) {
        return deleteDashboardById(id, defaultProject);
    }

    public Response deleteDashboardById(String id, String projectName) {
        String endpoint = String.format("/%s/dashboard/%s", projectName, id);
        Request request = defaultClient.buildRequest(baseUrl + endpoint, "DELETE", null);
        return defaultClient.newCall(request);
    }

    private void setCreatedDashboardId(String id) {
        createdDashboard.set(id);
    }

    public Response getAllDashboards() {
        return getAllDashboards(defaultProject);
    }

    public Response getAllDashboards(String projectName) {
        String endpoint = String.format("/%s/dashboard", projectName);
        Request request = defaultClient.buildRequest(baseUrl + endpoint, "GET", null);
        return defaultClient.newCall(request);
    }

    public Response getDashboardById(String id) {
        return getDashboardById(id, defaultProject);
    }

    public Response getDashboardById(String id, String projectName) {
        String endpoint = String.format("/%s/dashboard/%s", projectName, id);
        Request request = defaultClient.buildRequest(baseUrl + endpoint, "GET", null);
        return defaultClient.newCall(request);
    }

    public Response updateDashboardById(String id, String targetDashboardName, String updatedDescription) {
        return updateDashboardById(id, defaultProject, targetDashboardName, updatedDescription);
    }

    public Response updateDashboardById(String id, String projectName, String targetDashboardName, String updatedDescription) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final MediaType JSON = MediaType.parse("application/json");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", targetDashboardName);
        requestBody.put("description", updatedDescription);

        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body", e);
        }

        String endpoint = String.format("/%s/dashboard/%s", projectName, id);
        RequestBody body = RequestBody.create(jsonBody, JSON);
        Request request = defaultClient.buildRequest(baseUrl + endpoint, "PUT", body);

        return defaultClient.newCall(request);
    }
}
