package scenario1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Boost1 {

    public static void main(String[] args) throws IOException {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to BoostB2B Website
            driver.get("https://boostb2b.com");

            // Retrieve all the navigation links 
            List<WebElement> navLinks = driver.findElements(By.cssSelector("a"));

            // Write the navigation links to a text file
            FileWriter writer = new FileWriter("BoostB2B_NavigationListing.txt");
            for (WebElement link : navLinks) {
                writer.write(link.getText() + "\n"); 
            }
            writer.close();

            // Take a screenshot of the home page
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("BoostB2B_Navigation.png"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
