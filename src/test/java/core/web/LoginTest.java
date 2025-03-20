package core.web;

import core.driver.WebDriverHolder;
import core.model.User;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class LoginTest extends BaseWebTest {

    @Test
    @org.testng.annotations.Test
    public void loginTest() {
        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        loginPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        dashboardPage.openPage();

        String allDashboardsTitle = dashboardPage.getAllDashboardsTitle();

        Assert.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
        Assertions.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
    }
}