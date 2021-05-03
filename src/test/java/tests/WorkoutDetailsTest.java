package tests;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WorkoutDetailsTest extends BaseTest {

    @Test(description = "Load file with valid format")
    public void loadFileWithValidFormat() {
        String activityType = "Run";
        String workoutName = "My new train";
        String dayNumber = "1";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        calendarPage.viewDetails(dayNumber, "" + monthTraining + "/" + dayNumber + "/2021");
        workoutDetailsPage.isPageOpened();
        workoutDetailsPage.uploadData();
        workoutDetailsPage.isModalOpened();
        workoutDetailsPage.loadFile();
        workoutDetailsPage.switchToDefaultContent();
        assertTrue(workoutDetailsPage.checkLoadingFile(), "файл не был загружен");
    }

    @Test(description = "Load without file")
    public void loadWithoutFile() {
        String activityType = "Run";
        String workoutName = "My new train";
        String dayNumber = "1";
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.quickTrainingAdd();
        calendarPage.createQuickTraining("" + monthTraining + "/" + dayNumber + "/2021", activityType, workoutName);
        calendarPage.viewDetails(dayNumber, "" + monthTraining + "/" + dayNumber + "/2021");
        workoutDetailsPage.isPageOpened();
        workoutDetailsPage.uploadData();
        workoutDetailsPage.isModalOpened();
        String error = workoutDetailsPage.getErrorMessage();
        assertEquals(error, "This field is required.", "неправильное сообщение об ошибке");
    }
}
