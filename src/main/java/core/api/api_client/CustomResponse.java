package core.api.api_client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Map;

public class CustomResponse {
    private final int httpCode;
    private String responseBodyString;

    public CustomResponse(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getStatusCode() {
        return httpCode;
    }

    public String getResponseBodyString() {
        return responseBodyString;
    }

    @SneakyThrows
    public <T> T getResponseBodyAsObject(Class<T> clazz) {
        return new ObjectMapper().readValue(responseBodyString, clazz);
    }

    public void setResponseBodyString(String responseBodyString) {
        this.responseBodyString = responseBodyString;
    }

    @SneakyThrows
    public String getFiledValueFromJson(String filedName) {
        Map<String, Object> responseMap = new ObjectMapper().readValue(responseBodyString, Map.class);
        Object result = responseMap.get(filedName);
        return result != null ? result.toString() : "";
    }
}
