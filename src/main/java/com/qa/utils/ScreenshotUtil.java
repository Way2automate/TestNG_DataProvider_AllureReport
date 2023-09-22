package com.qa.utils;

import com.aventstack.extentreports.Status;
import com.qa.configs.DriverManager;
import com.qa.configs.GlobalVariables;
import com.qa.reports.ReportManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import org.openqa.selenium.TakesScreenshot;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

public class ScreenshotUtil implements GlobalVariables {

    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);

    public static String takeScreenShot(String testCaseName){
        // Take screenshot and store as a file format
        String screenShotFilePath = SCREENSHOT_FOLDER+testCaseName+DateUtil.getStringDate("_ddMMyyyy_HHmmss")+IMAGE_TYPE;
        Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                .takeScreenshot(DriverManager.getInstance().getDriver());
        try {
            ImageIO.write(screenshot.getImage(),"PNG",new File(screenShotFilePath));
        } catch (IOException exp) {
            log.error("Exception occured while taking ScreenShot: ", exp);
        }
        return screenShotFilePath;
    }

    public static void attachScreenshotToReport(ITestResult result, Status status) {
        String base64Screenshot = "data:image/png;base64," +
                ((TakesScreenshot) DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.BASE64);
        //ExtentReports log and screenshot operations for failed tests.
        ReportManager.getTest().log(status, result.getMethod().getMethodName()+" "+FAIL, result.getThrowable(),
                ReportManager.getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] attachImageToAllure(String testName) {
        return ((TakesScreenshot) DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
