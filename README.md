# 🚀 Selenium Java Automation Framework

![Java](https://img.shields.io/badge/Java-11%2B-orange)
![Selenium](https://img.shields.io/badge/Selenium-4.x-green)
![TestNG](https://img.shields.io/badge/TestNG-7.x-red)
![Maven](https://img.shields.io/badge/Maven-3.x-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

A robust, scalable **Selenium WebDriver** automation framework built with **Java**, **TestNG**, and **Page Object Model (POM)** design pattern. Includes data-driven testing, extent reports, logging, and screenshot capture on failure.

---

## 📌 Features

- ✅ Page Object Model (POM) Design Pattern
- ✅ Data-Driven Testing using Apache POI (Excel)
- ✅ Extent Reports for detailed HTML reporting
- ✅ Log4j2 for logging
- ✅ Screenshot capture on test failure
- ✅ TestNG for test management and parallel execution
- ✅ Configurable via `GlobalData.properties`
- ✅ Explicit Wait helpers for stable test execution

---

## 🛠️ Tech Stack

| Technology | Version |
|---|---|
| Java | 11+ |
| Selenium WebDriver | 4.x |
| TestNG | 7.x |
| Maven | 3.x |
| Apache POI | 5.x |
| ExtentReports | 5.x |
| Log4j2 | 2.x |

---

## 📁 Project Structure

```
seleniumpractise/
├── logs/                          # Runtime logs (auto-generated)
├── reports/                       # Extent HTML reports (auto-generated)
├── screenshots/                   # Failure screenshots (auto-generated)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Base/
│   │   │   │   └── Base.java             # WebDriver setup/teardown
│   │   │   ├── listeners/
│   │   │   │   └── TestListener.java     # TestNG listener — reports + screenshots
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
│   │       └── GlobalData.properties    # URL, browser, timeouts config
│   └── test/
│       ├── java/
│       │   └── testcomponents/
│       │       └── LoginTest.java        # Test cases for SauceDemo flow
│       └── resources/
│           ├── testng.xml               # TestNG suite config
│           ├── log4j2.xml               # Log4j2 config
│           └── testdata/                # Excel test data files
└── pom.xml                              # Maven dependencies
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 11 or higher installed
- Maven installed
- Chrome browser installed
- IntelliJ IDEA (recommended)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/akshaycode-hub/selenium_Java_framework.git
```

**2. Open in IntelliJ IDEA**
```
File → Open → Select project folder
```

**3. Configure `GlobalData.properties`**
```properties
url=https://www.saucedemo.com
browser=chrome
username=standard_user
password=secret_sauce
```

**4. Run Tests**
```bash
mvn test
```
Or right-click `testng.xml` → Run

---

## 📊 Reports

After test execution, reports are generated in the `reports/` folder:
```
reports/
└── ExtentReport.html    ← Open in browser to see results
```

---

## 📸 Screenshots

Screenshots are automatically captured on test failure:
```
screenshots/
└── TestName_timestamp.png
```

---

## 🧪 Test Cases

| Test Case | Description |
|---|---|
| Valid Login | Login with correct credentials |
| Invalid Login | Login with wrong credentials |
| Add to Cart | Add product to cart |
| Logout | Logout from application |

---

## 👤 Author

**Akshay** — [@akshaycode-hub](https://github.com/akshaycode-hub)

---

## 📝 License

This project is for learning and practice purposes.
```
