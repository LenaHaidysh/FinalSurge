package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class WorkoutAddingPage extends BasePage {

    public static final By SELECT_NAV = By.id("selectnav1");
    public static final By ACTIVITY_TYPE = By.cssSelector("[data-code=bike]");
    public static final By WORKOUT_NAME = By.id("Name");
    public static final By DISTANCE = By.id("Distance");
    public static final By ADD_WORKOUT_BUTTON = By.id("saveButton");
    public static final String DETAIL_NAME = "//*[contains(text(), '%s')]";

    public WorkoutAddingPage(WebDriver driver) {
        super(driver);
    }

    @Step("Creating full training")
    public void createFullTraining(String workoutName, String distance) {
        driver.findElement(ACTIVITY_TYPE).click();
        driver.findElement(WORKOUT_NAME).sendKeys(workoutName);
        driver.findElement(DISTANCE).sendKeys(distance);
        driver.findElement(ADD_WORKOUT_BUTTON).click();
    }


    public boolean findDetails(String detailsName) {
        try {
            driver.findElement(By.xpath(String.format(DETAIL_NAME, detailsName))).isDisplayed();
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void isPageOpened() {
        try {
            driver.findElement(SELECT_NAV);
        } catch (NoSuchElementException ex) {
            Assert.fail("Страница с календарем тренировок не была загружена");
        }
    }
}
