package core.web.pageObjects;

import core.config.PropertiesHolder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static core.utils.UiWait.waitForElementLocatedBy;

public class DashboardPage extends BaseReportPortalPage {

    private final By allDashboardsTitle = By.cssSelector("span[title='All Dashboards']");
    private final By addNewDashboardButton = By.xpath("//*[text()='Add New Dashboard']");
    private final By dashboardNameField = By.cssSelector("input[placeholder='Enter dashboard name']");
    private final By createButton = By.xpath("//*[text()='Add']");
    private final By allDashboardsPage = By.xpath("//*[text()='All Dashboards']");
    private final By dashboardsList = By.xpath("//*[contains(@class, 'gridRow__grid-row-wrapper')]");
    private final By confirmDeleteButton = By.xpath("//button[text()='Delete']");

    @Override
    public String pathUrl() {
        return "/ui/#%s/dashboard";
    }

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public DashboardPage openPage(String... params) {
        if(params.length < 1){
            //if we have no project specified -> then pick up default
            params = new String[]{PropertiesHolder.getInstance().getConfigProperties().defaultRpProjectName()};
        }
        super.openPage(params);
        waitForElementLocatedBy(driver, allDashboardsTitle);
        return this;
    }

    public String getAllDashboardsTitle() {
        return browserActions.getText(allDashboardsTitle);
    }

    public DashboardPage clickAddDashboardButton() {
        browserActions.click(addNewDashboardButton);
        return this;
    }

    public DashboardPage typeDashboardName(String testDashboard) {
        browserActions.inputText(dashboardNameField, testDashboard);
        return this;
    }

    public DashboardPage clickCreateButton() {
        browserActions.click(createButton);
        return this;
    }

    public DashboardPage returnToDashboardPage() {
        browserActions.waitUntilElementBeVisible(allDashboardsPage);
        browserActions.click(allDashboardsPage);
        return this;
    }

    public List<WebElement> getDasboardsList() {
        List<WebElement> dashboards;
        try {
            dashboards = browserActions.waitUntilElementsListIsNotEmpty(dashboardsList);
        } catch (Exception e) {
            return null;
        }
        return dashboards;
    }

    public DashboardPage deleteDashboardByName(String dashboardName) {
        String deleteDashboardByNameButton =
                "//*[text()='" + dashboardName + "']//following-sibling::div//i[contains(@class, 'icon__icon-delete')]";
        browserActions.jsClick(By.xpath(deleteDashboardByNameButton));
        browserActions.click(confirmDeleteButton);
        return this;
    }

}
