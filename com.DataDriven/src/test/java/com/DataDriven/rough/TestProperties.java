package com.DataDriven.rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		
		
		// System.out.println(System.getProperty("user.dir"));  >> C:\Users\Mani\eclipse-workspace\com.DataDriven

		//importing properties : Config/OR
		Properties config = new Properties();
		Properties OR = new Properties();
		//reading config prop file
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
		config.load(fis); //config files load
		
		fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
		OR.load(fis);
		System.out.println(config.getProperty("browser"));  //reading browsers value from its property.
	
		// during runtime
		//driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click
		System.out.println(OR.getProperty("bmlBtn"));  // it prints css of the button
	} 
}
