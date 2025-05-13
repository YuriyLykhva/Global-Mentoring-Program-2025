package core.web;

import core.driver.WebDriverHolder;
import core.model.User;
import core.utils.JiraIntegration;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DragAndDropWidgetTest extends BaseWebTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DragAndDropWidgetTest.class);

    @Test
    @org.testng.annotations.Test
    public void dragAndDropWidgetTest() {
        String testCaseId = "KAN-4";
        JiraIntegration.updateTestStatus("Running", testCaseId, "Test execution started.");

        int xOffset = 340;
        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        AllDashboardsPage allDashboardsPage = new AllDashboardsPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        loginPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        allDashboardsPage.openPage();

        dashboardPage.openPage();

        Point initialPos = dashboardPage.getWidgetLocation();
        dashboardPage.dragWidgetByOffset(xOffset, 0);
        LOGGER.info("Widget is moved to the right on {} px", xOffset);

        boolean isMoved = dashboardPage.isWidgetMovedSuccessfully(initialPos.getX(), initialPos.getY());

        // Jira integration
        if (isMoved) {
            JiraIntegration.updateTestStatus("Passed", testCaseId, "Widget was moved successfully.");
        } else {
            String errorMessage = "Failed to move widget.";
            JiraIntegration.updateTestStatus("Failed", testCaseId, errorMessage);
        }

        Assertions.assertTrue(isMoved, "Widget was not moved!");

        dashboardPage.dragWidgetByOffset(-xOffset, 0);
        LOGGER.info("Widget is moved to the left on {} px", xOffset);

    }
}
