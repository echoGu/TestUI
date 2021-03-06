package com.trane.display.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseActions 
{
	
	public WebDriver driver = new FirefoxDriver();
	public HashMap<String, Locator> locatorMap;
	public Log log = new Log(this.getClass());
	public String localDirAndFileName;
	public HashMap<String, String> dataMap;
	
	/**
	 * read xml to inital UI locator map
	 * @throws Exception
	 */
	public void InitLocatorMap() throws Exception 
	{
		localDirAndFileName = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\UIMaps\\" 
				+ "UIMap.xml";
		log.info(localDirAndFileName);
		locatorMap = XMLutils.readXMLDocument(localDirAndFileName);
	}
	
	public void VerifyData(String filename, Integer pageIndex) throws Exception 
	{
		localDirAndFileName = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\" 
				+ filename
				+ ".csv";
		dataMap = CSVutils.readCSV(localDirAndFileName, pageIndex);
		log.info(localDirAndFileName);
		  
		  if(!dataMap.isEmpty()) 
		  {
				Set<String> keys = dataMap.keySet();
				
				Iterator<String> iter = keys.iterator();
				  
				  while(iter.hasNext()) 
				  {
				      String key = (String)iter.next();
				      String value = (String)dataMap.get(key);
				      
				      if(!ElementExist(By.id(key))) 
				      {
				    	  log.info("This WebElement doesn't exist!" );
				    	  Assert.assertEquals("Blank", value);
				    	  
				    	  if(value.equalsIgnoreCase("Blank")) 
				    	  {
					    	  log.info("Pass! ");
					    	  log.info("Expected value: " + value);
				    	  } else if(!value.equalsIgnoreCase("Blank")) 
				    	  		{
						    	  log.error("Fail! ");
						    	  log.info("Expected value: " + value);
				    	  		}

				      } else if (ElementExist(By.id(key))) 
				      		{
				    		  WebElement e = driver.findElement(By.id(key));
				    		  String actualValue = e.getText();

				    		   Assert.assertEquals(actualValue, value);

					    		  if (actualValue.equalsIgnoreCase(value)) 
					    		  {
					    			  log.info("Pass! ");
					    			  log.info("Actual value: " + actualValue);
					    			  log.info("Expected value: " + value);
					    		  } else if (!actualValue.equalsIgnoreCase(value)) 
					    		  		{
						    			  log.error("Fail! ");
						    			  log.info("Actual value: " + actualValue);
						    			  log.info("Expected value: " + value);
					    		  		}
				    		  }
				      }
			} else 
			{
			  log.error("dataMap is empty!!!");
		    }
	}
	
	public void replaceFTPfile(String localfilename, String ftpDirAndFileName) throws Exception 
	{
		localDirAndFileName = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\UC Configs\\" 
				+ localfilename
				+ ".xml";

		FTPutils.deleteFile(ftpDirAndFileName);
		FTPutils.uploadFile(localDirAndFileName, ftpDirAndFileName);
	
	}
	
	public void configFTPfiles(String localRequiredDevices, String localConfigurationRecord, String localNameplateRecord, String localQuestionRecord) throws Exception 
	{
		FTPutils.connectFTP();
		
		log.info("configure RequiredDevices......");
		replaceFTPfile(localRequiredDevices, "/FW_IPC3DeviceBinding/RequiredDevices.xml");
		
		log.info("configure ConfigurationRecord......");
		replaceFTPfile(localConfigurationRecord, "/Configuration/ConfigurationRecord.xml");
		
		log.info("configure NameplateRecord......");
		replaceFTPfile(localNameplateRecord, "/Configuration/NameplateRecord.xml");
		
		log.info("configure QuestionRecord......");
		replaceFTPfile(localQuestionRecord, "/Configuration/QuestionRecord.xml");

		FTPutils.closeFTP();
		rebootMP();
		
	}
	
	public void rebootMP() 
	{
		String path = System.getProperty("user.dir") 
				+ "\\src\\main\\java\\com\\trane\\display\\cases\\data\\" 
				+ "UC800_reset.py";
		 try {  
	             log.info("start to reboot MP.........");
	             Process pr = Runtime.getRuntime().exec("python " + path);  
	             pr.waitFor();
	             log.info("wait 90 seconds to wait for reboot successfully......");
	             Thread.sleep(90000);
	             log.info("reboot MP successfully");
             } catch (Exception e) 
		 	  {  
                 e.printStackTrace();  
              }  
	}
	
	public WebDriver getDriver() 
	{
		return driver;
	}
    /**
     * load home page
     */
    public void openHomePage() 
    {
    	driver.get("http://192.168.1.3/FS/root/UI_Medium/index.html");  
    	WebDriverWait wait = new WebDriverWait(driver,20); 
    	wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("idLbl_pgHomePage_Title"), "Home"));
        log.info("Home Page is loaded successfully");
    }
    
    public void takeScreenShot() 
    {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		String dateStr = sf.format(date);
		
		String localDirAndFileName = System.getProperty("user.dir") 
				+ "\\screenshots\\" 
				+ dateStr
				+ ".png";
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		
		try 
		{
			log.info("saved screenshot is:" + localDirAndFileName);
			FileUtils.copyFile(scrFile, new File(localDirAndFileName));
		} catch (Exception e) 
		  {
			log.error("Can't save screenshot");
			e.printStackTrace();
		  } finally 
		    {
			 log.info("screen shot finished");
		    }
	}
    
    /**
     * 
     * @param locatorName
     * @return
     * @throws Exception
     */
    public boolean isElementPresent(String locatorName) throws Exception 
    {
    	Locator locator = getLocator(locatorName);
    	boolean isPresent = false;
    	WebDriverWait wait = new WebDriverWait(driver, 10);
    	isPresent = wait.until(ExpectedConditions.presenceOfElementLocated(getByLocator(locator))).isDisplayed();
    	return isPresent;
    	
    }
    
	boolean ElementExist(By Locator) 
	{
		try 
		{
			driver.findElement(Locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException ex) 
		{
			return false;
		}
	}
    
	/**
	 * 
	 * @param locatorName
	 * @return
	 * @throws IOException
	 */
	public Locator getLocator(String locatorName) throws IOException 
	{
		Locator locator = locatorMap.get(locatorName);
		return locator;

	}
	/**
	 * 
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public WebElement getElement(String locatorName) throws Exception 
	{
		Locator locator = getLocator(locatorName);
		By by = getByLocator(locator);
		WebElement element = driver.findElement(by);
		return element;
	}
	/**
	 * click and find a WebElement by locatorName
	 * @param locatorName
	 * @throws Exception
	 */
	public void click(String locatorName) throws Exception 
	{
		WebElement e = getElement(locatorName);
		log.info("click "+locatorName);
		e.click();
		Thread.sleep(3000);
	}
	/**
	 * 
	 * @param locator
	 * @return By
	 * @throws IOException
	 */
	public By getByLocator(Locator locator) throws IOException 
	{
		By by = null;
		String selector = locator.getSelector();
		switch (locator.getBy()) 
		{
			case xpath:
				log.debug("find element By xpath:"+selector);
				by = By.xpath(selector);
				break;
			case id:
				log.debug("find element By id:"+selector);
				by = By.id(selector);
				break;
			case name:
				log.debug("find element By name:"+selector);
				by = By.name(selector);
				break;
			case cssSelector:
				log.debug("find element By cssSelector:"+selector);
				by = By.cssSelector(selector);
				break;
			case className:
				log.debug("find element By className:"+selector);
				by = By.className(selector);
				break;
			case tagName:
				log.debug("find element By tagName:"+selector);
				by = By.tagName(selector);
				break;
			case linkText:
				log.debug("find element By linkText:"+selector);
				by = By.linkText(selector);
				break;
			case partialLinkText:
				log.debug("find element By partialLinkText:"+selector);
				by = By.partialLinkText(selector);
				break;
		}
		return by;
	}
	/**
	 * 
	 * @param locatorName
	 * @param num
	 * @throws Exception
	 */
	public void verifyText(String locatorName, Integer num) throws Exception 
	{
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getText(), num+"");
		
	}
	/**
	 * 
	 * @param locatorName
	 * @param text
	 * @throws Exception
	 */
	public void verifyText(String locatorName, String text) throws Exception 
	{
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getText(), text);
		
	}
	/**
	 * 
	 * @param locatorName
	 * @param attribute
	 * @param content
	 * @throws Exception
	 */
	public void verifyAttribute(String locatorName, String attribute, String content) throws Exception 
	{
		Thread.sleep(3000);
		Assert.assertEquals(getElement(locatorName).getAttribute(attribute), content);
	}
	
	/**
	 * find and click a WebElemnt by Text 
	 * @param WebElemnt's Text
	 */
	public void clickVisibleDiv(String text) 
	{
		driver.findElement(By.xpath("//*[text()='"+text+"']")).click();
		try 
		{
			Thread.sleep(3000);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void closeFirefox()
	{
		driver.close();
	}
	


}
