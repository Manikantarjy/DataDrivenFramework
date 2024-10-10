package com.DataDriven.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.DataDriven.listeners.ExtentListeners;
import com.DataDriven.utilities.ExcelReader;
import com.DataDriven.utilities.TestUtil;
import com.aventstack.extentreports.Status;

public class TestBase {

	//we create a reference and initialize it later part
	public static WebDriver driver;
	public static Properties config = new Properties(); 
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static WebDriverWait wait;
	public static Logger log = Logger.getLogger(TestBase.class);
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testData.xlsx");

	
	//public static Logger log;

	//before we call any test case
	@BeforeMethod
	@BeforeSuite
	public void setUp() {
		
		if(driver==null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //config files load
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(config.getProperty("browser").equals("chrome")) {
				
				driver = new ChromeDriver();
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
			}
			else if(config.getProperty("browser").equals("firefox")) {
				driver = new FirefoxDriver();
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\geckodriver.exe");
			}
			else if(config.getProperty("browser").equals("edge")) {
				driver = new FirefoxDriver();
				System.setProperty("webdriver.edge.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\msedgedriver.exe");				
			}
			
			driver.get(config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
			wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
			//wait = new WebDriverWait(driver, 5);
		
		}		
	}
	
	//Reusable method 
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e) {
			return false;
		}
		
	}
	
	public static void verifyEquals(String expected, String actual) throws IOException{
		

	try {
		Assert.assertEquals(actual, expected);
	}catch(Throwable t) {
		TestUtil.captureScreenshot();
		Reporter.log("<br>"+"Verification failure : "+t.getMessage()+"<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		//Extent Reports
       ExtentListeners.test.log(Status.PASS, "Verification failed with exception");
	}
		
	}
	
	static WebElement dropdown;
	
	public void select(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
			}else if(locator.endsWith("_XPATH")) {
			dropdown= driver.findElement(By.xpath(OR.getProperty(locator)));
			}else if(locator.endsWith("_id")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
			}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
			ExtentListeners.test.log(Status.INFO, "Selecting from dropdown on : " + locator + " value as " + value);
		
	}
	
	
	//we are making it more dynamic.
	
	public void click(String locator) {
		
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}else if(locator.endsWith("_id")) {
		driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		ExtentListeners.test.log(Status.INFO, "Clicking on : "+locator);
				
	}
	
	public void type(String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);

		}else if(locator.endsWith("_id")) {
		driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);

		}
		ExtentListeners.test.log(Status.INFO, "Typing in : "+locator+" entered values as "+value);

	}
	
	
	//call this method in extent listeners
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";		
		
		
	}
	
	

	@AfterSuite   //this will be executed after all the tests are executed
	public void tearDown() {
		
		if(driver!=null) {
		driver.quit();
	}
}
}
