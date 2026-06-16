package Utility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ExtentReportManager - Manages ExtentReports lifecycle.
 * Call initReports() once before suite, flushReports() once after.
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /** Initialize the report — call once in @BeforeSuite */
    public static void initReports() {
        String reportPath = System.getProperty("user.dir") + "/" + ConfigReader.getReportPath();
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("SauceDemo Test Execution");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", ConfigReader.getBaseUrl());
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        LogUtils.info("Extent Reports initialized at: " + reportPath);
    }

    /** Create a new test node in the report */
    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);
        return test;
    }

    /** Get the current test node (thread-safe) */
    public static ExtentTest getTest() {
        return extentTest.get();
    }

    /** Flush and write the report to disk — call in @AfterSuite */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            LogUtils.info("Extent Report flushed successfully.");
        }
    }
}
