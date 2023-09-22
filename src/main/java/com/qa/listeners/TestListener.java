package com.qa.listeners;

import com.aventstack.extentreports.Status;
import com.qa.configs.GlobalVariables;
import com.qa.reports.ReportManager;
import com.qa.utils.Log;
import com.qa.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, GlobalVariables {

    private static String getTestName(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    public void onFinish(ITestContext iTestContext) {
        //Do tier down operations for ExtentReports reporting!
        ReportManager.endTest();
    }


    public void onTestStart(ITestResult iTestResult) {
        Log.messageLog(getTestName(iTestResult)+" "+START);
    }


    public void onTestSuccess(ITestResult iTestResult) {
        Log.messageLog(getTestName(iTestResult)+" "+PASS);
        //ExtentReports log operation for passed tests.
        ReportManager.getTest().log(Status.PASS,  getTestName(iTestResult)+" "+PASS);
    }


    public void onTestFailure(ITestResult iTestResult) {
        Log.error(getTestName(iTestResult)+" "+FAIL,  iTestResult.getThrowable());
        Log.messageLog(getTestName(iTestResult)+" "+FAIL);
        ScreenshotUtil.attachScreenshotToReport(iTestResult, Status.FAIL);
        ScreenshotUtil.takeScreenShot(getTestName(iTestResult)+"_"+FAIL);
        ScreenshotUtil.attachImageToAllure(getTestName(iTestResult));
        Log.attachLogToAllure(iTestResult.getThrowable());
    }

    public void onTestSkipped(ITestResult iTestResult) {
        Log.messageLog(getTestName(iTestResult)+" "+SKIP);
        //ExtentReports log operation for skipped tests.
        ReportManager.getTest().log(Status.SKIP, getTestName(iTestResult)+" "+SKIP);
    }
}
