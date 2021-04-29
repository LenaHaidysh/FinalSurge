package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;

public class CalendarTest extends BaseTest {

    String activitiesList = "//*[@class='fc-day-content' and @data-day= '%s']" +
            "//*[@class='fc-event-activity']"; //получаем список тренировок на определенный день

    @Test(description = "Quick Training Add Without Activity Type")
    public void quickTrainingAddWithoutActivityType() {
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining(""+monthTraining+"/10/2021","Select...","");
        String error = calendarPage.getErrorMessage();
        assertEquals(error, "×\n" +
                "Please fix the following errors:\n" +
                "*Please select a valid Activity Type.", "неправильное сообщение об ошибке");
    }

    @Test(description = "Quick Training Add With Activity Type")
    public void quickTrainingAddWithActivityType() {
        String activityType = "Run";
        String workoutName = "My new train";
        String dayNumber = "1";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining(""+monthTraining+"/"+dayNumber+"/2021", activityType, workoutName);
        List<WebElement> activities = driver.findElements(By.xpath(String.format(activitiesList, dayNumber)));
        assertEquals(activities.get(activities. size()-1).getText(), String.format(activityType+": "+workoutName),
                "Quick Training was not added");
    }

    @Test(description = "Quick Training Add With Activity Type on a random Day")
    public void quickTrainingAddWithActivityTypeOnRandomDay() {
        String activityType = "Run";
        String workoutName = "My new train";
        Random rnd = new Random();
        int dayNumber = rnd.nextInt(30);
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining(""+monthTraining+"/"+dayNumber+"/2021", activityType, workoutName);
        List<WebElement> activities = driver.findElements(By.xpath(String.format(activitiesList, dayNumber)));
        assertEquals(activities.get(activities. size()-1).getText(), String.format(activityType+": "+workoutName),
                "Quick Training was not added");
    }

    @Test(description = "Deleting training")
    public void deleteTraining(){
        String dayNumber = "2";
        String activityType = "Run";
        String workoutName = "My new train";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining(""+monthTraining+"/"+dayNumber+"/2021", activityType, workoutName);
        calendarPage.createQuickTraining(""+monthTraining+"/"+dayNumber+"/2021", activityType, workoutName);
        List<WebElement> activities = driver.findElements(By.xpath(String.format(activitiesList, dayNumber)));
        int sizeBefore = activities.size();
        calendarPage.deleteTraining(dayNumber,""+monthTraining+"/"+dayNumber+"/2021");//была хардкод дата, поменяла, проверить
        calendarPage.isModalDisappeared();
        calendarPage.open();
        calendarPage.isPageOpened();
        List<WebElement> activitiesAfterDeleting = driver.findElements(By.xpath(String.format(activitiesList, dayNumber)));
        int sizeAfter = activitiesAfterDeleting.size();
        assertEquals(sizeAfter, sizeBefore-1, "Элемент не был удален");
    }

    @Test(description = "Drag And Drop training")
    public void dragAndDropTraining(){
        String dayNumber = "1";
        String dayNumberToDrop = "2";
        String activityType = "Run";
        Faker faker = new Faker();
        String workoutName = faker.artist().name();
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining(""+monthTraining+"/"+dayNumber+"/2021", activityType, workoutName);
        calendarPage.dragAndDropTraining(dayNumber,dayNumberToDrop);
        List<WebElement> activities = driver.findElements(By.xpath(String.format(activitiesList, dayNumberToDrop)));
        assertEquals(activities.get(0).getText(), String.format(activityType+": "+workoutName),
                "Quick Training was not added");
    }

    @Test (enabled = false)
    public void deleteOnly(){
        String dayNumber = "2";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.deleteTraining(dayNumber,""+monthTraining+"/"+dayNumber+"/2021");//была хардкод дата, поменяла, проверить
    }
}
