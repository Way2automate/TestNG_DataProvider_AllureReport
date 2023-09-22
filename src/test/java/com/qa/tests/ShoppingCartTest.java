package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import java.util.*;

public class ShoppingCartTest extends TestBase{

    @Test(dataProvider = "testData", testName = "Shopping Cart Test", description = "Verify the features of shopping cart")
    @Description("Test Description: To verify the features of shopping cart")
    public void shoppingCartTest(Map<String, String> map) throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        Log.info("Test Data: \n"+map);
        LandingPageSteps plpSteps = new LandingPageSteps(driver);
        for(String product : map.get("product").split("\\|")) {
            plpSteps.searchProduct(product);
            plpSteps.validateSearchResults(product);
            plpSteps.addProductToCart();
            plpSteps.validateAddToCartSuccess(map.get("message"));
        }
        plpSteps.navigateToCart();
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));

        CartPageSteps cartPageSteps = new CartPageSteps(driver);
        cartPageSteps.updateQuantity(map.get("quantity"));
        cartPageSteps.validateTerms(map.get("termsHeading"), map.get("termsDetails"));
        cartPageSteps.validateShippingEstimate(map.get("country"), map.get("state"), map.get("zipCode"));
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        cartPageSteps.validateTermsWarningMessage(map.get("tcWarningHeading"), map.get("tcWarningContent"));
        cartPageSteps.removeAllProducts();
        cartPageSteps.validateEmptyCart(map.get("emptyMessage"));
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
    }

    @DataProvider(name = "testData")
    public Object[][] testData() throws Exception{
        return getTestData(this.getClass().getSimpleName());
    }
}
