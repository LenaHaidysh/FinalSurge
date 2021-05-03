package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public abstract class BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
    }

    public WebElement findElement(By by, String message) {
        try {
            driver.findElement(by);
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            Assert.fail(message);
        }
        return driver.findElement(by);
    }
}
