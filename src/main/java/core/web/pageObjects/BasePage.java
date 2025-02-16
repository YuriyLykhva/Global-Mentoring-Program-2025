package core.web.pageObjects;

import org.openqa.selenium.WebDriver;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    public static final Duration WAIT_TIMEOUT_SECONDS = Duration.ofSeconds(10);

    protected abstract BasePage openPage();

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

}