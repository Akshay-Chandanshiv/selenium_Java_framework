package testcomponents;

import Base.Base;
import Utility.ConfigReader;
import Utility.ExcelUtils;
import Utility.ExtentReportManager;
import Utility.LogUtils;
import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.*;
import pageobject.CartPage;
import pageobject.LoginPage;
import pageobject.ProductsPage;

/**
 * LoginTest - End-to-end test for SauceDemo.
 *
 * Data source:
 *   - Username, Password, ExpectedResult  →  Excel (testdata.xlsx)
 *   - Browser, URL, Timeouts              →  GlobalData.properties
 */
public class LoginTest extends Base {

    private static final String EXCEL_PATH =
            System.getProperty("user.dir") + "/src/test/resources/testdata/testdata.xlsx";
    private static final String SHEET_NAME = "LoginData";

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;

    // ================================================
    // DataProvider — reads from Excel
    // ================================================

    /**
     * Reads Username, Password, ExpectedResult from testdata.xlsx
     * Returns Object[][] — each row = one test run
     */
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        LogUtils.info("Reading test data from Excel: " + EXCEL_PATH);
        return ExcelUtils.getTestDataAsArray(EXCEL_PATH, SHEET_NAME);
    }

    // ================================================
    // Setup / Teardown
    // ================================================

    @BeforeMethod
    public void setUp() {
        setupDriver();
        // URL from GlobalData.properties — not from Excel
        getDriver().get(ConfigReader.getBaseUrl());
        loginPage = new LoginPage(getDriver());
        LogUtils.info("Navigated to: " + ConfigReader.getBaseUrl());
    }

    @AfterMethod
    public void tearDown() {
        quitDriver();
    }

    // ================================================
    // TC_001: Data-Driven Login Test (Excel)
    // ================================================

    /**
     * Runs once per row in Excel.
     * @param username       from Excel column "Username"
     * @param password       from Excel column "Password"
     * @param expectedResult from Excel column "ExpectedResult" (Pass/Fail)
     */
    @Test(priority = 1,
            dataProvider = "loginData",
            description = "Data-driven login test using Excel")
    public void testLoginWithExcelData(String username, String password, String expectedResult) {

        ExtentReportManager.getTest().info(
                "Testing → Username: " + username + " | Expected: " + expectedResult);

        LogUtils.info("Running login test → User: " + username + " | Expected: " + expectedResult);

        productsPage = loginPage.login(username, password);
        String currentUrl = getDriver().getCurrentUrl();

        if (expectedResult.equalsIgnoreCase("Pass")) {
            // Should land on inventory page
            Assert.assertTrue(currentUrl.contains("inventory"),
                    "Expected login SUCCESS but failed! URL: " + currentUrl);

            ExtentReportManager.getTest().log(Status.PASS,
                    "Login PASSED as expected. URL: " + currentUrl);
            LogUtils.info("Login PASSED for user: " + username);

        } else {
            // Should stay on login page with error
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                    "Expected login FAILURE but login succeeded! URL: " + currentUrl);

            String errorMsg = loginPage.getErrorMessage();
            ExtentReportManager.getTest().log(Status.PASS,
                    "Login FAILED as expected. Error: " + errorMsg);
            LogUtils.info("Login FAILED as expected for user: " + username + " | Error: " + errorMsg);
        }
    }

    // ================================================
    // TC_002: Assert Products Page Loads (Pass users only)
    // ================================================

    @Test(priority = 2, description = "Verify products page loads after valid login")
    public void testProductsPageLoad() {
        ExtentReportManager.getTest().info("Login with valid credentials from properties");

        // Valid credentials from GlobalData.properties
        productsPage = loginPage.login(
                ConfigReader.getUsername(),
                ConfigReader.getPassword()
        );

        String pageTitle = productsPage.getPageTitle();
        Assert.assertEquals(pageTitle, "Products",
                "Page title mismatch! Got: " + pageTitle);

        Assert.assertTrue(productsPage.isProductListDisplayed(),
                "Product list not displayed!");

        int count = productsPage.getProductCount();
        Assert.assertTrue(count > 0, "No products found!");

        ExtentReportManager.getTest().log(Status.PASS,
                "Products page loaded. Count: " + count);
        LogUtils.info("TC_002 PASSED - Product count: " + count);
    }

    // ================================================
    // TC_003: Add Product to Cart
    // ================================================

    @Test(priority = 3, description = "Verify adding a product to cart")
    public void testAddProductToCart() {
        productsPage = loginPage.login(
                ConfigReader.getUsername(),
                ConfigReader.getPassword()
        );

        productsPage.addFirstProductToCart();

        String cartCount = productsPage.getCartBadgeCount();
        Assert.assertEquals(cartCount, "1",
                "Cart badge mismatch! Got: " + cartCount);

        ExtentReportManager.getTest().log(Status.PASS,
                "Product added. Cart count: " + cartCount);
        LogUtils.info("TC_003 PASSED - Cart count: " + cartCount);
    }

    // ================================================
    // TC_004: Navigate to Cart
    // ================================================

    @Test(priority = 4, description = "Verify Cart page after adding product")
    public void testNavigateToCart() {
        productsPage = loginPage.login(
                ConfigReader.getUsername(),
                ConfigReader.getPassword()
        );

        productsPage.addFirstProductToCart();
        cartPage = productsPage.goToCart();

        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cart page not displayed! URL: " + getDriver().getCurrentUrl());

        String cartTitle = cartPage.getPageTitle();
        Assert.assertEquals(cartTitle, "Your Cart",
                "Cart title mismatch! Got: " + cartTitle);

        int itemCount = cartPage.getCartItemCount();
        Assert.assertEquals(itemCount, 1,
                "Cart item count mismatch! Got: " + itemCount);

        String itemName = cartPage.getFirstCartItemName();
        Assert.assertEquals(itemName, "Sauce Labs Backpack",
                "Wrong product in cart! Got: " + itemName);

        ExtentReportManager.getTest().log(Status.PASS,
                "Cart verified. Items: " + itemCount + " | Product: " + itemName);
        LogUtils.info("TC_004 PASSED - Cart product: " + itemName);
    }
}