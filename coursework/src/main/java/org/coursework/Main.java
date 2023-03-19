package org.coursework;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Main {
    WebDriver driver;
    @BeforeMethod
    public void setup() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
    }

    @Test
    public void task() {
        String loginUrl = "https://magento.softwaretestingboard.com/";
        String destinationUrl = "https://magento.softwaretestingboard.com/";
        driver.get(loginUrl);
        String currentUrl = driver.getCurrentUrl();
    }

}