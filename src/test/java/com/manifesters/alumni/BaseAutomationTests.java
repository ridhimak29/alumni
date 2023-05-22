package com.manifesters.alumni;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseAutomationTests implements AutomateTest{

    private WebDriver driver;

    public BaseAutomationTests(WebDriver webDriver){
        driver = webDriver;
    }


    @Override
    public void setUp(String browser) throws MalformedURLException {
        DesiredCapabilities capabilities;
        switch (browser) {
            case "chrome":
                capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                break;
            case "firefox":
                capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("firefox");
                break;
            case "safari":
                capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("safari");
                break;
            default:
                throw new IllegalArgumentException("Invalid browser specified: " + browser);
        }

        // Set the URL of the Selenium Grid Hub
        String hubUrl = "http://192.168.1.184:4444/wd/hub";

        // Create the RemoteWebDriver instance
        driver = new RemoteWebDriver(new URL(hubUrl), capabilities);
        driver.manage().window().maximize();
    }

    @Override
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
