package core.web;

import core.api.ReportPortalApiClient;
import core.driver.WebDriverHolder;
import core.model.User;
import core.utils.RandomStringGenerator;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.List;

public class DeleteDashboardTest extends BaseWebTest {
    private final ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
    private final ThreadLocal<String> createdDashboard = new ThreadLocal<>();

    @BeforeEach
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        createdDashboard.remove();
    }

    @AfterEach
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if(createdDashboard.get() != null){
            reportPortalApiClient.deleteDashboardByName(createdDashboard.get());
        }
    }

    @Test
    @org.testng.annotations.Test
    public void deleteDashboardTest() {
        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        createdDashboard.set(targetDashboardName);

        loginPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        dashboardPage.openPage();

        List<WebElement> initialDashboardList = dashboardPage.getDasboardsList();
        int initialDashboardListSize = null == initialDashboardList ? 0 : initialDashboardList.size();

        ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
        reportPortalApiClient.createDashboardWithName(targetDashboardName);
        browserActions.refreshPage();

        List<WebElement> dashboardsAfterTest = dashboardPage
                .deleteDashboardByName(targetDashboardName)
                .returnToDashboardPage()
                .getDasboardsList();
        int afterTestDashboardListSize = null == dashboardsAfterTest ? 0 : dashboardsAfterTest.size();

        Assert.assertEquals(afterTestDashboardListSize,
                initialDashboardListSize, "Dashboard is not deleted!");

        Assertions.assertEquals(afterTestDashboardListSize,
                initialDashboardListSize, "Dashboard is not deleted!");
    }
}