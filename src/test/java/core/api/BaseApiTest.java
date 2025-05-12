package core.api;

import core.BaseTest;
import core.api.api_client.CustomResponse;
import core.utils.SlackService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.Optional;

public class BaseApiTest extends BaseTest {

    protected final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    protected final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    SlackService slackService = new SlackService();

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    @SneakyThrows
    public void setup() {
        createdDashboard.remove();
        slackService.postNotification("The test has been started");
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    @SneakyThrows
    public void tearDown() {
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardById(createdDashboard.get());
            slackService.postNotification("The test has been finished");
        }
    }

    protected void extractAndSetCreatedDashboard(CustomResponse response){
        Optional.ofNullable(response.getFiledValueFromJson("id")).ifPresent(createdDashboard::set);
    }

}
