package core.utils;

import core.config.PropertiesHolder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SlackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);

    private final OkHttpClient client;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public SlackService() {
        this.client = new OkHttpClient();
    }

    public void postNotification(String message) throws IOException {
        String json = String.format("{\"text\":\"%s\"}", message);
        String slackKey = PropertiesHolder.getInstance().getConfigProperties().slackKey();

        RequestBody body = RequestBody.create(json, JSON);

        String webhookUrl = "https://hooks.slack.com/services/" + slackKey;
        Request request = new Request.Builder()
                .url(webhookUrl)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                LOGGER.info("Failed to send Slack notification: {}", response);
                throw new IOException("Failed to send Slack notification: " + response);
            }
        }

    }
}