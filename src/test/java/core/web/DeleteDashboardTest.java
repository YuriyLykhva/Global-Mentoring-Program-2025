package core.web;

import core.api.ApiClient;
import core.driver.RunType;
import core.driver.WebDriverHolder;
import core.model.User;
import core.utils.RandomStringGenerator;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class DeleteDashboardTest extends BaseTest {

    @Test
    @org.testng.annotations.Test
    public void deleteDashboardTest() {
        final String homePageURL = webConfiguration.getRunType() == RunType.LOCAL ?
                webConfiguration.getLocalUrl() :
                webConfiguration.getRemoteUrl();
        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();

        loginPage
                .openPage(homePageURL)
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        List<WebElement> initialDashboardList = dashboardPage.getDasboardsList();
        int initialDashboardListSize = null == initialDashboardList ? 0 : initialDashboardList.size();

        ApiClient apiClient = new ApiClient();
        apiClient.createDashboardWithName(targetDashboardName);

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