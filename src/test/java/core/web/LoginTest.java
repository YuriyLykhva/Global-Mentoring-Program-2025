package core.web;

import core.annotations.JiraId;
import core.driver.WebDriverHolder;
import core.model.User;
import core.utils.JiraIntegration;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.Objects;

public class LoginTest extends BaseWebTest {

    @Test
    @org.testng.annotations.Test
    @JiraId("KAN-5")
    public void loginTest() {
        String testCaseId = "KAN-5";
        JiraIntegration.updateTestStatus("Running", testCaseId, "Test execution started.");

        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        AllDashboardsPage dashboardPage = new AllDashboardsPage(WebDriverHolder.getInstance().getWebDriver());

        loginPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        dashboardPage.openPage();

        String allDashboardsTitle = dashboardPage.getAllDashboardsTitle();

        // Jira integration
        if (Objects.equals(allDashboardsTitle, "ALL DASHBOARDS1")) {
            JiraIntegration.updateTestStatus("Passed", testCaseId, "User is logged in successfully.");
        } else {
            String errorMessage = "User is not logged in!";
            JiraIntegration.updateTestStatus("Failed", testCaseId, errorMessage);
        }

        Assert.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
        Assertions.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
    }
}