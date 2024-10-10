package com.DataDriven.utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.DataDriven.base.TestBase;

public class TestUtil extends TestBase{
	
	public static String screenshotPath;
	public static String screenshotName;
	public static void captureScreenshot() throws IOException {
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		Date d = new Date();
		screenshotName = d.toString().replace(":", "_").replace(" ","_")+".jpg";
		
		FileUtils.copyFileToDirectory(srcFile, new File(System.getProperty("user.dir")+"\\target\\surefire-reports\\html\\"+screenshotName));
	}

	
	//Writing common data provider
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m){
		
		String sheetName = m.getName();  //get the method name from test.
		//for this we need excel utility
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][cols];
		
		for(int rowNum =2; rowNum <=rows; rowNum++) { //starts from row 2, 
			for(int colNum =0; colNum <cols; colNum++) {
				
				//data[0][0]  -- parameterized 
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
		
	}
	
public static boolean isTestRunnable(String testName, ExcelReader excel) {	
		String sheetName = "test_suite";
		int rows = excel.getRowCount(sheetName);
		for(int rNum=2; rNum<=rows; rNum++) {
			String testCase = excel.getCellData(sheetName, "TCID", rNum);
			if(testCase.equalsIgnoreCase(testName)) {
				String runmode = excel.getCellData(sheetName, "Runmode", rNum);
				
			if(runmode.equalsIgnoreCase("Y")) {
				return true;
			}else {
				return false;
			}
			}
		}
		return false;
}
	}
