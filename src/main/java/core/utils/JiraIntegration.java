package core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.config.PropertiesHolder;
import core.model.CommentPayload;
import io.vavr.control.Try;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Base64;
import core.api.api_client.CustomRequestData;
import core.api.api_client.IApiClient;
import core.api.api_client.OkHttpApiClient;
import core.api.api_client.RestAssuredApiClient;
import core.config.ConfigProperties;
import core.meta.HttpClientType;
import core.api.api_client.CustomResponse;
import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;

public class JiraIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(JiraIntegration.class);
    private static final ConfigProperties CONFIG = PropertiesHolder.getInstance().getConfigProperties();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String JIRA_URL = CONFIG.jiraUrl();
    private static final String JIRA_API_BASE_URL = JIRA_URL + "/rest/api/3";
    private static final String USERNAME = CONFIG.jiraUsername();
    private static final String API_TOKEN = CONFIG.jiraKey();

    private static final IApiClient apiClient;

    static {
        String credentials = USERNAME + ":" + API_TOKEN;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        String authHeader = "Basic " + encodedCredentials;

        CustomRequestData customRequestData = new CustomRequestData();
        customRequestData.setBaseUri(JIRA_API_BASE_URL);
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", authHeader);
        headers.put("Content-Type", "application/json");
        customRequestData.setHeaders(headers);

        HttpClientType clientType = CONFIG.httpClientType();
        LOGGER.info("Initializing JiraIntegration with HTTP client: {}", clientType);
        apiClient = switch (clientType) {
            case OK_HTTP -> new OkHttpApiClient(customRequestData);
            case REST_ASSURED -> new RestAssuredApiClient(customRequestData);
            default -> throw new IllegalArgumentException("Unsupported http client type: " + clientType);
        };
    }

    public static void updateTestStatus(String status, String testCaseId, String errorMessage) {
        LOGGER.info("Updating test case ID: {} with status: {}", testCaseId, status);
        if (testCaseId == null || testCaseId.isEmpty()) {
            LOGGER.warn("No Jira test case is linked.");
            return;
        }

        String transitionId = getTransitionId(status, testCaseId);
        if (transitionId == null) {
            LOGGER.error("Could not find transition ID for status: {}", status);
            return;
        }

        Try.run(() -> {
            JSONObject json = new JSONObject().put("transition", new JSONObject().put("id", transitionId));
            String path = "/issue/" + testCaseId + "/transitions";

            CustomResponse response = apiClient.post(path, json.toString());

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                LOGGER.info("Successfully updated test case {} to status {}. Response: {}", testCaseId, status, response.getResponseBodyString());
                if ("Failed".equalsIgnoreCase(status) && errorMessage != null && !errorMessage.isEmpty()) {
                    addCommentToIssue(testCaseId, errorMessage);
                }
            } else {
                LOGGER.error("Failed to update status. Status code: {}. Response: {}", response.getStatusCode(), response.getResponseBodyString());
                addCommentToIssue(testCaseId, "Failed to update status. Response: " + response.getResponseBodyString());
            }
        }).onFailure(e -> {
            LOGGER.error("Error updating test case {}: {}", testCaseId, e.getMessage(), e);
            addCommentToIssue(testCaseId, "Error during update: " + e.getMessage());
        });
    }

    private static String getTransitionId(String status, String testCaseId) {
        String path = "/issue/" + testCaseId + "/transitions";

        return Try.of(() -> {
                    CustomResponse response = apiClient.get(path);
                    if (response.getStatusCode() >= 300) {
                        LOGGER.error("Failed to retrieve transitions. Status: {}, Body: {}", response.getStatusCode(), response.getResponseBodyString());
                        return null;
                    }

                    JSONObject json = new JSONObject(response.getResponseBodyString());
                    JSONArray transitions = json.getJSONArray("transitions");

                    for (int i = 0; i < transitions.length(); i++) {
                        JSONObject transition = transitions.getJSONObject(i);
                        if (status.equalsIgnoreCase(transition.getString("name"))) {
                            return transition.getString("id");
                        }
                    }

                    LOGGER.warn("Transition not found for status: {}", status);
                    return null;
                }).onFailure(e -> LOGGER.error("Error getting transition ID for {}: {}", testCaseId, e.getMessage(), e))
                .getOrNull();
    }

    public static void addCommentToIssue(String issueIdOrKey, String comment) {
        Try.run(() -> {
            CommentPayload payload = new CommentPayload(comment);
            String path = "/issue/" + issueIdOrKey + "/comment";
            CustomResponse response = apiClient.post(path, payload);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                LOGGER.info("Successfully added comment to issue {}. Response: {}", issueIdOrKey, response.getResponseBodyString());
            } else {
                LOGGER.error("Failed to add comment to issue {}. Status code: {}, Response: {}", issueIdOrKey, response.getStatusCode(), response.getResponseBodyString());
            }
        }).onFailure(e -> LOGGER.error("Error adding comment to issue {}: {}", issueIdOrKey, e.getMessage(), e));
    }
}

