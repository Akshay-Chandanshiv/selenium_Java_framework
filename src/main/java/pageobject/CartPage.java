package pageobject;

import Utility.LogUtils;
import Utility.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * CartPage - Page Object for the SauceDemo Cart page.
 * URL: https://www.saucedemo.com/cart.html
 */
public class CartPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // ========================
    //        LOCATORS
    // ========================

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    // ========================
    //      CONSTRUCTOR
    // ========================

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // ========================
    //        ACTIONS
    // ========================

    /** Returns cart page heading text ("Your Cart") */
    public String getPageTitle() {
        waitUtils.waitForVisibility(pageTitle);
        String title = pageTitle.getText();
        LogUtils.info("Cart page title: " + title);
        return title;
    }

    /** Returns true if cart page is loaded */
    public boolean isCartPageDisplayed() {
        waitUtils.waitForUrlContains("cart.html");
        return driver.getCurrentUrl().contains("/cart.html");
    }

    /** Returns number of items in cart */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /** Returns the name of the first cart item */
    public String getFirstCartItemName() {
        waitUtils.waitForVisibility(cartItemNames.get(0));
        return cartItemNames.get(0).getText();
    }

    /** Clicks the Checkout button */
    public void clickCheckout() {
        waitUtils.waitForClickable(checkoutButton);
        checkoutButton.click();
        LogUtils.info("Clicked Checkout button.");
    }

    /** Clicks Continue Shopping to go back to products */
    public ProductsPage clickContinueShopping() {
        waitUtils.waitForClickable(continueShoppingButton);
        continueShoppingButton.click();
        LogUtils.info("Clicked Continue Shopping.");
        return new ProductsPage(driver);
    }
}
