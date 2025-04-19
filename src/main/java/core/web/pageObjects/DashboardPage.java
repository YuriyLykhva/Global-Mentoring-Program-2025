package core.web.pageObjects;

import com.codeborne.selenide.SelenideElement;
import core.config.PropertiesHolder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
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
        if (params.length < 1) {
            //if we have no project specified -> then pick up default
            params = new String[]{PropertiesHolder.getInstance().getConfigProperties().defaultRpProjectName()};
        }
        super.openPage(params);

        if (useSelenide) {
            $(allDashboardsTitle).shouldBe(visible);
        } else {
            waitForElementLocatedBy(driver, allDashboardsTitle);
        }
        return this;
    }

    public String getAllDashboardsTitle() {
        if (useSelenide) {
            return $(allDashboardsTitle).getText();
        } else {
            return browserActions.getText(allDashboardsTitle);
        }
    }

    public DashboardPage clickAddDashboardButton() {
        if (useSelenide) {
            $(addNewDashboardButton).click();
        } else {
            browserActions.click(addNewDashboardButton);
        }
        return this;
    }

    public DashboardPage typeDashboardName(String testDashboard) {
        if (useSelenide) {
            $(dashboardNameField).setValue(testDashboard);
        } else {
            browserActions.inputText(dashboardNameField, testDashboard);
        }
        return this;
    }

    public DashboardPage clickCreateButton() {
        if (useSelenide) {
            $(createButton).click();
        } else {
            browserActions.click(createButton);
        }
        return this;
    }

    public DashboardPage returnToDashboardPage() {
        if (useSelenide) {
            $(allDashboardsPage).shouldBe(visible).click();
        } else {
            browserActions.waitUntilElementBeVisible(allDashboardsPage);
            browserActions.click(allDashboardsPage);
        }
        return this;
    }

    public List<WebElement> getDasboardsList() {
        if (useSelenide) {
            return $$(dashboardsList).stream()
                    .map(SelenideElement::toWebElement)
                    .toList();
        } else {
            try {
                return browserActions.waitUntilElementsListIsNotEmpty(dashboardsList);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public DashboardPage deleteDashboardByName(String dashboardName) {
        String deleteDashboardByNameButton =
                "//*[text()='" + dashboardName + "']//following-sibling::div//i[contains(@class, 'icon__icon-delete')]";
        By deleteButtonLocator = By.xpath(deleteDashboardByNameButton);

        if (useSelenide) {
            $x(deleteDashboardByNameButton).click();
            $(confirmDeleteButton).click();
        } else {
            browserActions.jsClick(deleteButtonLocator);
            browserActions.click(confirmDeleteButton);
        }
        return this;
    }

}
