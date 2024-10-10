	package com.DataDriven.testCases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.DataDriven.base.TestBase;
import com.DataDriven.utilities.TestUtil;

public class AddCustomerTest extends TestBase {
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(String firstName, String lastName, String postCode, String alerttext, String runmode) throws InterruptedException {
	
		if(!runmode.equals("Y")){
			throw new SkipException("Skipping the testcase as the Run mode for data set is NO");
		}
		//the locators identification goes here.
		Thread.sleep(2000);
		click("AddCustBtn_CSS");
		type("firstname_CSS",firstName);
		type("lastname_XPATH",lastName);
		type("postcode_CSS",postCode);
		click("custBtn_CSS");
		
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());  //get reference of alert 

		Assert.assertTrue(alert.getText().contains(alerttext));
		
		Thread.sleep(3000);
		alert.accept();
		
	}

}
