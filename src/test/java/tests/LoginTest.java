package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class LoginTest extends BaseTest{

    @Test(retryAnalyzer = Retry.class)
    public void emptyEmail() {
        loginPage.open();
        loginPage.login("", password);
        String error = loginPage.getErrorMessage();
        assertEquals(error, "Please enter your e-mail address.", "неправильное сообщение об ошибке");
    }

    @Test(description = "Check login with empty Password")
    public void emptyPassword(){
        loginPage.open();
        loginPage.login(email, "");
        String error = loginPage.getErrorMessage();
        assertEquals(error, "Please enter a password.", "неправильное сообщение об ошибке");
    }

    @Test(description = "Check login with valid Login And Password")
    public void validLoginAndPassword(){
        loginPage.open();
        loginPage.login(email, password);
        WebElement profileIcon = driver.findElement(By.id("LayoutProfilePic"));
        assertTrue(profileIcon.isDisplayed(), "Добавленного продукта нет в корзине");
    }

}
