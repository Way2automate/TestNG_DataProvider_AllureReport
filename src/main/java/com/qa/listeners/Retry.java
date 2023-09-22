package com.qa.listeners;

import com.aventstack.extentreports.Status;
import com.qa.configs.GlobalVariables;
import com.qa.utils.ScreenshotUtil;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer, GlobalVariables {

    private int count  = 0;
    private static int repeats = MAX_TRY; //Run the failed test 2 times

    @Override
    public boolean retry(ITestResult iTestResult) {
        System.out.println("In retry method");
        if (count < repeats) {                           //Check if maxTry count is reached
            count++;                                    //Increase the maxTry count by 1
            ScreenshotUtil.attachScreenshotToReport(iTestResult, Status.INFO);   //ExtentReports fail operations
            return true;                                //Tells TestNG to re-run the test
        }
        return false;
    }
}
