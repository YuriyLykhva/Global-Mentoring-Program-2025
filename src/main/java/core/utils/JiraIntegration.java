package core.utils;

import core.config.PropertiesHolder;
import okhttp3.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;

public class JiraIntegration {
    private static final Logger LOGGER = LoggerFactory.getLogger(JiraIntegration.class);

    private static final String JIRA_URL = "https://yuriy-lykhva.atlassian.net";
    private static final String JIRA_API_BASE_URL = JIRA_URL + "/rest/api/3";
    private static final String USERNAME = "yuriy.lykhva@gmail.com";
    private static final String API_TOKEN = PropertiesHolder.getInstance().getConfigProperties().jiraKey();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    private static final String AUTHENTICATION_HEADER;

    static {
        String credentials = USERNAME + ":" + API_TOKEN;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        AUTHENTICATION_HEADER = "Basic " + encodedCredentials;
    }

    public static void updateTestStatus(String status, String testCaseId, String errorMessage) {
        LOGGER.info("Updating test case ID: {} with status: {}", testCaseId, status);

        String transitionId = getTransitionId(status, testCaseId);
        if (transitionId == null) {
            LOGGER.error("Could not find transition ID for status: {}", status);
            return;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("transition", new JSONObject().put("id", transitionId));

            RequestBody body = RequestBody.create(json.toString(), JSON);
            String url = JIRA_API_BASE_URL + "/issue/" + testCaseId + "/transitions";

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Authorization", AUTHENTICATION_HEADER)
                    .header("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    LOGGER.info("Successfully updated test case {} to status: {} . Jira response: {}", testCaseId, status, response.body().string());
                    if (status.equalsIgnoreCase("Failed") && errorMessage != null && !errorMessage.isEmpty()) {
                        addCommentToIssue(testCaseId, errorMessage);
                    }
                } else {
                    String errorResponse = response.body().string();
                    LOGGER.error("Failed to update test case {}. Status code: {}. Jira response: {}", testCaseId, response.code(), errorResponse);
                    addCommentToIssue(testCaseId, "Failed to update status. Status code: " + response.code() + ".  Jira response: " + errorResponse);
                }
            } catch (IOException e) {
                LOGGER.error("Error during API call: {}", e.getMessage(), e);
                addCommentToIssue(testCaseId, "Error during API call: " + e.getMessage());
            }

        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred: {}", e.getMessage(), e);
            addCommentToIssue(testCaseId, "An unexpected error occurred: " + e.getMessage());
        }
    }


    private static String getTransitionId(String status, String testCaseId) {
        String url = JIRA_API_BASE_URL + "/issue/" + testCaseId + "/transitions";
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .header("Authorization", AUTHENTICATION_HEADER)
                    .header("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorResponse = response.body().string();
                    LOGGER.error("Failed to retrieve transitions. Status code: {}. Jira response: {}", response.code(), errorResponse);
                    return null;
                }

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                org.json.JSONArray transitions = jsonResponse.getJSONArray("transitions");

                for (int i = 0; i < transitions.length(); i++) {
                    JSONObject transition = transitions.getJSONObject(i);
                    String transitionName = transition.getString("name");
                    if (transitionName.equalsIgnoreCase(status)) {
                        return transition.getString("id");
                    }
                }
                LOGGER.warn("Transition not found for status: {}", status);
                return null;
            } catch (IOException e) {
                LOGGER.error("Error fetching transitions: {}", e.getMessage(), e);
                return null;
            }
        } catch (Exception e) {
            LOGGER.error("Error in getTransitionId: {}", e.getMessage(), e);
            return null;
        }
    }

    public static void addCommentToIssue(String issueIdOrKey, String comment) {
        try {
            JSONObject json = new JSONObject();
            JSONObject body = new JSONObject();
            body.put("type", "doc");
            body.put("version", 1);
            org.json.JSONArray contentArray = new org.json.JSONArray();
            JSONObject paragraph = new JSONObject();
            paragraph.put("type", "paragraph");
            org.json.JSONArray textArray = new org.json.JSONArray();
            JSONObject text = new JSONObject();
            text.put("type", "text");
            text.put("text", comment);
            textArray.put(text);
            paragraph.put("content", textArray);
            contentArray.put(paragraph);
            body.put("content", contentArray);
            json.put("body", body);

            RequestBody requestBody = RequestBody.create(json.toString(), JSON);
            String url = JIRA_API_BASE_URL + "/issue/" + issueIdOrKey + "/comment";
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .header("Authorization", AUTHENTICATION_HEADER)
                    .header("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    LOGGER.info("Successfully added comment to issue {} . Jira response: {}", issueIdOrKey, response.body().string());
                } else {
                    String errorResponse = response.body().string();
                    LOGGER.error("Failed to add comment to issue {}. Status code: {}. Jira response: {}", issueIdOrKey, response.code(), errorResponse);
                }
            } catch (IOException e) {
                LOGGER.error("Error adding comment: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            LOGGER.error("Error in addCommentToIssue: {}", e.getMessage(), e);
        }
    }
}

