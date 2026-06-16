# SauceDemo Selenium Automation Framework

## Tech Stack
- Java 11
- Selenium 4
- TestNG 7
- ExtentReports 5
- Log4j2
- Apache POI (Excel)
- WebDriverManager

---

## Project Structure

```
seleniumpractise/
├── logs/                          # Runtime logs (auto-generated)
├── reports/                       # Extent HTML reports (auto-generated)
├── screenshots/                   # Failure screenshots (auto-generated)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Base/
│   │   │   │   └── Base.java             # WebDriver setup/teardown, @BeforeSuite/@AfterSuite
│   │   │   ├── listeners/
│   │   │   │   └── TestListener.java     # TestNG listener — reports + screenshots on failure
│   │   │   ├── pageobject/
│   │   │   │   ├── LoginPage.java        # SauceDemo Login page POM
│   │   │   │   ├── ProductsPage.java     # Products/Inventory page POM
│   │   │   │   └── CartPage.java         # Cart page POM
│   │   │   └── Utility/
│   │   │       ├── ConfigReader.java     # Reads GlobalData.properties
│   │   │       ├── ExcelUtils.java       # Apache POI Excel reader
│   │   │       ├── ExtentReportManager.java # Extent Reports manager
│   │   │       ├── LogUtils.java         # Log4j2 wrapper
│   │   │       ├── ScreenshotUtils.java  # Screenshot capture helper
│   │   │       └── WaitUtils.java        # Explicit wait helpers
│   │   └── resources/
│   │       └── GlobalData.properties    # URL, credentials, browser, timeouts
│   └── test/
│       ├── java/
│       │   └── testcomponents/
│       │       └── LoginTest.java        # 4 test cases for the SauceDemo flow
│       └── resources/
│           ├── testng.xml               # TestNG suite config
│           ├── log4j2.xml               # Log4j2 config
│           └── testdata/                # Excel test data files (if needed)
└── pom.xml                              # Maven dependencies
```

---

## Test Cases

| TC # | Test Method | Description |
|------|-------------|-------------|
| TC_001 | testValidLogin | Login with valid credentials, assert URL changes |
| TC_002 | testProductsPageLoad | Assert Products page title and product listing |
| TC_003 | testAddProductToCart | Add product, assert cart badge shows "1" |
| TC_004 | testNavigateToCart | Go to Cart, assert page title, item count, item name |

---

## How to Run

### From IntelliJ
Right-click `testng.xml` → **Run**

### From Maven (terminal)
```bash
mvn clean test
```

### View Report
Open `reports/ExtentReport.html` in a browser after execution.

---

## Configuration
Edit `src/main/resources/GlobalData.properties` to change:
- `browser` → chrome / firefox
- `baseUrl` → application URL
- `username` / `password` → test credentials
