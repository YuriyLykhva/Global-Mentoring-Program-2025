package stepDefinitions;

import core.driver.WebDriverHolder;
import core.utils.RandomStringGenerator;
import core.web.pageObjects.DashboardPage;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import stepDefinitions.hooks.ApiHooks;

import java.util.List;

public class DashboardSteps {
    DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());
    String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
    List<WebElement> dashboardListAfterAddingNew;

    @Given("User has API access with a valid token")
    public void userIsAuthorized() {
        System.out.println("User has valid API");
    }

    @When("User creates a new dashboard with a random name")
    public void createANewDashboardWithRandomName() {
        ApiHooks.response = ApiHooks.reportPortalApiClient.createDashboardWithName(targetDashboardName);
        ApiHooks.extractAndSetCreatedDashboard(ApiHooks.response);
    }

    @Then("the dashboard is created successfully")
    public void theDashboardIsCreatedSuccessfully() {
        Assert.assertEquals(ApiHooks.response.getStatusCode(), 201);
    }

    @And("User deletes the dashboard")
    public void userDeletesTheDashboard() {
        String createdId = ApiHooks.createdDashboard.get();
        ApiHooks.response = ApiHooks.reportPortalApiClient.deleteDashboardById(createdId);
    }

    @Then("the dashboard is deleted successfully {int} times")
    public void theDashboardIsDeletedSuccessfully(int i) {
        System.out.println("Dashboard is deleted successfully " + i + " times");
        Assert.assertEquals(ApiHooks.response.getStatusCode(), 200);
    }

    @When("User creates a new dashboard with a random name on Dashboard page")
    public void userCreatesANewDashboardWithARandomNameOnDashboardPage() {

        dashboardListAfterAddingNew =
                dashboardPage
                        .clickAddDashboardButton()
                        .typeDashboardName(targetDashboardName)
                        .clickCreateButton()
                        .returnToDashboardPage()
                        .getDasboardsList();
    }

    @Then("the dashboard is created successfully on Dashboard page")
    public void theDashboardIsCreatedSuccessfullyOnDashboardPage() {
        boolean isDashboardCreated = dashboardListAfterAddingNew.stream()
                .map(WebElement::getText)
                .anyMatch(dashboardName -> dashboardName.contains(targetDashboardName));

        Assert.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
        Assertions.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
    }

    @When("User deletes the dashboard on Dashboard page")
    public void userDeletesTheDashboardOnDashboardPage() {
        dashboardPage
                .deleteDashboardByName(targetDashboardName)
                .returnToDashboardPage();
    }

    @Then("the dashboard is deleted successfully on Dashboard page")
    public void theDashboardIsDeletedSuccessfullyOnDashboardPage() {
        List<WebElement> dashboardsAfterDeletionTestDashboard = dashboardPage.getDasboardsList();

        boolean isDashboardDeleted = dashboardsAfterDeletionTestDashboard.stream()
                .map(WebElement::getText)
                .anyMatch(dn -> dn.contains(targetDashboardName));

        Assert.assertFalse(isDashboardDeleted, "The test dashboard was not deleted!");
    }

    @When("User creates new dashboard with {string} prefix on Dashboard page")
    public void userCreatesANewDashboardWithDashboardNamePrefixOnDashboardPage(String dashboardNamePrefix) {
        targetDashboardName = dashboardNamePrefix + "-" + RandomStringGenerator.getTargetDashboardName();
        dashboardListAfterAddingNew =
                dashboardPage
                        .clickAddDashboardButton()
                        .typeDashboardName(targetDashboardName)
                        .clickCreateButton()
                        .returnToDashboardPage()
                        .getDasboardsList();
        System.out.println("Dashboard name: " + targetDashboardName + " is created");
    }
}
