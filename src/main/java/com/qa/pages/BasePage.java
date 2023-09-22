package com.qa.pages;

import com.qa.configs.GlobalVariables;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.pagefactory.*;
import org.openqa.selenium.support.ui.*;

public class BasePage implements GlobalVariables {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, EXPLICIT_WAIT);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT), this);
    }

    protected void click(String xpathExpression) {
        waitForElement(By.xpath(xpathExpression)).click();
    }

    protected void clearAndEnterText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    protected String readText(String xpathExpression) {
        return waitForElement(By.xpath(xpathExpression)).getText();
    }

    protected String getAttributeValue(String xpathExpression, String attributeName) {
        return waitForElement(By.xpath(xpathExpression)).getAttribute(attributeName);
    }

    protected WebElement waitForElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected boolean waitForInvisibilityElement(By by) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    protected boolean waitForInvisibilityElement(WebElement element) {
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void pause(int sec) {
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        pause(2);
    }

    protected void checkWhenUnchecked(By by) {
        if(!isElementSelected(by))
            waitForElement(by).click();
    }

    protected boolean isElementSelected(By by) {
        try {
            return waitForElement(by).isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(By by) {
        try {
            return waitForElement(by).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected String selectByText(WebElement element, String text) {
        Select select = new Select(element);
        select.selectByVisibleText(text);
        return select.getFirstSelectedOption().getText();
    }

    protected String selectByIndex(WebElement element, int index) {
        Select select = new Select(element);
        select.selectByIndex(index);
        return select.getFirstSelectedOption().getText();
    }

    public void waitForPageLoad() {
        pause(1);
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_WAIT);
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
            }
        });
    }

    public String switchToNewWindow() {
        String window = driver.getWindowHandle();
        for(String handle : driver.getWindowHandles()) {
            if(!window.equals(handle)) {
                driver.switchTo().window(handle);
            }
        }
        return window;
    }

    public void closeAndSwitchToParentWindow(String handle) {
        driver.close();
        driver.switchTo().window(handle);
    }

    protected void clickAction(WebElement element) {
        new Actions(driver).moveToElement(element).click().build().perform();
    }

    protected String getOrderNumber(WebElement element) {
        return element.getText().trim().replaceAll("[^0-9]", "");
    }
}
