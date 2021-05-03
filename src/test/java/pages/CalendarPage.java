package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class CalendarPage extends BasePage {
    public static final By SELECT_NAV = By.id("selectnav1");
    public static final By QUICK_ADD_BUTTON = By.id("QuickAddToggle");
    public static final By FULL_ADD_BUTTON = By.id("FullAddBtn");
    public static final By ADD_WORKOUT_BUTTON = By.id("saveButton");
    public static final By ALERT = By.className("alert");
    public static final By SELECT_ACTIVITY_TYPE = By.id("ActivityType");
    public static final By WORKOUT_NAME = By.id("Name");
    public static final By WORKOUT_DATE = By.id("WorkoutDate");
    public static final String DELETE_BUTTON = "//*[@data-date='%s']//*[@role='presentation']//*[@class='quick-delete']";
    public static final By OK_BUTTON = By.xpath("//*[@class='modal-footer']//*[contains(text(), 'OK')]");
    public static final String ACTIVITIES_LIST_ON_DAY = "//*[@class='fc-day-content' and @data-day= '%s']" +
            "//*[@class='fc-event-activity-title']";
    public static final String DRAG_PLACE = "//*[@data-day='%s']//*[@class='fc-day-number ']"; //место, на которое перетягиваем активность
    public static final String VIEW_BUTTON = "//*[@data-date='%s']//*[@role='presentation']//*[@class='full-view']";

    public CalendarPage(WebDriver driver) {
        super(driver);
    }


    @Step("Opening CalendarPage")
    public CalendarPage open() {
        driver.get("https://log.finalsurge.com/Calendar.cshtml");
        return this;
    }

    @Step("Adding Quick Training")
    public void quickTrainingAdd() {
        driver.findElement(QUICK_ADD_BUTTON).click();
    }

    @Step("Adding Full Training")
    public void fullTrainingAdd() {
        driver.findElement(FULL_ADD_BUTTON).click();
    }

    @Step("Creating Quick Training")
    public void createQuickTraining(String date, String option, String workoutName) {
        driver.findElement(WORKOUT_DATE).clear();
        driver.findElement(WORKOUT_DATE).sendKeys(date);
        Select dropdown = new Select(driver.findElement(SELECT_ACTIVITY_TYPE));
        dropdown.selectByVisibleText(option);
        driver.findElement(WORKOUT_NAME).sendKeys(workoutName);
        driver.findElement(ADD_WORKOUT_BUTTON).click();
    }

    @Step("Deleting Training")
    public void deleteTraining(String dayDelete, String dataDate) {
        List<WebElement> activities = driver.findElements(By.xpath(String.format(ACTIVITIES_LIST_ON_DAY, dayDelete)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", activities.get(0));
        driver.findElement(By.xpath(String.format(DELETE_BUTTON, dataDate))).click();
        driver.findElement(OK_BUTTON).click();
    }

    public void isModalDisappeared() {
        try {
            driver.findElement(QUICK_ADD_BUTTON);
        } catch (NoSuchElementException ex) {
            Assert.fail("Страница с модалкой не исчезла");
        }
    }

    @Step("Get Training List")
    public List<WebElement> getTrainingList(String day) {
        List<WebElement> activities = driver.findElements(By.xpath(String.format(ACTIVITIES_LIST_ON_DAY, day)));
        return activities;
    }

    @Step("Drag and drop Training")
    public void dragAndDropTraining(String dayDrag, String dayForDragAndDrop) {
        List<WebElement> activities = driver.findElements(By.xpath(String.format(ACTIVITIES_LIST_ON_DAY, dayDrag)));
        Actions action = new Actions(driver);
        WebElement DragPlace = driver.findElement(By.xpath(String.format(DRAG_PLACE, dayForDragAndDrop)));
        action.dragAndDrop(activities.get(activities.size() - 1), DragPlace).build().perform();
    }

    @Step("Get ErrorMessage")
    public String getErrorMessage() {
        return driver.findElement(ALERT).getText();
    }

    @Step("View details")
    public void viewDetails(String dayView, String dataDate) {
        List<WebElement> activities = driver.findElements(By.xpath(String.format(ACTIVITIES_LIST_ON_DAY, dayView)));
        try {
            activities.get(0).click();
        } catch (ElementClickInterceptedException exception) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", activities.get(0));
        }
        driver.findElement(By.xpath(String.format(VIEW_BUTTON, dataDate))).click();
    }

    public void isPageOpened() {
        findElement(SELECT_NAV, "Страница с календарем тренировок не была загружена");
    }


}

