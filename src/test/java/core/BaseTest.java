package core;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import core.utils.SlackService;
import core.utils.TestListener;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class, ReportPortalTestNGListener.class})
@ExtendWith(ReportPortalExtension.class)
public class BaseTest {
    public SlackService slackService = new SlackService();

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    @SneakyThrows
    public void setup() {
        slackService.postNotification("The test has been started");
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    @SneakyThrows
    public void tearDown() {
        slackService.postNotification("The test has been finished");
    }

}