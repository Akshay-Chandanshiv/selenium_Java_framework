package listeners;

import Base.Base;
import Utility.ExtentReportManager;
import Utility.LogUtils;
import Utility.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener - TestNG ITestListener implementation.
 * Automatically:
 *  - Creates Extent Report test nodes
 *  - Logs PASS/FAIL/SKIP status
 *  - Captures screenshots on failure
 *
 * Register in testng.xml via <listeners> tag.
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.createTest(testName);
        LogUtils.info(">>> TEST STARTED: " + testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.getTest().log(Status.PASS, "Test PASSED: " + testName);
        LogUtils.info("<<< TEST PASSED: " + testName);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LogUtils.error("<<< TEST FAILED: " + testName);
        LogUtils.error("Failure reason: " + result.getThrowable().getMessage());

        // Capture screenshot on failure
        try {
            WebDriver driver = Base.driver.get();
            String screenshotPath = null;
            if (driver != null) {
                screenshotPath = ScreenshotUtils.captureScreenshot(driver, testName);
                ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath, "Screenshot on Failure");
            }
            ExtentReportManager.getTest().addScreenCaptureFromPath(
                    screenshotPath, "Screenshot on Failure");
        } catch (Exception e) {
            LogUtils.error("Could not capture screenshot: " + e.getMessage());
        }

        ExtentReportManager.getTest().log(Status.FAIL,
                "Test FAILED: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.getTest().log(Status.SKIP, "Test SKIPPED: " + testName);
        LogUtils.warn("<<< TEST SKIPPED: " + testName);
    }

    @Override
    public void onStart(ITestContext context) {
        LogUtils.info("====== Test Suite: " + context.getName() + " Started ======");
    }

    @Override
    public void onFinish(ITestContext context) {
        LogUtils.info("====== Test Suite: " + context.getName() + " Finished ======");
    }
}
