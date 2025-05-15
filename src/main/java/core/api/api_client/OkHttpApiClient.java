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
    private final CustomRequestData requestData;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final MediaType JSON = MediaType.parse("application/json");
    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpApiClient.class);

    public OkHttpApiClient(CustomRequestData customRequestData) {
        LOGGER.debug("Initializing OkHttpApiClient with base URI: {}", customRequestData.getBaseUri());
        requestData = customRequestData;
    }

    @Override
    public CustomResponse post(String url, Object requestBody) {
        LOGGER.info("Preparing POST request to: {}", url);
        return sendRequest(url, "POST", requestBody);
    }

    @Override
    public CustomResponse put(String url, Object requestBody) {
        LOGGER.info("Preparing PUT request to: {}", url);
        return sendRequest(url, "PUT", requestBody);
    }

    @Override
    public CustomResponse get(String url) {
        LOGGER.info("Preparing GET request to: {}", url);
        return sendRequest(url, "GET", null);
    }

    @Override
    public CustomResponse delete(String url) {
        LOGGER.info("Preparing DELETE request to: {}", url);
        return sendRequest(url, "DELETE", null);
    }

    @SneakyThrows
    private CustomResponse sendRequest(String path, String method, Object requestBody) {
        String fullUrl = requestData.getBaseUri() + path;
        LOGGER.debug("Composed full URL: {}", fullUrl);

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

        LOGGER.info("Sending {} request to URL: {}", method, fullUrl);

        switch (method.toUpperCase()) {
            case "POST" -> builder.post(body);
            case "PUT" -> builder.put(body);
            case "DELETE" -> builder.delete();
            case "GET" -> builder.get();
            default -> {
                LOGGER.warn("Unknown HTTP method: {}", method);
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            }
        }

        Request request = builder.build();
        LOGGER.debug("Executing HTTP request...");

        Response response = new OkHttpClient().newCall(request).execute();
        int statusCode = response.code();
        String responseString = response.body() != null ? response.body().string() : "";

        LOGGER.info("Received response with status code: {}", statusCode);
        LOGGER.debug("Raw response body: {}", responseString);

        CustomResponse customResponse = new CustomResponse(statusCode);
        customResponse.setResponseBodyString(responseString);

        LOGGER.trace("Returning CustomResponse: {}", customResponse);
        return customResponse;

    }

    private void addHeaders(Request.Builder builder, Map<String, Object> headers) {
        if (headers != null) {
            LOGGER.debug("Adding headers to request:");
            headers.forEach((key, value) -> {
                LOGGER.debug("Adding header: {}={}", key, value);
                builder.addHeader(key, String.valueOf(value));
            });
        } else {
            LOGGER.debug("No headers to add.");
        }
    }
}