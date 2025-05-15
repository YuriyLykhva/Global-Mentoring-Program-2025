package core.utils;

import core.api.api_client.CustomRequestData;
import core.api.api_client.IApiClient;
import core.api.api_client.OkHttpApiClient;
import core.api.api_client.RestAssuredApiClient;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import core.meta.HttpClientType;
import core.model.SlackMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SlackService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlackService.class);
    private static final String slackKey;
    private static final String slackBaseUrl;
    private final IApiClient apiClient;

    static {
        ConfigProperties config = PropertiesHolder.getInstance().getConfigProperties();
        slackKey = config.slackKey();
        slackBaseUrl = config.slackBaseWebHookUrl();
    }

    public SlackService() {
        ConfigProperties configProperties = PropertiesHolder.getInstance().getConfigProperties();
        HttpClientType clientType = configProperties.httpClientType();
        CustomRequestData customRequestData = new CustomRequestData();
        customRequestData.setBaseUri(slackBaseUrl + "/services/");
        customRequestData.setHeaders(Map.of(
                "Content-Type", "application/json",
                "Charset", "utf-8"
        ));

        LOGGER.info("Initializing SlackService with HTTP client: {}", clientType);

        switch (clientType) {
            case REST_ASSURED -> apiClient = new RestAssuredApiClient(customRequestData);
            case OK_HTTP -> apiClient = new OkHttpApiClient(customRequestData);
            default -> throw new IllegalArgumentException("Unsupported http client type: " + clientType);
        }
    }

    public void postNotification(String message) {
        apiClient.post(slackKey, new SlackMessage(message));
    }
}