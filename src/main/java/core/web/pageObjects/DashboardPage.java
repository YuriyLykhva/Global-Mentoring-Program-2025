package core.web.pageObjects;

import core.config.PropertiesHolder;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage extends BaseReportPortalPage {
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardPage.class);

    private final By widgetHandle = By.cssSelector(".widgetHeader__widget-header--ZGtj9");

    private static final String DASHBOARD_PATH = "/ui/#%s/dashboard/1049";

    public DashboardPage(WebDriver driver) {
        super(driver);
        if (!useSelenide) {
            PageFactory.initElements(driver, this);
        }
    }

    @Override
    public String pathUrl() {
        return DASHBOARD_PATH;
    }

    @Override
    public DashboardPage openPage(String... params) {
        LOGGER.info("Dashboard GREEN-50 opens");
        if (params.length < 1) {
            params = new String[]{PropertiesHolder.getInstance().getConfigProperties().defaultRpProjectName()};
        }
        super.openPage(params);
        if (useSelenide) {
            $x("(//div[contains(@class, 'react-grid-item')])[1]").shouldBe(visible);
        } else {
            browserActions.waitUntilElementBeVisible(widgetHandle);
        }
        return this;
    }

    public DashboardPage dragWidgetByOffset(int xOffset, int yOffset) {
        LOGGER.info("Dragging widget by offset: x={}, y={}", xOffset, yOffset);
        browserActions.dragAndDropByOffset(widgetHandle, xOffset, yOffset);
        return this;
    }

    public boolean isWidgetMovedSuccessfully(int initialX, int initialY) {
        Point newPosition;
        if (useSelenide) {
            newPosition = $(widgetHandle).getLocation();
        } else {
            newPosition = browserActions.getWebElement(widgetHandle).getLocation();
        }

        int newX = newPosition.getX();
        int newY = newPosition.getY();

        return newX != initialX || newY != initialY;
    }

    public Point getWidgetLocation() {
        if (useSelenide) {
            return $(widgetHandle).getLocation();
        } else {
            return browserActions.getWebElement(widgetHandle).getLocation();
        }
    }
}
