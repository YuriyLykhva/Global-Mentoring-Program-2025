package core.web.pageObjects;

import core.config.PropertiesHolder;
import org.openqa.selenium.WebDriver;

public abstract class BaseReportPortalPage extends BasePage {
    public BaseReportPortalPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String basePageUrl() {
        return PropertiesHolder.getInstance().getConfigProperties().webUrl();
    }
}
