package com.DataDriven.listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.DataDriven.base.TestBase;
import com.DataDriven.utilities.ExtentListenerNG;
import com.DataDriven.utilities.TestUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentListeners extends TestBase implements ITestListener{
	
	public static ExtentTest test;
	ExtentReports extent = ExtentListenerNG.getReportObject();
	
	@Override
	public void onTestStart(ITestResult result) {

	test = extent.createTest(result.getMethod().getMethodName());
		if(!TestUtil.isTestRunnable(result.getName(), excel))
		{
			throw new SkipException("Skipping the test "+result.getName().toUpperCase()+"as the Run Mode is No");
		}
	
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
		
		test.log(Status.SKIP, result.getName().toUpperCase()+"Skipped the test");
		
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {

	test.log(Status.PASS, "Test Passed");
	
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		test.fail(result.getThrowable());
		
		//code that will help you get the driver.
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//SS, attach to report
		
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
		  }
	
	
	@Override
	public void onFinish(ITestContext context) {
		
		extent.flush();
		
	}
	
}
