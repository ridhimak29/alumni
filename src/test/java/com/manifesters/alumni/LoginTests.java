package com.manifesters.alumni;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginTests {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        // Set up the browser driver based on the provided parameter
        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions().addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                //System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
                driver = new FirefoxDriver();
                break;
            case "safari":
                WebDriverManager.safaridriver().setup();
                //System.setProperty("webdriver.edge.driver", "path/to/msedgedriver");
                driver = new SafariDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser specified: " + browser);
        }
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
