package com.qa.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.util.*;

public class LandingPage extends BasePage{

    public LandingPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "q")
    private WebElement serachTextbox;

    @FindBy(name = "Q")
    private WebElement advanceSerachTextbox;

    @FindBy(xpath = "//input[@value='Search']")
    private WebElement serachButton;

    @FindBy(css = "[class='product-title']>a")
    private List<WebElement> productList;

    @FindBy(css = ".product-box-add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(css = "#bar-notification p.content")
    private WebElement addToCartSuccessMessage;

    @FindBy(className = "ico-cart")
    private WebElement  cartLink;

    @FindBy(css = ".close")
    private WebElement closeBannerLink;

    @FindBy(className = "header-logo")
    private WebElement headerLogo;

    public void clickHeaderLogo() {
        headerLogo.click();
    }

    public void enterProductToSearch(String product) {
        clearAndEnterText(serachTextbox, product);
    }

    public void clickSearchButton() {
        clickAction(serachButton);
    }

    public List<String> getProdutResult(){
        List<String> productName = new ArrayList<String>();
        productList.stream().forEach(ele -> productName.add(ele.getText()));
        return productName;
    }

    public boolean isAdvanceSearchDisplayed() {
        return isElementDisplayed(advanceSerachTextbox);
    }

    public void clickOnAddToCartButton() {
        addToCartButton.click();
    }

    public String getAddToCartSuccessMessage() {
        return addToCartSuccessMessage.getText();
    }

    public void clickCartLink() {
        cartLink.click();
    }

    public void closeSuccessBanner() {
        closeBannerLink.click();
        waitForInvisibilityElement(closeBannerLink);
    }
}
