package core.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class BrowserActions {

    private UiWait uiWait = new UiWait();

    public WebElement getWebElement(By by) {
        WebElement element = uiWait.until(wd -> wd.findElement(by), "Find element by locator: **%s**", by);
        uiWait.until(wd -> {
            new Actions(wd).moveToElement(element).perform();
            return element;
        }, "Move to element by locator: **%s**", by);
        return element;
    }

    public BrowserActions inputText(By by, String text) {
        WebElement element = getWebElement(by);
        element.sendKeys(text);
        return this;
    }

    public BrowserActions click(By by) {
        WebElement element = getWebElement(by);
        element.click();
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

}