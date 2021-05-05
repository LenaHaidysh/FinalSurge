package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.CalendarPage;
import pages.LoginPage;
import pages.WorkoutAddingPage;
import pages.WorkoutDetailsPage;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

@Listeners(TestListener.class) //будет слушать все тесты
public class BaseTest {

    WebDriver driver;
    LoginPage loginPage;
    CalendarPage calendarPage;
    WorkoutDetailsPage workoutDetailsPage;
    WorkoutAddingPage workoutAddingPage;
    PropertyReader propertyReader;
    String email;
    String password;
    String monthTraining;  //месяц, в котором планируется тренировка, устанавливать номер текущего месяца

    @BeforeMethod(description = "Opening browser")
    public void setup(ITestContext context) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        monthTraining = String.valueOf(month);
        email = System.getenv().getOrDefault("FS_EMAIL", propertyReader.getProperty("email"));
        password = System.getenv().getOrDefault("FS_PASSWORD", propertyReader.getProperty("password"));
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        context.setAttribute("driver", driver);
        loginPage = new LoginPage(driver);
        calendarPage = new CalendarPage(driver);
        workoutDetailsPage = new WorkoutDetailsPage(driver);
        workoutAddingPage = new WorkoutAddingPage(driver);
        propertyReader = new PropertyReader();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
