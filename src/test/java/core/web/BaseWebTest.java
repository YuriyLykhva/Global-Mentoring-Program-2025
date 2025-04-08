package core.web;

import core.BaseTest;
import core.api.RestAssured.ReportPortalApiClient;
import core.config.ConfigProperties;
import core.config.PropertiesHolder;
import core.driver.RunType;
import core.driver.WebConfiguration;
import core.driver.WebDriverHolder;
import core.utils.BrowserActions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseWebTest extends BaseTest {
    private final WebConfiguration webConfiguration;
    protected final BrowserActions browserActions;
    private final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    protected final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    public BaseWebTest() {
        this.webConfiguration = new WebConfiguration();
        this.browserActions = new BrowserActions();
        loadConfigurationFromFile();
    }

    private void loadConfigurationFromFile() {
        ConfigProperties config = PropertiesHolder.getInstance().getConfigProperties();
        webConfiguration.setBrowserName(config.browser());
        webConfiguration.setToken(config.apiKey());
        webConfiguration.setRunType(RunType.valueOf(config.runType()));
        webConfiguration.setBrowserVersion(config.browserVersion());
        webConfiguration.setLocalUrl(config.rpUrl());
        webConfiguration.setRemoteUrl(config.webDriverRemoteUrl());
        webConfiguration.setTimeOutSeconds(config.timeOutSeconds());
        webConfiguration.setPollingTimeOutMilliSeconds(config.pollingTimeOutMilliSeconds());
        webConfiguration.setReadTimeOutSeconds(config.readTimeOutSeconds());
    }

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void beforeEachMethodSetupWeb() {
        WebDriverHolder.getInstance().setDriver(webConfiguration);
        createdDashboard.remove();
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void afterEachMethodTearDownWeb() {
        WebDriverHolder.getInstance().tearDown();
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardByName(createdDashboard.get());
        }
    }
}
