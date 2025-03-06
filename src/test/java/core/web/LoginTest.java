package core.web;

import core.driver.RunType;
import core.driver.WebDriverHolder;
import core.model.User;
import core.web.pageObjects.DashboardPage;
import core.web.pageObjects.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {
        logger.info("Starting login test...");
        final String homePageURL = webConfiguration.getRunType() == RunType.LOCAL ?
                webConfiguration.getLocalUrl() :
                webConfiguration.getRemoteUrl();

        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());

        loginPage
                .openPage(homePageURL)
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        String allDashboardsTitle = dashboardPage.getAllDashboardsTitle();

        Assert.assertEquals(allDashboardsTitle, "ALL DASHBOARDS", "User is not logged in!");
        logger.info("login test passed successfully!");

    }
}