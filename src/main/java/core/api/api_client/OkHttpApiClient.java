package core.api.api_client;

import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class OkHttpApiClient implements IApiClient {
    ConfigProperties configProperties;
    protected final String token;

    public OkHttpApiClient() {
        configProperties = PropertiesHolder.getInstance().getConfigProperties();
        token = configProperties.apiKey();
    }

    public Response newCall(Request request){
        try {
            return new OkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Request buildRequest(String url, String method, RequestBody body) {
        Request.Builder builder = new Request.Builder().url(url);

        switch (method.toUpperCase()) {
            case "POST":
                builder.post(body);
                break;
            case "DELETE":
                builder.delete();
                break;
            case "PUT":
                builder.put(body);
                break;
            case "GET":
                builder.get();
                break;
            default:
                throw new IllegalArgumentException("Unsupported method: " + method);
        }

        return buildRequest(builder);
    }

    private Request buildRequest(Request.Builder builder) {
        return builder
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer" + token)
                .build();
    }

    @Override
    public CustomResponse post(String url, Object requestBody) {
        return null;
    }

    @Override
    public CustomResponse put(String url, Object requestBody) {
        return null;
    }

    @Override
    public CustomResponse get(String url) {
        return null;
    }

    @Override
    public CustomResponse delete(String url) {
        return null;
    }
}
