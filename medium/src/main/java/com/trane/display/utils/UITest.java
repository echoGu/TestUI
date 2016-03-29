package com.trane.display.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class UITest {
	WebDriver driver;
	Log log = new Log(this.getClass());

	public WebDriver getDriver() {
		return driver;
	}


	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void init(WebDriver driver) {
		log.info("Start WebDriver");
		setDriver(driver);
	}


	public void stop() {
		log.info("Stop WebDriver");
		driver.quit();

	}


	public void takeScreenShot() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String dateStr = sf.format(date);
		String path = this.getClass().getSimpleName() + "_" + dateStr + ".png";
		takeScreenShot((TakesScreenshot) this.getDriver(), path);
	}


	public void takeScreenShot(TakesScreenshot drivername, String path) {
		String currentPath = System.getProperty("user.dir"); // get current work
		log.info(currentPath);
		File scrFile = drivername.getScreenshotAs(OutputType.FILE);
		
		try {
			log.info("saved screenshot path is:" + currentPath + path);
			FileUtils.copyFile(scrFile, new File(currentPath + "\\" + path));
		} catch (Exception e) {
			log.error("Can't save screenshot");
			e.printStackTrace();
		} finally {
			log.info("screen shot finished");
		}
	}

}
