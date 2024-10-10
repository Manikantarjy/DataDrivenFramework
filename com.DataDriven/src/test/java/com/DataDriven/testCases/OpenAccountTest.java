	package com.DataDriven.testCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.DataDriven.base.TestBase;
import com.DataDriven.utilities.TestUtil;

public class OpenAccountTest extends TestBase {
	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")

	public void openAccountTest(String customer, String currency) throws InterruptedException {
	//the locators identification goes here.
		
		click("openaccount_CSS");
		select("customer_CSS", customer);
		select("currency_CSS", currency);
		click("process_CSS");
		Thread.sleep(3000);
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		//Assert.assertTrue(alert.getText().contains(alertText2));
		alert.accept();
		
		
		
	//we need to implement dataprovider and get data from excel..
	}

}
