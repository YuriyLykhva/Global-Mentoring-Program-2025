package core.web;

import core.annotations.JiraId;
import core.driver.WebDriverHolder;
import core.model.User;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseWebTest {

    @Test
    @org.testng.annotations.Test
    @JiraId("KAN-5")
    public void loginTest() {
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

        Assertions.assertEquals("ALL DASHBOARDS", allDashboardsTitle, "User is not logged in!");
    }
}