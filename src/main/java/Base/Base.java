package Base;

import Utility.ConfigReader;
import Utility.ExtentReportManager;
import Utility.LogUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.time.Duration;

/**
 * Base - Parent class for all tests.
 * Handles WebDriver init/teardown and Extent Report lifecycle.
 * All test classes extend this.
 */
public class Base {

    // ThreadLocal so parallel tests each get their own driver
    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    @BeforeSuite
    public void initSuite() {
        // Screenshots folder clear करा प्रत्येक run आधी
        clearScreenshotsFolder();
        ExtentReportManager.initReports();
        LogUtils.info("=== Test Suite Started ===");
    }

    private void clearScreenshotsFolder() {
        File screenshotsDir = new File(System.getProperty("user.dir") + "/screenshots/");
        if (screenshotsDir.exists()) {
            for (File file : screenshotsDir.listFiles()) {
                file.delete();
            }
        }
        LogUtils.info("Screenshots folder cleared.");
    }

    @AfterSuite
    public void teardownSuite() {
        ExtentReportManager.flushReports();
        LogUtils.info("=== Test Suite Finished ===");
    }

    /** Call this in your test's @BeforeMethod */
    public void setupDriver() {
        String browser = ConfigReader.getBrowser().toLowerCase();
        WebDriver webDriver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                // options.addArguments("--headless"); // Uncomment for headless
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                options.addArguments("--incognito");
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--disable-features=PasswordLeakDetection");

                java.util.HashMap<String, Object> prefs = new java.util.HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.password_manager_leak_detection", false);
                options.setExperimentalOption("prefs", prefs);

                webDriver = new ChromeDriver(options);
                break;
        }

        webDriver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(ConfigReader.getImplicitWait()));
        webDriver.manage().window().maximize();

        driver.set(webDriver);
        LogUtils.info("Browser launched: " + browser);
    }

    /** Returns the current thread's WebDriver instance */
    public WebDriver getDriver() {
        return driver.get();
    }

    /** Call this in your test's @AfterMethod */
    public void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            LogUtils.info("Browser closed.");
        }
    }
}
