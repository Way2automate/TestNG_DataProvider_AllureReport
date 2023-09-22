package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

public class PDFInvoiceTest extends TestBase{

    @Test(testName = "PDF Invoice Test", description = "Verify the details on the PDF Invoice of an Order")
    @Description("Test Description: To verify the details on the PDF Invoice of an Order")
    public void pdfInvoiceTest() throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        HomePageSteps homePageSteps = new HomePageSteps(driver);
        homePageSteps.navigateToLoginPage();
        new LoginPageSteps(driver).login(EMAIL, PASSWORD);
        homePageSteps.navigateToUserAccount();
        OrderPageSteps orderPageSteps = new OrderPageSteps(driver);
        orderPageSteps.naviageToDetailsFromMyOrder();
        orderPageSteps.validatePDFInvoice();
        ScreenshotUtil.takeScreenShot(new Object(){}.getClass().getEnclosingMethod().getName());
        Log.info("Validated PDF invoice file from Order Details page");
        homePageSteps.logut();
    }

    @Test(testName = "PDF Invoice Test", description = "Verify the details on the PDF Invoice of Downloadable products")
    @Description("Test Description: To verify the details on the PDF Invoice of Downloadable products")
    public void pdfInvoiceOfDownloadableProductsTest() throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        HomePageSteps homePageSteps = new HomePageSteps(driver);
        homePageSteps.navigateToLoginPage();
        new LoginPageSteps(driver).login(EMAIL, PASSWORD);
        homePageSteps.navigateToUserAccount();
        OrderPageSteps orderPageSteps = new OrderPageSteps(driver);
        orderPageSteps.naviageToDetailsFromDownloadableProducts();
        orderPageSteps.validatePDFInvoice();
        ScreenshotUtil.takeScreenShot(new Object(){}.getClass().getEnclosingMethod().getName());
        Log.info("Validated PDF invoice file from Downloadable products page");
        homePageSteps.logut();
    }
}
