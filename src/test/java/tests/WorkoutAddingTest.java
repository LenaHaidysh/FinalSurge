package tests;

import org.testng.annotations.Test;

import java.util.Random;

import static org.testng.Assert.assertTrue;

public class WorkoutAddingTest extends BaseTest {

    @Test(description = "Add full training")
    public void addFullTraining() {
        String workoutName = "My new train";
        Random rnd = new Random();
        int distance = rnd.nextInt(100);
        loginPage.open();
        loginPage.login(email, password);
        calendarPage.open();
        calendarPage.isPageOpened();
        calendarPage.fullTrainingAdd();
        workoutAddingPage.isPageOpened();
        workoutAddingPage.createFullTraining(workoutName, Integer.toString(distance));
        workoutDetailsPage.isPageOpened();
        assertTrue(workoutAddingPage.findDetails(workoutName), "Тренировка с заданным именем не найдена");
        assertTrue(workoutAddingPage.findDetails(Integer.toString(distance)), "Тренировка с заданной дистанцией не найдена");
    }
}
