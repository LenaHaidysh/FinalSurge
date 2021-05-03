package tests;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static org.testng.Assert.assertEquals;

public class CalendarTest extends BaseTest {

    @Test(description = "Quick Training Add Without Activity Type")
    public void quickTrainingAddWithoutActivityType() {
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining("" + monthTraining + "/10/2021", "Select...", "");
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
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        List<WebElement> activities = calendarPage.getTrainingList(dayNumber);
        assertEquals(activities.get(activities.size() - 1).getText(),activityType + ": " + workoutName,
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
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        List<WebElement> activities = calendarPage.getTrainingList(String.valueOf(dayNumber));
        assertEquals(activities.get(activities.size() - 1).getText(), activityType + ": " + workoutName,
                "Quick Training was not added");
    }

    @Test(description = "Deleting training")
    public void deleteTraining() {
        String dayNumber = "2";
        String activityType = "Run";
        String workoutName = "My new train";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        List<WebElement> activities = calendarPage.getTrainingList(dayNumber);
        int sizeBefore = activities.size();
        calendarPage.deleteTraining(dayNumber, "" + monthTraining + "/" + dayNumber + "/2021");
        calendarPage.isModalDisappeared();
        calendarPage.open();
        calendarPage.isPageOpened();
        List<WebElement> activitiesAfterDeleting = calendarPage.getTrainingList(dayNumber);
        int sizeAfter = activitiesAfterDeleting.size();
        assertEquals(sizeAfter, sizeBefore - 1, "Элемент не был удален");
    }

    @Test(description = "Drag And Drop training")
    public void dragAndDropTraining() {
        String dayNumber = "4";
        String dayNumberToDrop = "5";
        String activityType = "Run";
        Faker faker = new Faker();
        String workoutName = faker.artist().name();
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        calendarPage.dragAndDropTraining(dayNumber, dayNumberToDrop);
        List<WebElement> activities = calendarPage.getTrainingList(dayNumberToDrop);
        assertEquals(activities.get(0).getText(), activityType + ": " + workoutName,
                "Элемент не был перемещен");
    }

    @Test(enabled = false)
    public void deleteOnly() {
        String dayNumber = "2";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.deleteTraining(dayNumber, "" + monthTraining + "/" + dayNumber + "/2021");
    }
}
