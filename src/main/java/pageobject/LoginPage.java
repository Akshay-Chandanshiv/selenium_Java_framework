package pageobject;

import Utility.LogUtils;
import Utility.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage - Page Object for https://www.saucedemo.com
 * Contains all locators and actions for the Login page.
 */
public class LoginPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // ========================
    //        LOCATORS
    // ========================

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ========================
    //      CONSTRUCTOR
    // ========================

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // ========================
    //        ACTIONS
    // ========================

    /** Enter username into the username field */
    public void enterUsername(String username) {
        waitUtils.waitForVisibility(usernameField);
        usernameField.clear();
        usernameField.sendKeys(username);
        LogUtils.info("Entered username: " + username);
    }

    /** Enter password into the password field */
    public void enterPassword(String password) {
        waitUtils.waitForVisibility(passwordField);
        passwordField.clear();
        passwordField.sendKeys(password);
        LogUtils.info("Entered password.");
    }

    /** Click the Login button */
    public void clickLoginButton() {
        waitUtils.waitForClickable(loginButton);
        loginButton.click();
        LogUtils.info("Clicked Login button.");
    }

    /**
     * Full login action — enters credentials and submits.
     * @return ProductsPage (next page after successful login)
     */
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new ProductsPage(driver);
    }

    /** Returns the error message text if login fails */
    public String getErrorMessage() {
        waitUtils.waitForVisibility(errorMessage);
        return errorMessage.getText();
    }

    /** Checks if error message is displayed */
    public boolean isErrorDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
