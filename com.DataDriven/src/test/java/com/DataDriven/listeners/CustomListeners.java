package com.DataDriven.listeners;

import java.io.IOException;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.DataDriven.utilities.TestUtil;

public class CustomListeners implements ITestListener {
	

	public void onTestFailure(ITestResult arg0) {
	System.setProperty("org.uncommons.reportng.escape-output","false");
	
	try {
		TestUtil.captureScreenshot();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Reporter.log("Click to see Screenshot");
	Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
	Reporter.log("<br>");
	Reporter.log("<br>");
	Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+"</img></a>");
	}
}
