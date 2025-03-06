package core.util;

import core.driver.WebDriverHolder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class UiWait {

    public static final Duration WAIT_TIMEOUT_SECONDS = Duration.ofSeconds(10);

    public static WebElement waitForElementLocatedBy(WebDriver driver, By by) {
        return new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void waitForElement(WebDriver driver, WebElement webElement) {
        new WebDriverWait(driver, WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.visibilityOf(webElement));
    }

    public <T> T until(Function<WebDriver, T> func, String message, Object... args) {
        return getWait().until(func);
    }

    public FluentWait<WebDriver> getWait() {
        return WebDriverHolder.getInstance().getWebDriverWait();
    }
}
