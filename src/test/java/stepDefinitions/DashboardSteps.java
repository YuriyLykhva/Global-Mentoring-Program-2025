package stepDefinitions;

import core.driver.WebDriverHolder;
import core.utils.RandomStringGenerator;
import core.web.pageObjects.DashboardPage;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import stepDefinitions.hooks.ApiHooks;

import java.util.List;

public class DashboardSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardSteps.class);

    DashboardPage dashboardPage = new DashboardPage(WebDriverHolder.getInstance().getWebDriver());
    String targetDashboardName = RandomStringGenerator.getTargetDashboardName();
    List<WebElement> dashboardListAfterAddingNew;

    @Given("User has API access with a valid token")
    public void userIsAuthorized() {
        LOGGER.info("User has valid API access");
    }

    @When("User creates a new dashboard with a random name")
    public void createANewDashboardWithRandomName() {
        LOGGER.info("Creating new dashboard via API with name: {}", targetDashboardName);
        ApiHooks.response = ApiHooks.reportPortalApiClient.createDashboardWithName(targetDashboardName);
        ApiHooks.extractAndSetCreatedDashboard(ApiHooks.response);
    }

    @Then("the dashboard is created successfully")
    public void theDashboardIsCreatedSuccessfully() {
        int statusCode = ApiHooks.response.getStatusCode();
        LOGGER.info("Verifying dashboard creation, expected 201, got {}", statusCode);
        Assert.assertEquals(statusCode, 201);
    }

    @And("User deletes the dashboard")
    public void userDeletesTheDashboard() {
        String createdId = ApiHooks.createdDashboard.get();
        LOGGER.info("Deleting dashboard with ID: {}", createdId);
        ApiHooks.response = ApiHooks.reportPortalApiClient.deleteDashboardById(createdId);
    }

    @Then("the dashboard is deleted successfully {int} times")
    public void theDashboardIsDeletedSuccessfully(int i) {
        int statusCode = ApiHooks.response.getStatusCode();
        LOGGER.info("Verifying dashboard deletion, expected 200, got {}, occurrence: {}", statusCode, i);
        Assert.assertEquals(statusCode, 200);
    }

    @When("User creates a new dashboard with a random name on Dashboard page")
    public void userCreatesANewDashboardWithARandomNameOnDashboardPage() {
        LOGGER.info("Creating new dashboard via UI with name: {}", targetDashboardName);
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

        LOGGER.info("Verifying UI dashboard creation for '{}': {}", targetDashboardName, isDashboardCreated);
        Assert.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
        Assertions.assertTrue(isDashboardCreated, "The new dashboard was not found in the list!");
    }

    @When("User deletes the dashboard on Dashboard page")
    public void userDeletesTheDashboardOnDashboardPage() {
        LOGGER.info("Deleting dashboard '{}' via UI", targetDashboardName);
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

        LOGGER.info("Verifying UI dashboard deletion for '{}': deleted={}", targetDashboardName, isDashboardDeleted);
        Assert.assertFalse(isDashboardDeleted, "The test dashboard was not deleted!");
    }

    @When("User creates new dashboard with {string} prefix on Dashboard page")
    public void userCreatesANewDashboardWithDashboardNamePrefixOnDashboardPage(String dashboardNamePrefix) {
        targetDashboardName = dashboardNamePrefix + "-" + RandomStringGenerator.getTargetDashboardName();
        LOGGER.info("Creating dashboard with prefix: '{}', full name: '{}'", dashboardNamePrefix, targetDashboardName);
        dashboardListAfterAddingNew =
                dashboardPage
                        .clickAddDashboardButton()
                        .typeDashboardName(targetDashboardName)
                        .clickCreateButton()
                        .returnToDashboardPage()
                        .getDasboardsList();
    }
}
