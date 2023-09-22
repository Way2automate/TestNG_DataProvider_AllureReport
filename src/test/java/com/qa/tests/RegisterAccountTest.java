package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import java.util.*;

public class RegisterAccountTest extends TestBase{

    @Test(dataProvider = "testData", testName = "Register Account Test", description = "Register to shop")
    @Description("Test Description: To register a new user in to shop")
    public void registerAccountTest(Map<String, String> map) throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        Log.info("Test Data: \n"+map);
        HomePageSteps homePageSteps = new HomePageSteps(driver);
        homePageSteps.navigateToCreateAccount();

        RegisterPageSteps registerPageSteps = new RegisterPageSteps(driver);
        String registeredEmail = registerPageSteps.enterRegistrationDetails(map.get("firstName"), map.get("lastName"),
                map.get("email"), map.get("gender"), map.get("password"), map.get("confirmPassword"));
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        registerPageSteps.saveRegistrationDetails();
        registerPageSteps.verifyRegistrationCompleted(map.get("message"));

        homePageSteps.verifyLoggedInUser(registeredEmail);
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        homePageSteps.logut();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() throws Exception{
        return getTestData(this.getClass().getSimpleName());
    }
}
