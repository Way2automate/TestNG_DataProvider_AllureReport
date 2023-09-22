package com.qa.tests;

import com.qa.configs.*;
import com.qa.steps.*;
import com.qa.utils.*;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.*;

import java.util.*;

public class ComputerCheckoutTest extends TestBase{

    @Test(dataProvider = "testData", testName = "Computer Checkout Test", description = "To checkout a computer")
    @Description("Test Description: To checkout a Jewelry, Shoes & Apparel")
    public void computerCheckoutTest(Map<String, String> map) throws Exception {
        WebDriver driver = DriverManager.getInstance().getDriver();
        Log.info("Test Data: \n"+map);
        new HomePageSteps(driver).navigateToLoginPage();
        new LoginPageSteps(driver).login(map.get("email"), map.get("password"));

        CartPageSteps cartPageSteps = new CartPageSteps(driver);
        LandingPageSteps plpSteps = new LandingPageSteps(driver);
        CheckoutPageSteps checkoutPageSteps = new CheckoutPageSteps(driver);
        ProductPageSteps productPageSteps	= new ProductPageSteps(driver);

        plpSteps.navigateToCart();
        cartPageSteps.removeAllProducts();
        plpSteps.searchProduct(map.get("product"));
        plpSteps.addProductToCart();
        productPageSteps.enterComputerSpecification(map.get("processor"), map.get("RAM"), map.get("HDD"), map.get("OS"), map.get("software"));
        productPageSteps.updateQuantity(map.get("quantity"));
        ScreenshotUtil.takeScreenShot(map.get("Test_Case"));
        productPageSteps.addProductToCart();
        plpSteps.closeAddToCartSuccessBanner();
        plpSteps.navigateToCart();
        cartPageSteps.acceptTermsAndCheckout();
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
