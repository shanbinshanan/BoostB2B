package scenario2;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


public class Boost2 {

    public static void main(String[] args) {
        // Setup WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Navigate to BoostB2B Website
            driver.get("https://boostb2b.com");

            WebDriverWait wait = new WebDriverWait(driver, 20); 

            // Handle the Cookie Consent pop-up if it appears
            try {
                WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Accept')]")));
                acceptCookiesButton.click();
                System.out.println("Cookie consent accepted.");
            } catch (Exception e) {
                System.out.println("No cookie consent pop-up found.");
            }
            
            // Locate and click the "Solutions" tab 
            WebElement solutionsTab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Solutions')]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", solutionsTab); 
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", solutionsTab); 

            // Define the expected links in the Solutions sub-menu
            List<String> expectedLinks = new ArrayList<>();
            expectedLinks.add("For Buyers");
            expectedLinks.add("For Suppliers");
            expectedLinks.add("For Merchants");
            expectedLinks.add("For Issuers");
            expectedLinks.add("Boost 100");
            expectedLinks.add("Boost Intercept");
            expectedLinks.add("Dynamic Boost");
            expectedLinks.add("U.S. Domestic");
            expectedLinks.add("U.S. Cross-Border");
            expectedLinks.add("International Companies");
            expectedLinks.add("Global Insights");

            // Get all links from the Solutions sub-menu using the CSS selector
            List<WebElement> actualLinks = driver.findElements(By.cssSelector("li.hs-menu-item a"));

            // Validate if all expected links are present in the actual links
            List<String> missingLinks = new ArrayList<>();
            for (String expectedLink : expectedLinks) {
                boolean linkFound = actualLinks.stream().anyMatch(link -> link.getText().equals(expectedLink));
                if (!linkFound) {
                    missingLinks.add(expectedLink);
                }
            }

            // Print the result of the validation
            if (missingLinks.isEmpty()) {
                System.out.println("All expected links are present in the Solutions sub-menu. Test passed!");
            } else {
                System.out.println("The following links are missing: " + missingLinks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
