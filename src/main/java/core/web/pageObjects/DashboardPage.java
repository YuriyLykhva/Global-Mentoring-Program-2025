package core.web.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class DashboardPage extends BaseReportPortalPage {

    private final String allDashboardsTitle = "span[title='All Dashboards']";
    private final String addNewDashboardButton = "//*[text()='Add New Dashboard']";
    private final String dashboardNameField = "input[placeholder='Enter dashboard name']";
    private final String createButton = "//*[text()='Add']";
    private final String allDashboardsPage = "//*[text()='All Dashboards']";
    private final String dashboardsList = "//*[contains(@class, 'gridRow__grid-row-wrapper')]";
    private final String confirmDeleteButton = "//button[text()='Delete']";

    @Override
    public String pathUrl() {
        return "/ui/#%s/dashboard";
    }

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public String getAllDashboardsTitle() {
        return browserActions.getText(By.cssSelector(allDashboardsTitle));
    }

    public DashboardPage clickAddDashboardButton() {
        browserActions.click(By.xpath(addNewDashboardButton));
        return this;
    }

    public DashboardPage typeDashboardName(String testDashboard) {
        browserActions.inputText(By.cssSelector(dashboardNameField), testDashboard);
        return this;
    }

    public DashboardPage clickCreateButton() {
        browserActions.click(By.xpath(createButton));
        return this;
    }

    public DashboardPage returnToDashboardPage() {
        browserActions.waitUntilElementBeVisible(By.xpath(allDashboardsPage));
        browserActions.click(By.xpath(allDashboardsPage));
        return this;
    }

    public List<WebElement> getDasboardsList() {
        List<WebElement> dashboards;
        try {
            dashboards = browserActions.waitUntilElementsListIsNotEmpty(By.xpath(dashboardsList));
        } catch (Exception e) {
            return null;
        }
        return dashboards;
    }

    public DashboardPage deleteDashboardByName(String dashboardName) {
        String deleteDashboardByNameButton =
                "//*[text()='" + dashboardName + "']//following-sibling::div//i[contains(@class, 'icon__icon-delete')]";
        browserActions.refreshPage();
        browserActions.jsClick(By.xpath(deleteDashboardByNameButton));
        browserActions.click(By.xpath(confirmDeleteButton));
        return this;
    }

}
