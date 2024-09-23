package scenario3;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Boost3 {

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, 20);
            driver.get("https://boostb2b.com/contact");

            acceptCookies(wait);

            fillForm(wait, driver);

            handleMarketingEmails(driver);

            // Wait for manual reCAPTCHA validation
            System.out.println("Waiting for 10 seconds to manually validate reCAPTCHA...");
            Thread.sleep(10000);

            submitForm(driver);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // Accept cookie consent if available
    private static void acceptCookies(WebDriverWait wait) {
        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Accept')]")));
            acceptCookiesButton.click();
            System.out.println("Cookie consent accepted.");
        } catch (Exception e) {
            System.out.println("No cookie consent pop-up found.");
        }
    }

    // Fill out the form with necessary details
    private static void fillForm(WebDriverWait wait, WebDriver driver) {
        sendKeys(wait, By.name("firstname"), "Shanbin");
        sendKeys(wait, By.name("lastname"), "Shanan");
        sendKeys(wait, By.name("email"), "shanbin.shanan18@gmail.com");
        sendKeys(wait, By.name("jobtitle"), "Testing Technical Assessment");
        sendKeys(wait, By.name("company"), "Testing Technical Assessment");
        sendKeys(wait, By.name("country"), "United States");

        String formattedDate = fetchFormattedDate();
        sendKeys(wait, By.name("message"), "The date is " + formattedDate);

        Select topInterest = new Select(driver.findElement(By.name("contact_interest_reason")));
        topInterest.selectByVisibleText("Career Opportunities");
    }

    // Helper method to send keys to elements
    private static void sendKeys(WebDriverWait wait, By locator, String input) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(input);
    }

    // Handle marketing emails checkbox
    private static void handleMarketingEmails(WebDriver driver) {
        WebElement marketingCheckbox = driver.findElement(By.id("sign_up_for_marketing_emails-84814477-a911-4ee2-a265-e87f8069ad8a_4865"));
        if (marketingCheckbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", marketingCheckbox);
            System.out.println("Unchecked 'Sign Up for Marketing Emails'.");
        }
    }

    // Submit the form using JavaScript
    private static void submitForm(WebDriver driver) {
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }

    // Fetch and format the date from timeapi.io
    private static String fetchFormattedDate() {
        String apiUrl = "https://timeapi.io/api/Time/current/zone?timeZone=America/New_York";
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            // Parsing date from API response and formatting it
            String rawDate = result.toString().split("\"date\":\"")[1].split("\"")[0];
            LocalDate parsedDate = LocalDate.parse(rawDate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            return parsedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")); 

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
