package core.utils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class SlackService {

    private final OkHttpClient client;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public SlackService() {
        this.client = new OkHttpClient();
    }

    public void postNotification(String message) throws IOException {
        String json = String.format("{\"text\":\"%s\"}", message);

        RequestBody body = RequestBody.create(json, JSON);

        String webhookUrl = "https://hooks.slack.com/services/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";
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to send Slack notification: " + response);
            }
        }

    }
}