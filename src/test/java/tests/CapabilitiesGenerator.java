package tests;

import org.openqa.selenium.chrome.ChromeOptions;

public class CapabilitiesGenerator {
    public static ChromeOptions getChromeOptions() {
        String driverPath = "src/test/resources/webdrivers";
        ChromeOptions options = new ChromeOptions();
        String os = System.getProperty("os.name").toLowerCase();//вернет ос и выберет драйвер из ресурсов для конкретной ос
        System.out.println("Operational system: " + os + "; Driver path: " + driverPath);
        if (os.contains("win")) {
            System.setProperty("webdriver.chrome.driver", driverPath + "/chromedriver.exe");
        } else if (os.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", driverPath + "/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", driverPath + "/linux/chromedriver");
        }
        options.addArguments("--ignore-certificate-errors");//
        options.addArguments("--disable-popup-blocking");//чтобы ниче не блокировала
        options.addArguments("--disable-notifications");//чтобы не было подписаться на обновления

        return options;
    }
}
