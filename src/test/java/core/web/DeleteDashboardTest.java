package core.web;

import core.api.ReportPortalApiClient;
import core.driver.WebDriverHolder;
import core.model.User;
import core.utils.JiraIntegration;
import core.utils.RandomStringGenerator;
import core.web.pageObjects.AllDashboardsPage;
import core.web.pageObjects.LoginPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.stream.Stream;

public class DeleteDashboardTest extends BaseWebTest {

    static Stream<Object[]> deleteDashboardTestDataStream1() {
        return Stream.of(
                new Object[]{RandomStringGenerator.getTargetDashboardName()},
                new Object[]{RandomStringGenerator.getTargetDashboardName()},
                new Object[]{RandomStringGenerator.getTargetDashboardName()}
        );
    }

    static Stream<Arguments> deleteDashboardTestDataStream2() {
        return Stream.of(
                Arguments.of(RandomStringGenerator.getTargetDashboardName()),
                Arguments.of(RandomStringGenerator.getTargetDashboardName()),
                Arguments.of(RandomStringGenerator.getTargetDashboardName())
        );
    }

    @DataProvider(name = "deleteDashboardTest", parallel = true)
    public Object[][] deleteDashboardTestData() {
        return new Object[][]{
                {RandomStringGenerator.getTargetDashboardName()},
                {RandomStringGenerator.getTargetDashboardName()},
                {RandomStringGenerator.getTargetDashboardName()}
        };
    }

    @ParameterizedTest
    @MethodSource("deleteDashboardTestDataStream2")//todo: deleteDashboardTestDataStream1 works as well
    @org.testng.annotations.Test(dataProvider = "deleteDashboardTest")
    public void deleteDashboardDataDrivenTest(String targetDashboardName) {
        String testCaseId = "KAN-3";
        JiraIntegration.updateTestStatus("Running", testCaseId, "Test execution started.");

        User user = User.createUser();
        LoginPage loginPage = new LoginPage(WebDriverHolder.getInstance().getWebDriver());
        AllDashboardsPage dashboardPage = new AllDashboardsPage(WebDriverHolder.getInstance().getWebDriver());

        createdDashboard.set(targetDashboardName);

        loginPage
                .openPage()
                .typeLogin(user.getLogin())
                .typePassword(user.getPassword())
                .clickLoginButton();

        dashboardPage.openPage();

        ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
        reportPortalApiClient.createDashboardWithName(targetDashboardName);
        browserActions.refreshPage();

        List<WebElement> dashboardsAfterCreationTestDashboard = dashboardPage.getDasboardsList();

        boolean isDashboardCreated = dashboardsAfterCreationTestDashboard.stream()
                .map(WebElement::getText)
                .anyMatch(dn -> dn.contains(targetDashboardName));

        Assert.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
        Assertions.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");

        dashboardPage
                .deleteDashboardByName(targetDashboardName)
                .returnToDashboardPage();

        List<WebElement> dashboardsAfterDeletionTestDashboard = dashboardPage.getDasboardsList();

        boolean isDashboardDeleted = dashboardsAfterDeletionTestDashboard.stream()
                .map(WebElement::getText)
                .anyMatch(dn -> dn.contains(targetDashboardName));

        // Jira integration
        if (isDashboardDeleted) {
            JiraIntegration.updateTestStatus("Passed", testCaseId, "Dashboard deleted successfully.");
        } else {
            String errorMessage = "Failed to delete dashboard.";
            JiraIntegration.updateTestStatus("Failed", testCaseId, errorMessage);
        }

        Assert.assertFalse(isDashboardDeleted, "The test dashboard was not deleted!");
        Assertions.assertFalse(isDashboardDeleted, "The test dashboard was not deleted!");

    }

    @Test
    @org.testng.annotations.Test(threadPoolSize = 3, invocationCount = 5, timeOut = 1000)
    public void deleteDashboardTest() {
        String testCaseId = "KAN-3";
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

        ReportPortalApiClient reportPortalApiClient = new ReportPortalApiClient();
        reportPortalApiClient.createDashboardWithName(targetDashboardName);
        browserActions.refreshPage();

        List<WebElement> dashboardsAfterCreationTestDashboard = dashboardPage.getDasboardsList();

        boolean isDashboardCreated = dashboardsAfterCreationTestDashboard.stream()
                .map(WebElement::getText)
                .anyMatch(dn -> dn.contains(targetDashboardName));

        Assert.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
        Assertions.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");

        dashboardPage
                .deleteDashboardByName(targetDashboardName)
                .returnToDashboardPage();

        List<WebElement> dashboardsAfterDeletionTestDashboard = dashboardPage.getDasboardsList();

        boolean isDashboardDeleted = dashboardsAfterDeletionTestDashboard.stream()
                .map(WebElement::getText)
                .noneMatch(dn -> dn.contains(targetDashboardName));

        // Jira integration
        if (isDashboardDeleted) {
            JiraIntegration.updateTestStatus("Passed", testCaseId, "Dashboard deleted successfully.");
        } else {
            String errorMessage = "Failed to delete dashboard.";
            JiraIntegration.updateTestStatus("Failed", testCaseId, errorMessage);
        }

        Assert.assertTrue(isDashboardDeleted, "The test dashboard was not deleted!");
        Assertions.assertTrue(isDashboardDeleted, "The test dashboard was not deleted!");

    }
}
