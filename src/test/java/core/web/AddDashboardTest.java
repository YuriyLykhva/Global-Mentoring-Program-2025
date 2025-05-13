package core.web;

import core.driver.WebDriverHolder;
import core.model.User;
import core.utils.JiraIntegration;
import core.utils.RandomStringGenerator;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class AddDashboardTest extends BaseWebTest {

    @Test
    @org.testng.annotations.Test
    public void addDashboardTest() {
        String testCaseId = "KAN-2";
        JiraIntegration.updateTestStatus("Running", testCaseId, "Test execution started.");

        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        AllDashboardsPage dashboardPage = new AllDashboardsPage(WebDriverHolder.getInstance().getWebDriver());

        String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
        createdDashboard.set(targetDashboardName);

        loginPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        dashboardPage.openPage();

        List<WebElement> dashboardListAfterAddingNew =
                dashboardPage
                        .clickAddDashboardButton()
                        .typeDashboardName(targetDashboardName)
                        .clickCreateButton()
                        .returnToDashboardPage()
                        .getDasboardsList();

        boolean isDashboardCreated = dashboardListAfterAddingNew.stream()
                .map(WebElement::getText)
                .anyMatch(dashboardName -> dashboardName.contains(targetDashboardName));

        // Jira integration
        if (isDashboardCreated) {
            JiraIntegration.updateTestStatus("Passed", testCaseId, "Dashboard added successfully.");
        } else {
            String errorMessage = "Failed to add dashboard.";
            JiraIntegration.updateTestStatus("Failed", testCaseId, errorMessage);
        }

        Assert.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
        Assertions.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
    }

}
