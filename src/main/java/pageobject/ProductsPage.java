package pageobject;

import Utility.LogUtils;
import Utility.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * ProductsPage - Page Object for the SauceDemo Products/Inventory page.
 * URL: https://www.saucedemo.com/inventory.html
 */
public class ProductsPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // ========================
    //        LOCATORS
    // ========================

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    // Add to cart button for the first product (Sauce Labs Backpack)
    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpackToCartBtn;

    // Cart icon with badge
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    // ========================
    //      CONSTRUCTOR
    // ========================

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // ========================
    //        ACTIONS
    // ========================

    /** Returns the page heading text ("Products") */
    public String getPageTitle() {
        waitUtils.waitForVisibility(pageTitle);
        String title = pageTitle.getText();
        LogUtils.info("Products page title: " + title);
        return title;
    }

    /** Checks if the products list is displayed */
    public boolean isProductListDisplayed() {
        waitUtils.waitForVisibility(productItems.get(0));
        boolean displayed = !productItems.isEmpty();
        LogUtils.info("Products displayed: " + displayed + " | Count: " + productItems.size());
        return displayed;
    }

    /** Returns count of products on the page */
    public int getProductCount() {
        return productItems.size();
    }

    /** Adds the first product (Sauce Labs Backpack) to the cart */
    public void addFirstProductToCart() {
        waitUtils.waitForClickable(addBackpackToCartBtn);
        addBackpackToCartBtn.click();
        LogUtils.info("Added 'Sauce Labs Backpack' to cart.");
    }

    /** Returns the cart badge count as string (e.g. "1") */
    public String getCartBadgeCount() {
        waitUtils.waitForVisibility(cartBadge);
        return cartBadge.getText();
    }

    /** Clicks the cart icon to navigate to Cart page */
    public CartPage goToCart() {
        waitUtils.waitForClickable(cartIcon);
        cartIcon.click();
        LogUtils.info("Navigated to Cart page.");
        return new CartPage(driver);
    }
}
