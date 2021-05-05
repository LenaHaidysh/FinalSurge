package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public static final By LOGIN_INPUT = By.id("login_name");
    public static final By PASSWORD_INPUT = By.id("login_password");
    public static final By LOGIN_BUTTON = By.cssSelector("[type=submit]");
    public static final By ERROR_MESSAGE = By.cssSelector("[generated=true]");


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Opening LoginPage")
    public LoginPage open() {
        driver.get("https://log.finalsurge.com/login.cshtml");
        return this;
    }

    @Step("Filling credentials")
    public void login(String email, String password) {
        driver.findElement(LOGIN_INPUT).sendKeys(email);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
    }

    @Step("Get Error Message")
    public String getErrorMessage() {
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}
