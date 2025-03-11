package core.web;

import com.github.javafaker.Faker;
import core.api.DashboardApiTests;
import core.driver.RunType;
import core.driver.WebDriverHolder;
import core.model.User;
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

        String targetDashboardName = new Faker().color().name();

        loginPage
                .openPage(homePageURL)
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        List<WebElement> dashboardsBeforeTest = dashboardPage.getDasboardsList();

        DashboardApiTests dashboardApi = new DashboardApiTests();
        dashboardApi.createDashboardTest(targetDashboardName);

        List<WebElement> dashboardsAfterTest = dashboardPage
                .deleteDashboardByName(targetDashboardName)
                .returnToDashboardPage()
                .getDasboardsList();

        Assert.assertEquals(dashboardsAfterTest.size(),
                dashboardsBeforeTest.size(), "Dashboard is not deleted!");

        Assertions.assertEquals(dashboardsAfterTest.size(),
                dashboardsBeforeTest.size(), "Dashboard is not deleted!");

    }
}