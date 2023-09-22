package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import java.util.*;

public class CheckoutProductTest extends TestBase{

    @Test(dataProvider = "testData", testName = "Checkout Product Test", description = "To checkout a product")
    @Description("Test Description: To checkout a product")
    public void checkoutProductTest(Map<String, String> map) throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        Log.info("Test Data: \n"+map);
        new HomePageSteps(driver).navigateToLoginPage();
        new LoginPageSteps(driver).login(map.get("email"), map.get("password"));

        CartPageSteps cartPageSteps = new CartPageSteps(driver);
        LandingPageSteps plpSteps = new LandingPageSteps(driver);

        plpSteps.navigateToCart();
        cartPageSteps.removeAllProducts();
        for(String product : map.get("product").split("\\|")) {
            plpSteps.searchProduct(product);
            plpSteps.addProductToCart();
            plpSteps.closeAddToCartSuccessBanner();
        }
        plpSteps.navigateToCart();
        cartPageSteps.updateQuantity(map.get("quantity"));
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        cartPageSteps.acceptTermsAndCheckout();

        CheckoutPageSteps checkoutPageSteps = new CheckoutPageSteps(driver);
        checkoutPageSteps.selectFirstBillingAddress();
        checkoutPageSteps.selectShippingAddressAndMethod(map.get("shippingMethod"));
        checkoutPageSteps.selectPaymentMethod(map.get("paymentMethod"), map.get("cardType"), map.get("holderName"),
                map.get("cardNumber"), map.get("expirationDate"), map.get("code"), map.get("poNumber"));
        String orderNumber = checkoutPageSteps.confirmOrder();

        new OrderPageSteps(driver).verifyOrderDetails(map.get("quantity"), map.get("shippingMethod"), map.get("paymentMethod"), orderNumber);
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        new HomePageSteps(driver).logut();
    }

    @DataProvider(name = "testData")
    public Object[][] testData() throws Exception{
        return getTestData(this.getClass().getSimpleName());
    }
}
