package core.web.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BasePage {

    private final String allDashboardsTitle = "span[title='All Dashboards']";

    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getAllDashboardsTitle() {
        return browserActions.getText(By.cssSelector(allDashboardsTitle));
    }
}
