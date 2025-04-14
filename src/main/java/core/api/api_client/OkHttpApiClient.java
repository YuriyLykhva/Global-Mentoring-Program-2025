package core.api.api_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import okhttp3.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OkHttpApiClient implements IApiClient {
    private final OkHttpClient client = new OkHttpClient();
    private final CustomRequestData requestData;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MediaType JSON = MediaType.parse("application/json");
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpApiClient.class);

    public OkHttpApiClient(CustomRequestData customRequestData) {
        requestData = customRequestData;
    }

    @Override
    public CustomResponse post(String url, Object requestBody) {
        return sendRequest(url, "POST", requestBody);
    }

    @Override
    public CustomResponse put(String url, Object requestBody) {
        return sendRequest(url, "PUT", requestBody);
    }

    @Override
    public CustomResponse get(String url) {
        return sendRequest(url, "GET", null);
    }

    @Override
    public CustomResponse delete(String url) {
        return sendRequest(url, "DELETE", null);
    }

    @SneakyThrows
    private CustomResponse sendRequest(String path, String method, Object requestBody) {
        String fullUrl = requestData.getBaseUri() + path;
        RequestBody body = Try.of(() -> {
                    if (requestBody != null) {
                        String json = objectMapper.writeValueAsString(requestBody);
                        LOGGER.debug("Serialized request body: {}", json);
                        return RequestBody.create(json, JSON);
                    }
                    return null;
                }).onFailure(e -> LOGGER.error("Failed to serialize request body", e))
                .getOrElseThrow(e -> new RuntimeException("Failed to serialize request body", e));

        Request.Builder builder = new Request.Builder().url(fullUrl);
        addHeaders(builder, requestData.getHeaders());

        LOGGER.info("Executing {} request to URL: {}", method, fullUrl);

        switch (method.toUpperCase()) {
            case "POST" -> builder.post(body);
            case "PUT" -> builder.put(body);
            case "DELETE" -> builder.delete();
            case "GET" -> builder.get();
            default -> LOGGER.warn("Unknown HTTP method: {}", method);
        }

        Response response = client.newCall(builder.build()).execute();

        String responseString = response.body() != null ? response.body().string() : "";

        CustomResponse customResponse = new CustomResponse(response.code());
        customResponse.setResponseBodyString(responseString);

        LOGGER.info("Received response with status code: {}", response.code());
        LOGGER.debug("Response body: {}", responseString);

        return customResponse;

    }

    private void addHeaders(Request.Builder builder, Map<String, Object> headers) {
        if (headers != null) {
            headers.forEach((key, value) -> {
                LOGGER.debug("Adding header: {}={}", key, value);
                builder.addHeader(key, String.valueOf(value));
            });
        }
    }

}
