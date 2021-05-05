package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;

public class WorkoutDetailsPage extends BasePage {

    public static final By UPLOAD_BUTTON = By.cssSelector("[data-reveal-id=WorkoutUpload]");
    public static final By ERROR_MESSAGE = By.className("error");
    public static final By ELEMENT_FILE = By.className("muted");

    public WorkoutDetailsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Upload data")
    public void uploadData() {
        driver.findElement(UPLOAD_BUTTON).click();
        driver.switchTo().frame(driver.findElement(By.id("WorkoutUploadiFrame")));
    }

    @Step("Loading file")
    public void loadFile() {
        //WebElement addFile2 = driver.findElement(By.xpath("//input[@type='file']"));
        WebElement addFile = driver.findElement(By.id("TCXFile"));
        addFile.sendKeys(new File("src/test/resources/example.tcx").getAbsolutePath());
        driver.findElement(By.id("saveButton")).click();
    }

    public void isPageOpened() {
        try {
            driver.findElement(UPLOAD_BUTTON);
        } catch (NoSuchElementException ex) {
            Assert.fail("Страница с деталями тренировок не была загружена");
        }
    }

    public void isModalOpened() {
        try {
            driver.findElement(By.xpath("//*[@type='file']"));
        } catch (NoSuchElementException ex) {
            Assert.fail("Страница с модалкой загрузки файла не была загружена");
        }
    }

    @Step("Switch To Default Content")
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @Step("Check Loading File")
    public boolean checkLoadingFile() {
        try {
            driver.findElement(ELEMENT_FILE);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    @Step("Get Error Message")
    public String getErrorMessage() {
        driver.findElement(By.id("saveButton")).click();
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}
