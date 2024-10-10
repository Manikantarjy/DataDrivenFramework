package com.DataDriven.testCases;


import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.DataDriven.base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException, IOException{
	
		//driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
		//instead of writing above we used reusable methods
		
		//if we add step directly it fails everycase
		//instead use try catch
		
//		try {
//		Assert.assertEquals("abc", "xyz");
//		System.out.println("After Assertion");
//		}catch(Throwable t) {
//			System.out.println("Inside catch");
//		}
		
		//instead of all above lines, we have created reusable method in Test Base
		//soft assertion: Test execution wont stop.. captures multiple screenshots as per failures
		verifyEquals("abc", "xyz");
		Thread.sleep(3000);
		log.debug("Inside login test");
		click("bmlBtn_CSS");
		
		Thread.sleep(3000);
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("AddCustBtn_CSS"))), "Login not successful");
		Reporter.log("login successfully executed");
		
		//if we write assert fail statement the test fails
		//hard assertion
		//Assert.fail("Login not successful");
	//output opens in a new tab
		
	}
		

}
