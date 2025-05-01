package core.web;

import core.driver.WebDriverHolder;
import core.model.User;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class ResizeWidgetTest extends BaseWebTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResizeWidgetTest.class);

    @Test
    @org.testng.annotations.Test
    public void resizeWidgetTest() {
        int xOffset = 200;
        int yOffset = 150;
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

        Dimension initialSize = dashboardPage.getWidgetSize();
        dashboardPage.resizeWidgetByOffset(xOffset, yOffset);
        LOGGER.info("Widget is increased by {} px on X axis and {} px on Y axis", xOffset, yOffset);

        boolean isResized = dashboardPage.isWidgetResizedSuccessfully(initialSize);

        Assert.assertTrue(isResized, "Widget was not resized!");
        Assertions.assertTrue(isResized, "Widget was not resized!");

        dashboardPage.resizeWidgetByOffset(-xOffset, -yOffset);
        LOGGER.info("Widget is decreased by {} px on X axis and {} px on Y axis", xOffset, yOffset);
    }
}