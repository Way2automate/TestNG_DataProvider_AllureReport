package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

public class FooterLinkTest extends TestBase{

    @Test(testName = "Footer link Test", description = "To validate the footer links")
    @Description("Test Description: To validate the footer links")
    public void footerLinkTest() throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        new HomePageSteps(driver).validateFooterlinks();
        Log.info("Validated the footer links");
    }
}
