package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import java.util.*;

public class AccountLoginTest extends TestBase{

    @Test(dataProvider = "testData", testName = "Account Login Test", description = "Account Login")
    @Description("Test Description: To validate login feature")
    public void accountLoginTest(Map<String, String> map) throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        Log.info("Test Data: \n"+map);
        new HomePageSteps(driver).navigateToLoginPage();
        LoginPageSteps loginPageSteps = new LoginPageSteps(driver);
        loginPageSteps.enterLoginDetails(map.get("email"), map.get("password"));
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        loginPageSteps.clickLoginButton();
        loginPageSteps.verifyErrorMessage(map.get("message"));
    }

    @DataProvider(name = "testData")
    public Object[][] testData() throws Exception{
        return getTestData(this.getClass().getSimpleName());
    }
}
