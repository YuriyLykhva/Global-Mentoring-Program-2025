package core.utils;

import core.driver.WebDriverHolder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BrowserActions {

    private final UiWait uiWait = new UiWait();

    public WebElement getWebElement(By by) {
        WebElement element = uiWait.until(wd -> wd.findElement(by), "Find element by locator: **%s**", by);
        uiWait.until(wd -> {
            new Actions(wd).moveToElement(element).perform();
            return element;
        }, "Move to element by locator: **%s**", by);
        return element;
    }

    public BrowserActions inputText(By by, String text) {
        uiWait.until(wd -> {
            WebElement element = wd.findElement(by);
            element.click();
            element.clear();
            element.sendKeys(text.toString());
            return element;
        }, "Type text *'%s'* into the element **%s**", text, by);
        return this;
    }

    public BrowserActions click(By by) {
        uiWait.until(wd -> {
            WebElement element = wd.findElement(by);
            element.click();
            return element;
        }, "Click on the element by locator: **%s**", by);
        return this;
    }

    public String getText(By by) {
        WebElement element = getWebElement(by);
        String text = getText(element);
        return text;
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public List<WebElement> getWebElements(By by) {
        return uiWait.until(wd -> wd.findElements(by), "Find list of elements by locator: **%s**", by);
    }

    public BrowserActions waitUntilElementBeVisible(By locator) {
        uiWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator), "Wait until the element **%s** be visible", locator);
        return this;
    }

    public BrowserActions waitUntilElementBeClickable(By locator) {
        uiWait.until(ExpectedConditions.elementToBeClickable(locator), "Wait until the element **%s** be clickable", locator);
        return this;
    }

    public List<WebElement> waitUntilElementsListIsNotEmpty(By locator) {
        return uiWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), "Wait until the elements **%s** list is not empty", locator);
    }

    public BrowserActions scrollToElement(WebElement element) {
        JavascriptExecutor jse = WebDriverHolder.getInstance().getJsExecutor();
        int x = element.getLocation().getX();
        int y = element.getLocation().getY();
        jse.executeScript(String.format("window.scrollTo(%s,%s)", x, y));
        return this;
    }

    public BrowserActions jsClick(By by) {
        WebElement element = getWebElement(by);
        String clickScript = "arguments[0].click()";
        WebDriverHolder.getInstance().getJsExecutor().executeScript(clickScript, element);
        return this;
    }

    public BrowserActions refreshPage() {
        WebDriverHolder.getInstance().getWebDriver().navigate().refresh();
        return this;
    }

}