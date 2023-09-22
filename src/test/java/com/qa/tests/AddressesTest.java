package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import java.util.*;

public class AddressesTest extends TestBase {

    @Test(dataProvider = "testData", testName = "Address Test", description = "Verify that user is able to add, update and delete an address")
    @Description("Test Description: To verify that user is able to add, update and delete an address")
    public void addressTest(Map<String, String> map) throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        Log.info("Test Data: \n"+map);
        HomePageSteps homePageSteps = new HomePageSteps(driver);
        homePageSteps.navigateToLoginPage();
        new LoginPageSteps(driver).login(map.get("email"), map.get("password"));
        homePageSteps.navigateToUserAccount();
        AddressesPageSteps addressesPageSteps = new AddressesPageSteps(driver);
        addressesPageSteps.navigateToAddress();
        addressesPageSteps.clickAddNewAddress();
        String email = addressesPageSteps.enterAddressDetails(map.get("firstName"), map.get("lastName"), map.get("emailAddress"),
                map.get("company"), map.get("country"), map.get("state"), map.get("city"), map.get("addressLine1"),
                map.get("addressLine2"), map.get("zip"), map.get("phone"), map.get("fax"));
        addressesPageSteps.saveAddressAndVerify(email);
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        addressesPageSteps.editAddress(email);
        email = addressesPageSteps.updateAddressDetails(map.get("updatedEmail"), map.get("updatedPhone"), map.get("updatedFax"));
        addressesPageSteps.saveAddressAndVerify(email);
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        addressesPageSteps.deleteAddress(email);
        homePageSteps.logut();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() throws Exception{
        return getTestData(this.getClass().getSimpleName());
    }

}
