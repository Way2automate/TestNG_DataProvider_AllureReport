package com.qa.tests;

import com.qa.configs.*;
import com.qa.listeners.*;
import com.qa.reports.*;
import com.qa.utils.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

import java.lang.reflect.*;
import java.util.*;

@Listeners({TestListener.class})
public class TestBase implements GlobalVariables {

    protected WebDriverWait wait;

    @BeforeSuite
    public void configurations(ITestContext context) {
        Directory dir = new Directory();
        dir.clearFolder(DOWNLOAD_FOLDER);
        dir.clearFolder(SCREENSHOT_FOLDER);
        dir.clearFolder(ALLURE_RESULTS);
    }

    @BeforeMethod (alwaysRun = true)
    public void setup(Method method) {
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("download.default_directory", DOWNLOAD_FOLDER);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--remote-allow-origins=*");
        options.setAcceptInsecureCerts(true);
        WebDriver driver = new  ChromeDriver(options);
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
        wait = new WebDriverWait(driver, EXPLICIT_WAIT);
        DriverManager.getInstance().setDriver(driver);
        ReportManager.startTest(method.getName());
    }

    @AfterMethod (alwaysRun = true)
    public void tearDown() {
        DriverManager.getInstance().getDriver().quit();
    }

    public Object[][] getTestData(String className) throws Exception{
        ExcelUtil excel = new ExcelUtil();
        excel.setExcelFile(DATA_FOLDER + WORKBOOK);
        int testRow = excel.getNumberOfRows(className);
        Object[][] data = new Object[testRow-1][1];
        for(int i=1; i<testRow; i++) {
            data[i-1][0] = excel.getData(className, i);
        }
        return data;
    }

    protected void pause(int sec) {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
