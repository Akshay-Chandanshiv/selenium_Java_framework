package Utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtils - Captures screenshots and saves to /screenshots folder.
 */
public class ScreenshotUtils {

    /**
     * Captures screenshot and returns the absolute file path.
     * @param driver  WebDriver instance
     * @param testName  Name used in the filename
     * @return absolute path of saved screenshot
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = System.getProperty("user.dir") + "/screenshots/" + fileName;

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            FileUtils.copyFile(src, dest);
            LogUtils.info("Screenshot saved: " + filePath);
        } catch (IOException e) {
            LogUtils.error("Failed to capture screenshot: " + e.getMessage());
        }

        return filePath;
    }
}
