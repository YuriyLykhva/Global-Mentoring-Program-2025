package core.api.OkHttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.utils.RandomStringGenerator;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class OkHttpDashboardApiTests extends OkHttpBaseApiTest {

    @Test
    @org.junit.jupiter.api.Test
    public void createDashboardTest() {
        // prepare
        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", targetDashboardName);
        requestBody.put("description", "This is a test dashboard");

        String jsonBody;

        try {
            jsonBody = objectMapper.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize request body", e);
        }

        RequestBody body = RequestBody.create(jsonBody, JSON);
        String endpoint = String.format("/%s/dashboard", defaultProject);

        int actualStatusCode;
        String responseBody = "";

        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer" + token)
                .build();

        // act
        try (Response response = client.newCall(request).execute()) {
            actualStatusCode = response.code();
            if (response.body() != null) {
                responseBody = response.body().string();
                extractAndSetCreatedDashboard(responseBody);
            }
            System.out.println("Response: " + responseBody);
            System.out.println("Status Code: " + actualStatusCode);
        } catch (IOException e) {
            throw new RuntimeException("API call failed", e);
        }

        // verify
        Assert.assertEquals(actualStatusCode, 201, "Dashboard is created successfully");
    }

}
