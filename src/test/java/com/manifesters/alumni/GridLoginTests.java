package com.manifesters.alumni;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GridLoginTests {
    private WebDriver driver;


    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
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

    @Test(dataProvider = "loginData")
    public void testloginPage(String username, String password) {
        try {
            // Your test logic using Selenium WebDriver
            driver.get("http://localhost:9000/login");
            // Find the username and password fields
            // Different ways to finding element
            // By.id, By.className, By.name, By.tagName, By.xpath, By.cssSelector, By.linkText, By.partialLinkText
            WebElement usernameField = driver.findElement(By.name("email"));
            WebElement passwordField = driver.findElement(By.name("password"));

            // Enter the username and password
            //sendKeys you are typing the value in the field
            usernameField.sendKeys(username);
            passwordField.sendKeys(password);

/*            // Inject JavaScript code
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String script = "alert('Hello, World!')";
            jsExecutor.executeScript(script);*/

            // take a screenshots after adding data
            takeScreenShot("LoginTest_After_Fill_Value", driver.getClass().getSimpleName());

            // Submit the login form
            // Give
            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
            loginButton.click();

            // Verify the successful login by redirecting to a new URL
            String expectedRedirectUrl = "http://localhost:9000";
            String actualUrl = driver.getCurrentUrl();
            Assert.assertEquals(actualUrl, expectedRedirectUrl, "Login was unsuccessful. Redirect URL mismatch.");
        } catch (IOException ioException){
            System.out.println("Something went wrong while saving screenshots"+ ioException.getMessage());
        } catch(Exception e) {
            // Handle the exception, e.g., log the error or take appropriate actions
            System.out.println("Error occurred during test execution: " + e.getMessage());
        }
    }


    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                {"ridhimak29@gmail.com", "123456"},
                {"vishalmahajan087@gmail.com", "123456"},
        };
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void takeScreenShot(String stepNamePrefix, String driverName) throws IOException {
        // Take the screenshot and save it to a file
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Generate a dynamic file name
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = stepNamePrefix+ "_" + driverName +" _" + timestamp + ".png";

        // Define the destination file path
        String destinationFilePath = "src/test/resources/screenshots/" + fileName;

        // Copy the screenshot file to the destination file path
        FileHandler.copy(screenshotFile, new File(destinationFilePath));
        System.out.println("Screenshot saved: " + destinationFilePath);
    }

}
