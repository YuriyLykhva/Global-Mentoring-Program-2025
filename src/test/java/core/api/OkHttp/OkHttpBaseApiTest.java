package core.api.OkHttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.BaseTest;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.Map;

public class OkHttpBaseApiTest extends BaseTest {

    protected final ConfigProperties configProperties;
    protected final String defaultProject;
    protected final ThreadLocal<String> createdDashboard = new ThreadLocal<>();
    protected final OkHttpClient client = new OkHttpClient();
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final MediaType JSON = MediaType.parse("application/json");
    protected final String baseUrl;
    protected final String token;

    public OkHttpBaseApiTest() {
        configProperties = PropertiesHolder.getInstance().getConfigProperties();
        defaultProject = configProperties.defaultRpProjectName();
        baseUrl = configProperties.rpUrl() + "/api/v1";
        token = configProperties.apiKey();
    }

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        createdDashboard.remove();
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (createdDashboard.get() != null) {
            deleteDashboardById(createdDashboard.get());
        }
    }

    public Response deleteDashboardById(String id) {
        return deleteDashboardById(id, defaultProject);
    }

    public Response deleteDashboardById(String id, String projectName) {
        String endpoint = String.format("/%s/dashboard/%s", projectName, id);
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .delete()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer" + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Status Code: " + response.code());
        } catch (IOException e) {
            throw new RuntimeException("API call failed", e);
        }
        return null;
    }

    protected void extractAndSetCreatedDashboard(String responseBody) {
        Map responseMap;
        try {
            responseMap = objectMapper.readValue(responseBody, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String id = responseMap.get("id").toString();
        createdDashboard.set(id);
    }

}
